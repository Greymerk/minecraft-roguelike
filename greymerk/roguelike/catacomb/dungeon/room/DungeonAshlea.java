package greymerk.roguelike.catacomb.dungeon.room;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureChestBase;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;



public class DungeonAshlea implements IDungeon {

	private static MetaBlock log = Log.getLog(Log.BIRCH, Cardinal.UP, false);
	private static MetaBlock plank = new MetaBlock(Block.planks.blockID, 2);
	private static int stairBrick = Block.stairsBrick.blockID;
	private static int stairBirch = Block.stairsWoodBirch.blockID;
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		
		// air		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y, z - 6, x + 6, y + 2, z + 6, 0);

		// ceiling
		WorldGenPrimitive.fillRectSolid(world, x - 6, y + 3, z - 6, x + 6, y + 4, z + 6, plank, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 3, z - 2, x + 4, y + 3, z - 2, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 3, z + 2, x + 4, y + 3, z + 2, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 3, z - 4, x - 2, y + 3, z + 4, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y + 3, z - 4, x + 2, y + 3, z + 4, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 3, z - 4, x + 1, y + 3, z + 4, 0);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 3, z - 1, x + 4, y + 3, z + 1, 0);
		
		MetaBlock pinkClay = new MetaBlock(Block.stainedClay.blockID, 2);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, pinkClay, true, true);
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
		WorldGenPrimitive.setBlock(world, x + 2, y, z - 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y, z, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y, z + 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		ITreasureChest ashlea = new TreasureChestAshlea().generate(world, rand, x + 2, y + 1, z, 2, false);
		ashlea.setInventorySlot(ItemNovelty.getItem(ItemNovelty.ASHLEA), (ashlea.getInventorySize() / 2));
		
		// south shelf
		WorldGenPrimitive.setBlock(world, x - 1, y, z + 2, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y, z + 2, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z + 2, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		ITreasureChest rleahy = new TreasureChestAshlea().generate(world, rand, x, y + 1, z + 2, 2, false);
		rleahy.setInventorySlot(ItemNovelty.getItem(ItemNovelty.RLEAHY), (rleahy.getInventorySize() / 2));
	}
	
	private void northTable(World world, Random rand, int x, int y, int z){
		// floor
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z - 2, x + 1, y, z - 2, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, false), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y, z + 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		
		// table
		WorldGenPrimitive.setBlock(world, x, y, z, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z + 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y, z + 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Block.torchWood.blockID);
	}

	private void southTable(World world, Random rand, int x, int y, int z){
		// floor
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		WorldGenPrimitive.fillRectSolid(world, x - 1, y, z + 2, x + 1, y, z + 2, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, false), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 1, x - 2, y, z + 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.EAST, false), 2, true, true);
		
		// table
		WorldGenPrimitive.setBlock(world, x, y, z - 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z - 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y, z, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Block.torchWood.blockID);
				
	}
	
	private static void pillar(World world, int x, int y, int z){
		
		WorldGenPrimitive.fillRectSolid(world, x, y, z, x, y + 2, z, log, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 2, z + 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 2, z - 1, stairBirch, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 3, z - 1, x + 1, y + 3, z + 1, plank, true, true);
		
	}
	
	private class TreasureChestAshlea extends TreasureChestBase{

		@Override
		protected void fillChest(TileEntityChest chest, int level) {
			ItemStack item;
			
			int stacks = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? chest.getSizeInventory() : 12; 
			
			for (int i = 0; i < stacks; i++) {
				if(rand.nextInt(10) < 8){
					item = ItemFood.getDessert(rand);
					chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);	
				}
			}
		}
		
	}
	
	public int getSize(){
		return 10;
	}
}
