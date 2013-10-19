package greymerk.roguelike;


import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public enum TreasureChest {

	ARMOUR, WEAPONS, BLOCKS, ENCHANTING, FOOD, NOVELTY, ORE, POTIONS, STARTER, TOOLS, SUPPLIES, SMITH;
	
	public static final TreasureChest[] rank0 = {ORE, TOOLS, ARMOUR, WEAPONS, FOOD, SUPPLIES, BLOCKS};
	public static final TreasureChest[] rank1 = {ORE, TOOLS, ARMOUR, WEAPONS, FOOD, BLOCKS};
	public static final TreasureChest[] rank2 = {ORE, TOOLS, ARMOUR, WEAPONS, BLOCKS};
	public static final TreasureChest[] rank3 = {ORE, TOOLS, ARMOUR, WEAPONS};
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ){
		generate(world, rand, posX, posY, posZ, false);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, boolean trapped){
		int rank = Dungeon.getRank(posY);
		
		TreasureChest type = getChestType(rand, rank);
		ITreasureChest chest = getChest(type);
		
		chest.generate(world, rand, posX, posY, posZ, trapped);
	}
	
	public static void generate(World world, Random rand, int posX, int posY, int posZ, TreasureChest type){
		int rank = Dungeon.getRank(posY);
		
		ITreasureChest chest = getChest(type);
		
		chest.generate(world, rand, posX, posY, posZ, false);
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
				generate(world, rand, x, y, z, getChestType(rand, Dungeon.getRank(y)));
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
		case NOVELTY: return new TreasureChestNovelty();
		case ORE: return new TreasureChestOre();
		case POTIONS: return new TreasureChestPotions();
		case STARTER: return new TreasureChestStarter();
		case TOOLS: return new TreasureChestTools();
		case SUPPLIES: return new TreasureChestSupplies();
		case SMITH: return new TreasureChestSmithy();
		default: return new TreasureChestFood();
		}
	}

	private static TreasureChest getChestType(Random rand, int rank){		
		
		if(rand.nextInt(30) == 0){
			return NOVELTY;
		}
		
		switch(rank){
		
		case 0:
			
			if(rand.nextInt(30) == 0){
				return ENCHANTING;
			}
			
			if(rand.nextInt(10) == 0){
				return SUPPLIES;
			}
			
			return rank0[rand.nextInt(rank0.length)];
			
		case 1:
			
			if(rand.nextInt(20) == 0){
				return ENCHANTING;
			}
			
			return rank1[rand.nextInt(rank1.length)];
						
		case 2:
			
			if(rand.nextInt(10) == 0){
				return ENCHANTING;
			}
			
			return rank2[rand.nextInt(rank2.length)];
		
		case 3:
			
			if(rand.nextInt(10) == 0){
				return ENCHANTING;
			}
			
			return rank3[rand.nextInt(rank3.length)];
		
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
