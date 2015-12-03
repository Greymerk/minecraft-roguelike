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
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
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
	
	private static ITreasureChest getChest(Treasure type) {
		return new TreasureChest(type);
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
	
	public static void fillChest(ITreasureChest chest, Random rand, LootSettings loot, int level){
		
		if(chest.getType() == EMPTY) return;
		
		int middle = chest.getSize()/2;
		switch(chest.getType()){
		case ARMOUR:
			chest.setSlot(middle - 1, loot.get(Loot.POTION, rand));
			chest.setSlot(middle, loot.get(Loot.ARMOUR, rand));
			chest.setSlot(middle + 1, loot.get(Loot.FOOD, rand));
			break;
		case WEAPONS:
			chest.setSlot(middle - 1, loot.get(Loot.POTION, rand));
			chest.setSlot(middle, loot.get(Loot.WEAPON, rand));
			chest.setSlot(middle + 1, loot.get(Loot.FOOD, rand));
			break;
		case BLOCKS:
		case ENCHANTING:
			chest.setSlot(middle - 1, loot.get(Loot.ENCHANTBONUS, rand));
			chest.setSlot(middle, loot.get(Loot.ENCHANTBOOK, rand));
			chest.setSlot(middle + 1, loot.get(Loot.ENCHANTBONUS, rand));
			break;
		case FOOD:
			for(int i = 0; i < 12; ++i){
				chest.setRandomEmptySlot(loot.get(Loot.FOOD, rand));
			}
			break;
		case ORE:
			chest.setSlot(middle - 1, loot.get(Loot.ORE, rand));
			chest.setSlot(middle, loot.get(Loot.ORE, rand));
			chest.setSlot(middle + 1, loot.get(Loot.ORE, rand));
			break;
		case POTIONS:
			for(int i = 0; i < 8; ++i){
				chest.setRandomEmptySlot(loot.get(Loot.POTION, rand));
			}
			break;
		case STARTER:
			for(int i = 0; i < 8; ++i){
				if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
					chest.setRandomEmptySlot(getStarterLoot(loot, i, rand));
				} else {
					chest.setRandomEmptySlot(loot.get(Loot.JUNK, rand));
				}
			}
			break;
		case TOOLS:
			chest.setSlot(middle - 1, loot.get(Loot.ORE, rand));
			chest.setSlot(middle, loot.get(Loot.TOOL, rand));
			chest.setSlot(middle + 1, loot.get(Loot.ORE, rand));
			break;
		case SUPPLIES:
			for(int i = 0; i < 8; ++i){
				chest.setRandomEmptySlot(loot.get(Loot.SUPPLY, rand));
			}
			break;
		case SMITH:
			chest.setSlot(middle - 1, loot.get(Loot.ORE, rand));
			chest.setSlot(middle, loot.get(Loot.SMITHY, rand));
			chest.setSlot(middle + 1, loot.get(Loot.ORE, rand));
			break;
		case MUSIC:
			chest.setSlot(middle, loot.get(Loot.MUSIC, rand));
			break;
		case SPECIAL:
			chest.setSlot(middle, loot.get(Loot.SPECIAL, rand));
			break;
		case REWARD:
			chest.setSlot(middle, loot.get(Loot.REWARD, rand));
			break;
		default:
		}
		
		int amount = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 9 : 4;
		for(int i = 0; i < amount; ++i){
			chest.setRandomEmptySlot(loot.get(Loot.JUNK, rand));
		}
	}
	
	public static ItemStack getStarterLoot(LootSettings loot, int choice, Random rand){
		
		if(!RogueConfig.getBoolean(RogueConfig.GENEROUS)) return loot.get(Loot.JUNK, rand);
		
		switch (choice){
		case 4: return loot.get(Loot.TOOL, rand);
		case 3: return loot.get(Loot.WEAPON, rand);
		case 2: return loot.get(Loot.FOOD, rand);
		case 1: if(RogueConfig.getBoolean(RogueConfig.GENEROUS)) return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, Quality.WOOD);
		default: return new ItemStack(Blocks.torch, 1 + rand.nextInt(RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 7 : 3));
		}
	}
}
