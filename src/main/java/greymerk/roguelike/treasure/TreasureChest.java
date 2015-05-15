package greymerk.roguelike.treasure;


import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public enum TreasureChest {

	ARMOUR, WEAPONS, BLOCKS, ENCHANTING, FOOD, ORE, POTIONS, STARTER, TOOLS, SUPPLIES, SMITH, MUSIC, SPECIAL, REWARD;
	
	public static final List<TreasureChest> level0 = new ArrayList<TreasureChest>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS, FOOD));
	public static final List<TreasureChest> level1 = new ArrayList<TreasureChest>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS, FOOD));
	public static final List<TreasureChest> level2 = new ArrayList<TreasureChest>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	public static final List<TreasureChest> level3 = new ArrayList<TreasureChest>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	public static final List<TreasureChest> level4 = new ArrayList<TreasureChest>(Arrays.asList(ORE, TOOLS, ARMOUR, WEAPONS));
	
	private static ITreasureChest getChest(TreasureChest type) {
		
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
	
	public static void generate(World world, Random rand, CatacombLevelSettings settings, Coord pos, int level, boolean trapped){
		
		TreasureChest type = getChestType(rand, level);
		ITreasureChest chest = getChest(type);
		
		chest.generate(world, rand, settings.getLoot(), pos, level, trapped);
	}
	
	public static void generate(World world, Random rand, CatacombLevelSettings settings, Coord pos){
		generate(world, rand, settings, pos, settings.getDifficulty(pos), false);
	}
	
	public static void generate(World world, Random rand, CatacombLevelSettings settings, Coord pos, TreasureChest type){
		generate(world, rand, settings, pos, type, Catacomb.getLevel(pos.getY()), false);
	}
	
	public static void generate(World world, Random rand, CatacombLevelSettings settings, Coord pos, TreasureChest type, int level, boolean trapped){
		ITreasureChest chest = getChest(type);
		chest.generate(world, rand, settings.getLoot(), pos, level, trapped);
	}
	
	public static void generate(World world, Random rand, CatacombLevelSettings settings, List<Coord> space, TreasureChest type){
		createChests(world, rand, settings, 1, space, new ArrayList<TreasureChest>(Arrays.asList(type)));
	}
	
	public static void createChests(World world, Random rand, CatacombLevelSettings settings, int numChests, List<Coord> space){
		createChests(world, rand, settings, numChests, space, false);
	}
	
	public static void createChests(World world, Random rand, CatacombLevelSettings settings, int numChests, List<Coord> space, boolean trapped){
		
		Collections.shuffle(space, rand);
		
		int count = 0;
		
		for (Coord block : space){
			
			if(count == numChests){
				break;
			}
			
			if (isValidChestSpace(world, block)) {
				generate(world, rand, settings, block, getChestType(rand, Catacomb.getLevel(block.getY())));
				count++;
			}
		}
	}
	
	public static void createChests(World world, Random rand, CatacombLevelSettings settings, int numChests, List<Coord> space, List<TreasureChest> types){
		
		Collections.shuffle(space, rand);
		
		int count = 0;
		
		for (Coord block : space){
			
			if(count == numChests){
				break;
			}
			
			if (isValidChestSpace(world, block)) {
				generate(world, rand, settings, block, types.get(rand.nextInt(types.size())));
				count++;
			}
		}
	}
	
	private static TreasureChest getChestType(Random rand, int level){		
		
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

	public static boolean isValidChestSpace(World world, Coord pos) {

		if (!world.isAirBlock(pos)) {
			return false;
		}
		
		Coord cursor;
		cursor = new Coord(pos);
		cursor.add(Cardinal.DOWN);
		
		if (!WorldGenPrimitive.getBlock(world, cursor).getBlock().getMaterial().isSolid()) return false;
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(pos);
			cursor.add(dir);
			if(WorldGenPrimitive.getBlock(world, cursor).getBlock() == Blocks.chest) return false;
		}
		
		return true;
	}
}
