package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.BlockFactoryProvider;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class DungeonsPrison implements IDungeon {

	World world;
	Random rand;
	IBlockFactory blocks;
	
	public DungeonsPrison(){}
	
	@Override
	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		
		world = inWorld;
		rand = inRandom;
		
		blocks = BlockFactoryProvider.getRandomizer(Catacomb.getLevel(inOriginY), inRandom);
		
		// clear air
		WorldGenPrimitive.fillRectSolid(inWorld, inOriginX - 7, inOriginY, inOriginZ - 7, inOriginX + 7, inOriginY + 3, inOriginZ + 7, 0);
		
		// create outer walls
		WorldGenPrimitive.fillRectHollow(world, inOriginX - 8, inOriginY - 1, inOriginZ - 8, inOriginX + 8, inOriginY + 5, inOriginZ + 8, blocks, false, true);
		
		// fill hallway ceiling beams
		WorldGenPrimitive.fillRectSolid(world, inOriginX - 7, inOriginY + 3, inOriginZ - 2, inOriginX + 7, inOriginY + 3, inOriginZ - 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, inOriginX - 7, inOriginY + 3, inOriginZ + 2, inOriginX + 7, inOriginY + 3, inOriginZ + 2, blocks);
		
		WorldGenPrimitive.fillRectSolid(world, inOriginX - 2, inOriginY + 3, inOriginZ - 7, inOriginX - 2, inOriginY + 3, inOriginZ + 7, blocks);
		WorldGenPrimitive.fillRectSolid(world, inOriginX + 2, inOriginY + 3, inOriginZ - 7, inOriginX + 2, inOriginY + 3, inOriginZ + 7, blocks);
		
		// fill hallway roofs
		WorldGenPrimitive.fillRectSolid(world, inOriginX - 7, inOriginY + 4, inOriginZ - 1, inOriginX - 2, inOriginY + 4, inOriginZ + 1, blocks);
		WorldGenPrimitive.fillRectSolid(world, inOriginX + 2, inOriginY + 4, inOriginZ - 1, inOriginX + 7, inOriginY + 4, inOriginZ + 1, blocks);

		WorldGenPrimitive.fillRectSolid(world, inOriginX - 1, inOriginY + 4, inOriginZ - 7, inOriginX + 1, inOriginY + 4, inOriginZ - 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, inOriginX - 1, inOriginY + 4, inOriginZ + 2, inOriginX + 1, inOriginY + 4, inOriginZ + 7, blocks);
		
		WorldGenPrimitive.fillRectSolid(world, inOriginX - 1, inOriginY + 4, inOriginZ - 1, inOriginX + 1, inOriginY + 4, inOriginZ + 1, blocks);
		

		
		// create cells
		createCell(inOriginX - 5, inOriginY, inOriginZ - 5);
		createCell(inOriginX - 5, inOriginY, inOriginZ + 5);
		createCell(inOriginX + 5, inOriginY, inOriginZ - 5);
		createCell(inOriginX + 5, inOriginY, inOriginZ + 5);
		
		return false;
	}
	
	
	private void createCell(int inX, int inY, int inZ){
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, inX - 3, inY - 1, inZ - 3, inX + 3, inY - 1, inZ + 3, blocks);
		WorldGenPrimitive.fillRectSolid(world, inX - 1, inY - 1, inZ - 1, inX + 1, inY - 1, inZ + 1, Block.cobblestoneMossy.blockID);
		
		// pillars
		WorldGenPrimitive.fillRectSolid(world, inX - 2, inY, inZ - 2, inX - 2, inY + 2, inZ - 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, inX - 2, inY, inZ + 2, inX - 2, inY + 2, inZ + 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, inX + 2, inY, inZ - 2, inX + 2, inY + 2, inZ - 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, inX + 2, inY, inZ + 2, inX + 2, inY + 2, inZ + 2, blocks);
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, inX - 3, inY + 3, inZ - 3, inX + 3, inY + 6, inZ + 3, blocks);
		
		
		// torches
		WorldGenPrimitive.fillRectSolid(world, inX - 1, inY + 4, inZ - 2, inX + 1, inY + 4, inZ - 2, 0);
		WorldGenPrimitive.setBlock(world, inX, inY + 4, inZ - 2, Block.torchRedstoneActive.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, inX - 1, inY + 4, inZ + 2, inX + 1, inY + 4, inZ + 2, 0);
		WorldGenPrimitive.setBlock(world, inX, inY + 4, inZ + 2, Block.torchRedstoneActive.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, inX - 2, inY + 4, inZ - 1, inX - 2, inY + 4, inZ + 1, 0);
		WorldGenPrimitive.setBlock(world, inX - 2, inY + 4, inZ, Block.torchRedstoneActive.blockID);

		WorldGenPrimitive.fillRectSolid(world, inX + 2, inY + 4, inZ - 1, inX + 2, inY + 4, inZ + 1, 0);
		WorldGenPrimitive.setBlock(world, inX + 2, inY + 4, inZ, Block.torchRedstoneActive.blockID);

		// ceiling holes
		WorldGenPrimitive.fillRectSolid(world, inX, inY + 3, inZ, inX, inY + 6, inZ, 0);
		WorldGenPrimitive.fillRectSolid(world, inX - 1, inY + 3, inZ - 1, inX - 1, inY + 6, inZ - 1, 0);
		WorldGenPrimitive.fillRectSolid(world, inX - 1, inY + 3, inZ + 1, inX - 1, inY + 6, inZ + 1, 0);
		WorldGenPrimitive.fillRectSolid(world, inX + 1, inY + 3, inZ - 1, inX + 1, inY + 6, inZ - 1, 0);
		WorldGenPrimitive.fillRectSolid(world, inX + 1, inY + 3, inZ + 1, inX + 1, inY + 6, inZ + 1, 0);
		
		// bars
		WorldGenPrimitive.fillRectSolid(world, inX - 1, inY, inZ - 2, inX + 1, inY + 2, inZ - 2, Block.fenceIron.blockID);
		WorldGenPrimitive.fillRectSolid(world, inX, inY, inZ - 2, inX, inY + 1, inZ - 2, 0);
		WorldGenPrimitive.fillRectSolid(world, inX - 1, inY, inZ + 2, inX + 1, inY + 2, inZ + 2, Block.fenceIron.blockID);
		WorldGenPrimitive.fillRectSolid(world, inX, inY, inZ + 2, inX, inY + 1, inZ + 2, 0);
		
		WorldGenPrimitive.fillRectSolid(world, inX - 2, inY, inZ - 1, inX - 2, inY + 2, inZ + 1, Block.fenceIron.blockID);
		WorldGenPrimitive.fillRectSolid(world, inX - 2, inY, inZ, inX - 2, inY + 1, inZ, 0);
		WorldGenPrimitive.fillRectSolid(world, inX + 2, inY, inZ - 1, inX + 2, inY + 2, inZ + 1, Block.fenceIron.blockID);
		WorldGenPrimitive.fillRectSolid(world, inX + 2, inY, inZ, inX + 2, inY + 1, inZ, 0);
		
		if(rand.nextBoolean()){
			switch(rand.nextInt(4)){
			case 0:
				Spawner.generate(world, rand, inX - 2, inY + 4, inZ - 2, Spawner.ZOMBIE);
				break;
			case 1:
				Spawner.generate(world, rand, inX - 2, inY + 4, inZ + 2, Spawner.ZOMBIE);
				break;
			case 2:
				Spawner.generate(world, rand, inX + 2, inY + 4, inZ - 2, Spawner.ZOMBIE);
				break;
			case 3:
				Spawner.generate(world, rand, inX + 2, inY + 4, inZ + 2, Spawner.ZOMBIE);
				break;
			}
		}
		
		if(rand.nextBoolean()){
			TreasureChest.generate(world, rand, inX, inY, inZ);	
		}
		
	}
	
	public int getSize(){
		return 10;
	}
}
