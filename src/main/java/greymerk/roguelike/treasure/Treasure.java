package greymerk.roguelike.treasure;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ILoot;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public enum Treasure {

	ARMOUR, WEAPONS, BLOCKS, ENCHANTING, FOOD, ORE, POTIONS, STARTER, TOOLS, SUPPLIES, SMITH, MUSIC, SPECIAL, REWARD, EMPTY;
	
	public static final List<Treasure> level0 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS, FOOD));
	public static final List<Treasure> level1 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS, FOOD));
	public static final List<Treasure> level2 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	public static final List<Treasure> level3 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	public static final List<Treasure> level4 = new ArrayList<Treasure>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	
	public static ITreasureChest generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, int level, boolean trapped){
		
		Treasure type = getChestType(rand, level);
		ITreasureChest chest = new TreasureChest(type);
		
		return chest.generate(editor, rand, pos, level, trapped);
	}
	
	public static ITreasureChest generate(WorldEditor editor,  Random rand, LevelSettings settings, Coord pos){
		return generate(editor, rand, settings, pos, settings.getDifficulty(pos), false);
	}
	
	public static ITreasureChest generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, Treasure type){
		return generate(editor, rand, settings, pos, type, settings.getDifficulty(pos), false);
	}
	
	public static ITreasureChest generate(WorldEditor editor, Random rand, LevelSettings settings, Coord pos, Treasure type, int level, boolean trapped){
		ITreasureChest chest = new TreasureChest(type);
		chest.generate(editor, rand, pos, level, trapped);
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
	
	public static void fillChest(ITreasureChest chest, Random rand, ILoot loot){
		
		int level = chest.getLevel();
		
		if(chest.getType() == EMPTY) return;
		
		int middle = chest.getSize()/2;
		switch(chest.getType()){
		case ARMOUR:
			chest.setSlot(middle - 1, loot.get(rand, Loot.POTION, level));
			chest.setSlot(middle, loot.get(rand, Loot.ARMOUR, level));
			chest.setSlot(middle + 1, loot.get(rand, Loot.FOOD, level));
			break;
		case WEAPONS:
			chest.setSlot(middle - 1, loot.get(rand, Loot.POTION, level));
			chest.setSlot(middle, loot.get(rand, Loot.WEAPON, level));
			chest.setSlot(middle + 1, loot.get(rand, Loot.FOOD, level));
			break;
		case BLOCKS:
			for(int i = 0; i < 8; ++i){
				chest.setRandomEmptySlot(loot.get(rand, Loot.BLOCK, level));
			}
			break;
		case ENCHANTING:
			chest.setSlot(middle - 1, loot.get(rand, Loot.ENCHANTBONUS, level));
			chest.setSlot(middle, loot.get(rand, Loot.ENCHANTBOOK, level));
			chest.setSlot(middle + 1, loot.get(rand, Loot.ENCHANTBONUS, level));
			break;
		case FOOD:
			for(int i = 0; i < 12; ++i){
				chest.setRandomEmptySlot(loot.get(rand, Loot.FOOD, level));
			}
			break;
		case ORE:
			chest.setSlot(middle - 1, loot.get(rand, Loot.ORE, level));
			chest.setSlot(middle, loot.get(rand, Loot.ORE, level));
			chest.setSlot(middle + 1, loot.get(rand, Loot.ORE, level));
			break;
		case POTIONS:
			for(int i = 0; i < 8; ++i){
				chest.setRandomEmptySlot(loot.get(rand, Loot.POTION, level));
			}
			break;
		case STARTER:
			for(int i = 0; i < 8; ++i){
				if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
					chest.setRandomEmptySlot(getStarterLoot(loot, i, rand));
				} else {
					chest.setRandomEmptySlot(loot.get(rand, Loot.JUNK, level));
				}
			}
			break;
		case TOOLS:
			chest.setSlot(middle - 1, loot.get(rand, Loot.ORE, level));
			chest.setSlot(middle, loot.get(rand, Loot.TOOL, level));
			chest.setSlot(middle + 1, loot.get(rand, Loot.ORE, level));
			break;
		case SUPPLIES:
			for(int i = 0; i < 8; ++i){
				chest.setRandomEmptySlot(loot.get(rand, Loot.SUPPLY, level));
			}
			break;
		case SMITH:
			chest.setSlot(middle - 1, loot.get(rand, Loot.ORE, level));
			chest.setSlot(middle, loot.get(rand, Loot.SMITHY, level));
			chest.setSlot(middle + 1, loot.get(rand, Loot.ORE, level));
			break;
		case MUSIC:
			chest.setSlot(middle, loot.get(rand, Loot.MUSIC, level));
			break;
		case SPECIAL:
			chest.setSlot(middle, loot.get(rand, Loot.SPECIAL, level));
			break;
		case REWARD:
			chest.setSlot(middle, loot.get(rand, Loot.REWARD, level));
			break;
		default:
		}
		
		int amount = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 9 : 4;
		for(int i = 0; i < amount; ++i){
			chest.setRandomEmptySlot(loot.get(rand, Loot.JUNK, level));
		}
	}
	
	public static ItemStack getStarterLoot(ILoot loot, int choice, Random rand){
		
		if(!RogueConfig.getBoolean(RogueConfig.GENEROUS)) return loot.get(rand, Loot.JUNK, 0);
		
		switch (choice){
		case 4: return loot.get(rand, Loot.TOOL, 0);
		case 3: return loot.get(rand, Loot.WEAPON, 0);
		case 2: return loot.get(rand, Loot.FOOD, 0);
		case 1: if(RogueConfig.getBoolean(RogueConfig.GENEROUS)) return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, Quality.WOOD);
		default: return new ItemStack(Blocks.torch, 1 + rand.nextInt(RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 7 : 3));
		}
	}
}
