package greymerk.roguelike.treasure;


import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.worldgen.Coord;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public enum TreasureChest {

	ARMOUR, WEAPONS, BLOCKS, ENCHANTING, FOOD, ORE, POTIONS, STARTER, TOOLS, SUPPLIES, SMITH, MUSIC;
	
	public static final TreasureChest[] level0 = {ORE, TOOLS, ARMOUR, WEAPONS, FOOD, SUPPLIES, BLOCKS};
	public static final TreasureChest[] level1 = {ORE, TOOLS, ARMOUR, WEAPONS, FOOD, BLOCKS};
	public static final TreasureChest[] level2 = {ORE, TOOLS, ARMOUR, WEAPONS, BLOCKS};
	public static final TreasureChest[] level3 = {ORE, TOOLS, ARMOUR, WEAPONS, BLOCKS};
	public static final TreasureChest[] level4 = {ORE, TOOLS, ARMOUR, WEAPONS};
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ){
		generate(world, rand, posX, posY, posZ, Catacomb.getLevel(posY), false);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, int level, boolean trapped){
		
		TreasureChest type = getChestType(rand, level);
		ITreasureChest chest = getChest(type);
		
		chest.generate(world, rand, posX, posY, posZ, level, trapped);
	}

	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, TreasureChest type){
		generate(world, rand, posX, posY, posZ, type, Catacomb.getLevel(posY), false);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, TreasureChest type, int level, boolean trapped){
		ITreasureChest chest = getChest(type);
		chest.generate(world, rand, posX, posY, posZ, level, trapped);
	}
	
	public static void generate(World world, Random rand, List<Coord> space, TreasureChest type){
		createChests(world, rand, 1, space, new TreasureChest[]{type});
	}

	public static void createChests(World world, Random rand, int numChests, List<Coord> space){
		createChests(world, rand, numChests, space, false);
	}
	
	public static void createChests(World world, Random rand, int numChests, List<Coord> space, boolean trapped){
		
		Collections.shuffle(space);
		
		int count = 0;
		
		for (Coord block : space){
			
			if(count == numChests){
				break;
			}
			
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if (isValidChestSpace(world, x, y, z)) {
				generate(world, rand, x, y, z, getChestType(rand, Catacomb.getLevel(y)));
				count++;
			}
		}
	}
	
	public static void createChests(World world, Random rand, int numChests, List<Coord> space, TreasureChest[] types){
		
		Collections.shuffle(space);
		
		int count = 0;
		
		for (Coord block : space){
			
			if(count == numChests){
				break;
			}
			
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if (isValidChestSpace(world, x, y, z)) {
				generate(world, rand, x, y, z, types[rand.nextInt(types.length)]);
				count++;
			}
		}
	}
	
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
		default: return new TreasureChestFood();
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
			
			return level0[rand.nextInt(level0.length)];
			
		case 1:
			
			if(rand.nextInt(20) == 0){
				return ENCHANTING;
			}
			
			return level1[rand.nextInt(level1.length)];
						
		case 2:
			
			if(rand.nextInt(10) == 0){
				return ENCHANTING;
			}
			
			return level2[rand.nextInt(level2.length)];
		
		case 3:
			
			if(rand.nextInt(10) == 0){
				return ENCHANTING;
			}
			
			return level3[rand.nextInt(level3.length)];
			
		case 4:
			
			if(rand.nextInt(10) == 0){
				return ENCHANTING;
			}
			
			return level4[rand.nextInt(level4.length)];
		
		default:
			return FOOD;
		}
		
	}

	public static boolean isValidChestSpace(World world, int x, int y, int z) {

		if (!world.isAirBlock(x, y, z)) {
			return false;
		}
		
		if (!world.getBlockMaterial(x, y - 1, z).isSolid()) {
			return false;
		}
				
		if(world.getBlockId(x - 1, y, z) == Block.chest.blockID){
			return false;
		}
		
		if(world.getBlockId(x + 1, y, z) == Block.chest.blockID){
			return false;
		}
		
		if(world.getBlockId(x, y, z - 1) == Block.chest.blockID){
			return false;
		}

		if(world.getBlockId(x, y, z + 1) == Block.chest.blockID){
			return false;
		}
		
		return true;
	}
}
