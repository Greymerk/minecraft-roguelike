package greymerk.roguelike.treasure;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public enum Treasure {

	ARMOUR, WEAPONS, BLOCKS, ENCHANTING, FOOD, ORE, POTIONS, STARTER, TOOLS, SUPPLIES, SMITH, MUSIC, SPECIAL, REWARD, EMPTY;
	
	public static final List<Treasure> level0 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS, FOOD));
	public static final List<Treasure> level1 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS, FOOD));
	public static final List<Treasure> level2 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	public static final List<Treasure> level3 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	public static final List<Treasure> level4 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	
	private static ITreasureChest getChest(Treasure type) {
		
		switch(type){
		case ARMOUR: return new TreasureChestArmour();
		case WEAPONS: return new TreasureChestWeapons();
		case BLOCKS: return new TreasureChestBlocks();
		case ENCHANTING: return new TreasureChestEnchanting();
		case FOOD: return new TreasureChestFood();
		case ORE: return new TreasureChestOre();
		case POTIONS: return new TreasureChestPotions();
		case STARTER: return new TreasureChestStarter();
		case TOOLS: return new TreasureChestTools();
		case SUPPLIES: return new TreasureChestSupplies();
		case SMITH: return new TreasureChestSmithy();
		case MUSIC: return new TreasureChestMusic();
		case SPECIAL: return new TreasureChestSpecialty();
		case REWARD: return new TreasureChestReward();
		default: return new TreasureChestFood();
		}
	}
	
	public static ITreasureChest generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, int level, boolean trapped){
		
		Treasure type = getChestType(rand, level);
		ITreasureChest chest = getChest(type);
		
		return chest.generate(editor, rand, settings.getLoot(), pos, level, trapped);
	}
	
	public static ITreasureChest generate(WorldEditor editor,  Random rand, LevelSettings settings, Coord pos){
		return generate(editor, rand, settings, pos, settings.getDifficulty(pos), false);
	}
	
	public static ITreasureChest generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, Treasure type){
		return generate(editor, rand, settings, pos, type, settings.getDifficulty(pos), false);
	}
	
	public static ITreasureChest generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, Treasure type, int level, boolean trapped){
		ITreasureChest chest = getChest(type);
		chest.generate(editor, rand, settings.getLoot(), pos, level, trapped);
		return chest;
	}
	
	public static List<ITreasureChest> generate(WorldEditor editor, Random rand, LevelSettings settings, List<Coord> space, Treasure type){
		return createChests(editor, rand, settings, 1, space, new ArrayList<Treasure>(Arrays.asList(type)));
	}
	
	public static List<ITreasureChest> createChests(WorldEditor editor, Random rand, LevelSettings settings, int numChests, List<Coord> space){
		return createChests(editor, rand, settings, numChests, space, false);
	}
	
	public static List<ITreasureChest> createChests(WorldEditor editor, Random rand, LevelSettings settings, int numChests, List<Coord> space, boolean trapped){
		
		List<ITreasureChest> chests = new ArrayList<ITreasureChest>();
		
		Collections.shuffle(space, rand);
		
		int count = 0;
		
		for (Coord block : space){
			
			if(count == numChests){
				break;
			}
			
			if (isValidChestSpace(editor, block)) {
				ITreasureChest chest = generate(editor, rand, settings, block, getChestType(rand, Dungeon.getLevel(block.getY())));
				chests.add(chest);
				count++;
			}
		}
		
		return chests;
	}
	
	public static List<ITreasureChest> createChests(WorldEditor editor, Random rand, LevelSettings settings, int numChests, List<Coord> space, List<Treasure> types){
		
		List<ITreasureChest> chests = new ArrayList<ITreasureChest>();
		
		Collections.shuffle(space, rand);
		
		int count = 0;
		
		for (Coord block : space){
			
			if(count == numChests){
				break;
			}
			
			if (isValidChestSpace(editor, block)) {
				ITreasureChest chest = generate(editor, rand, settings, block, types.get(rand.nextInt(types.size())));
				chests.add(chest);
				count++;
			}
		}
		
		return chests;
	}
	
	private static Treasure getChestType(Random rand, int level){		
		
		switch(level){
		
		case 0:
			
			if(rand.nextInt(30) == 0){
				return ENCHANTING;
			}
			
			if(rand.nextInt(10) == 0){
				return SUPPLIES;
			}
			
			return level0.get(rand.nextInt(level0.size()));
			
		case 1:
			
			if(rand.nextInt(20) == 0){
				return ENCHANTING;
			}
			
			return level1.get(rand.nextInt(level1.size()));
						
		case 2:
			
			if(rand.nextInt(10) == 0){
				return ENCHANTING;
			}
			
			return level2.get(rand.nextInt(level2.size()));
		
		case 3:
			
			if(rand.nextInt(10) == 0){
				return ENCHANTING;
			}
			
			return level3.get(rand.nextInt(level3.size()));
			
		case 4:
			
			if(rand.nextInt(10) == 0){
				return ENCHANTING;
			}
			
			return level4.get(rand.nextInt(level4.size()));
		
		default:
			return FOOD;
		}
		
	}

	public static boolean isValidChestSpace(WorldEditor editor, Coord pos) {

		if (!editor.isAirBlock(pos)) {
			return false;
		}
		
		Coord cursor;
		cursor = new Coord(pos);
		cursor.add(Cardinal.DOWN);
		
		if (!editor.getBlock(cursor).getBlock().getMaterial().isSolid()) return false;
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(pos);
			cursor.add(dir);
			if(editor.getBlock(cursor).getBlock() == Blocks.chest) return false;
		}
		
		return true;
	}
	
	public static void fillChest(ITreasureChest chest){
		switch(chest.getType()){
		case ARMOUR:
		case WEAPONS:
		case BLOCKS:
		case ENCHANTING:
		case FOOD:
		case ORE:
		case POTIONS:
		case STARTER:
		case TOOLS:
		case SUPPLIES:
		case SMITH:
		case MUSIC:
		case SPECIAL:
		case REWARD:
		case EMPTY:
		default:
		}
	}
}
