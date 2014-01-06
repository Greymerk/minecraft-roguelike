package greymerk.roguelike.catacomb.dungeon.room;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.treasure.loot.ItemNovelty;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class DungeonEniko implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z - 5, x + 5, y + 4, z + 5, 0);
		
		pillar(world, x - 5, y, z - 5);
		pillar(world, x - 5, y, z - 2);
		pillar(world, x - 5, y, z + 2);
		pillar(world, x - 5, y, z + 5);
		
		pillar(world, x - 2, y, z - 5);
		pillar(world, x - 2, y, z + 5);
		
		pillar(world, x + 2, y, z - 5);
		pillar(world, x + 2, y, z + 5);
		
		pillar(world, x + 5, y, z - 5);
		pillar(world, x + 5, y, z - 2);
		pillar(world, x + 5, y, z + 2);
		pillar(world, x + 5, y, z + 5);

		MetaBlock shelf = new MetaBlock(Block.stairsStoneBrick.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.fillRectSolid(world, x - 6, y + 1, z - 4, x - 6, y + 3, z - 3, Block.coalBlock.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z - 4, x - 5, y, z - 3, shelf, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 6, y + 1, z + 3, x - 6, y + 3, z + 4, Block.coalBlock.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z + 3, x - 5, y, z + 4, shelf, true, true);
		
		shelf.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.fillRectSolid(world, x + 6, y + 1, z - 4, x + 6, y + 3, z - 3, Block.coalBlock.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y, z - 4, x + 5, y, z - 3, shelf, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 6, y + 1, z + 3, x + 6, y + 3, z + 4, Block.coalBlock.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y, z + 3, x + 5, y, z + 4, shelf, true, true);
		
		shelf.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 1, z - 6, x - 3, y + 3, z - 6, Block.coalBlock.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y, z - 5, x - 3, y, z - 5, shelf, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 1, z - 6, x + 4, y + 3, z - 6, Block.coalBlock.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y, z - 5, x + 4, y, z - 5, shelf, true, true);
		
		shelf.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 1, z + 6, x - 3, y + 3, z + 6, Block.coalBlock.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y, z + 5, x - 3, y, z + 5, shelf, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 1, z + 6, x + 4, y + 3, z + 6, Block.coalBlock.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y, z + 5, x + 4, y, z + 5, shelf, true, true);
		ITreasureChest eniChest = new TreasureChestEmpty().generate(world, rand, x + 3, y + 1, z + 5);
		if(rand.nextBoolean()){
			eniChest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.ENIKOBOW), eniChest.getInventorySize() / 2);
		} else {
			eniChest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.ENIKOSWORD), eniChest.getInventorySize() / 2);
		}
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 5, x + 5, y - 1, z + 5, Block.stoneBrick.blockID);
				
		MetaBlock blockOne = new MetaBlock(Block.blockNetherQuartz.blockID);
		MetaBlock blockTwo = new MetaBlock(Block.blockLapis.blockID);
		BlockFactoryCheckers checkers = new BlockFactoryCheckers(blockOne, blockTwo);
		
		WorldGenPrimitive.fillRectSolid(world, x - 3, y - 1, z - 3, x + 3, y - 1, z + 3, checkers);
		
		WorldGenPrimitive.setBlock(world, x - 2, y - 1, z - 2, Block.redstoneLampActive.blockID);
		WorldGenPrimitive.setBlock(world, x - 2, y - 2, z - 2, Block.blockRedstone.blockID);
		
		WorldGenPrimitive.setBlock(world, x - 2, y - 1, z + 2, Block.redstoneLampActive.blockID);
		WorldGenPrimitive.setBlock(world, x - 2, y - 2, z + 2, Block.blockRedstone.blockID);
		
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z - 2, Block.redstoneLampActive.blockID);
		WorldGenPrimitive.setBlock(world, x + 2, y - 2, z - 2, Block.blockRedstone.blockID);
		
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z + 2, Block.redstoneLampActive.blockID);
		WorldGenPrimitive.setBlock(world, x + 2, y - 2, z + 2, Block.blockRedstone.blockID);
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, x - 6, y + 4, z - 6, x - 4, y + 4, z + 6, Block.netherBrick.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 3, y + 4, z - 6, x + 4, y + 4, z - 4, Block.netherBrick.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 3, y + 4, z + 4, x + 4, y + 4, z + 6, Block.netherBrick.blockID);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y + 4, z - 6, x + 6, y + 4, z + 6, Block.netherBrick.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 5, z - 4, x + 4, y + 5, z + 4, Block.netherBrick.blockID);
		
		MetaBlock upsideDownNetherSlab = new MetaBlock(44, 6 | 8);
		
		WorldGenPrimitive.fillRectSolid(world, x - 3, y + 4, z - 3, x - 3, y + 4, z + 3, upsideDownNetherSlab, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 4, z - 3, x + 3, y + 4, z + 3, upsideDownNetherSlab, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 4, z - 3, x + 2, y + 4, z - 3, upsideDownNetherSlab, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 4, z + 3, x + 2, y + 4, z + 3, upsideDownNetherSlab, true, true);
		
		return true;
	}

	
	private static void pillar(World world, int x, int y, int z){
		
		WorldGenPrimitive.fillRectSolid(world, x, y, z, x, y + 3, z, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x + 1, y + 3, z, Block.stairsStoneBrick.blockID, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true), 2, true, false);
		WorldGenPrimitive.setBlock(world, x - 1, y + 3, z, Block.stairsStoneBrick.blockID, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true), 2, true, false);
		WorldGenPrimitive.setBlock(world, x, y + 3, z + 1, Block.stairsStoneBrick.blockID, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true), 2, true, false);
		WorldGenPrimitive.setBlock(world, x, y + 3, z - 1, Block.stairsStoneBrick.blockID, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true), 2, true, false);
		
	}
	
	public int getSize(){
		return 7;
	}
	
}
