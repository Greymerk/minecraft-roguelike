package greymerk.roguelike.catacomb.dungeon.room;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureChestBase;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;



public class DungeonMess implements IDungeon {

	private static MetaBlock log = new MetaBlock(Block.wood.blockID, 1);
	private static MetaBlock plank = new MetaBlock(Block.planks.blockID, 1);
	private static int stairBrick = Block.stairsBrick.blockID;
	private static int stairSpruce = Block.stairsWoodSpruce.blockID;
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		
		// air		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y, z - 6, x + 6, y + 2, z + 6, 0);

		// ceiling
		WorldGenPrimitive.fillRectSolid(world, x - 6, y + 3, z - 6, x + 6, y + 4, z + 6, plank, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 3, z - 2, x + 4, y + 3, z - 2, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 3, z + 2, x + 4, y + 3, z + 2, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 3, z - 4, x - 2, y + 3, z + 4, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y + 3, z - 4, x + 2, y + 3, z + 4, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 3, z - 4, x + 1, y + 3, z + 4, 0);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 3, z - 1, x + 4, y + 3, z + 1, 0);
		
		MetaBlock brownClay = new MetaBlock(Block.stainedClay.blockID, 12);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, brownClay, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 6, x + 1, y - 1, z + 6, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 1, x - 2, y - 1, z + 1, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z - 1, x + 5, y - 1, z + 1, log, true, true);

		
		
		// walls
		WorldGenPrimitive.fillRectSolid(world, x + 7, y, z - 2, x + 7, y + 2, z + 6, plank, false, true);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z - 6, x - 7, y + 2, z + 6, plank, false, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y, z - 7, x + 2, y + 2, z - 7, plank, false, true);
		WorldGenPrimitive.fillRectSolid(world, x - 6, y, z + 7, x + 6, y + 2, z + 7, plank, false, true);
		
		// pillars
		for(int i = - 6; i <= 6; i = i += 4){
			for(int j = - 6; j <= 6; j += 4){
				pillar(world, x + i, y, z + j);
			}
		}
				
		// stove
		stove(world, rand, x + 4, y, z - 4);
		
		// storage
		storage(world, rand, x + 4, y, z + 4);
		
		// table north
		northTable(world, rand, x - 4, y, z - 4);
		
		
		// table south
		southTable(world, rand, x - 4, y, z + 4);

		return true;
	}

	private void stove(World world, Random rand, int x, int y, int z){
		int brick = Block.brick.blockID;
		
		// floor
		
		// fire pit
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 4, x + 2, y - 1, z + 1, brick);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 4, x + 1, y + 2, z - 3, brick);
		WorldGenPrimitive.setBlock(world, x - 1, y, z - 2, stairBrick, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, x - 1, y + 1, z - 2, stairBrick, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 1, y, z - 2, stairBrick, WorldGenPrimitive.blockOrientation(Cardinal.WEST, false), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 1, z - 2, stairBrick, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 2, z - 2, x + 1, y + 2, z - 2, brick);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 2, z - 1, x + 2, y + 2, z - 1, stairBrick, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x, y - 1, z - 3, Block.netherrack.blockID);
		WorldGenPrimitive.setBlock(world, x, y, z - 3, Block.fire.blockID);
		WorldGenPrimitive.setBlock(world, x, y + 1, z - 3, stairBrick, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);

		// furnace
		WorldGenPrimitive.fillRectSolid(world, x + 2, y, z - 1, x + 2, y + 2, z + 1, brick);
		WorldGenPrimitive.fillRectSolid(world, x + 1, y + 2, z - 1, x + 1, y + 2, z + 1, stairBrick, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y, z, Block.furnaceIdle.blockID);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z, stairBrick, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		// ceiling
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 3, z - 1, x + 1, y + 3, z + 1, brick);
	}
	
	private void storage(World world, Random rand, int x, int y, int z){
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// east shelf
		WorldGenPrimitive.setBlock(world, x + 2, y, z - 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y, z, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y, z + 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		new TreasureChestFoodStore().generate(world, rand, x + 2, y + 1, z, 1, false);
		
		// south shelf
		WorldGenPrimitive.setBlock(world, x - 1, y, z + 2, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y, z + 2, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z + 2, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		new TreasureChestFoodStore().generate(world, rand, x, y + 1, z + 2, 1, false);
	}
	
	private void northTable(World world, Random rand, int x, int y, int z){
		// floor
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 2, x + 1, y, z - 2, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, false), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y, z + 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		
		// table
		WorldGenPrimitive.setBlock(world, x, y, z, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z + 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y, z + 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Block.torchWood.blockID);
	}

	private void southTable(World world, Random rand, int x, int y, int z){
		// floor
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 2, x + 1, y, z + 2, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y, z + 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		
		// table
		WorldGenPrimitive.setBlock(world, x, y, z - 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z - 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y, z, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Block.torchWood.blockID);
				
	}
	
	private static void pillar(World world, int x, int y, int z){
		
		WorldGenPrimitive.fillRectSolid(world, x, y, z, x, y + 2, z, log, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 2, z + 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 2, z - 1, stairSpruce, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 3, z - 1, x + 1, y + 3, z + 1, plank, true, true);
		
	}
	
	private class TreasureChestFoodStore extends TreasureChestBase{

		@Override
		protected void fillChest(TileEntityChest chest, int level) {
			ItemStack item;
			
			int stacks = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? chest.getSizeInventory() : 12; 
			
			for (int i = 0; i < stacks; i++) {
				if(rand.nextInt(10) < 8){
					item = Loot.getLoot(Loot.FOOD, rand, level);
					chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);	
				}
			}
		}
		
	}
	
	public int getSize(){
		return 10;
	}
}
