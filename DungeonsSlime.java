package greymerk.roguelike;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class DungeonsSlime implements IDungeon {
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;

	BlockRandomizer fillBlocks;
	int liquid;
	
	public DungeonsSlime() {
	}

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		liquid = Dungeon.getRank(originY) == 3 ? Block.lavaStill.blockID : Block.waterStill.blockID;

		
		
		if(Dungeon.getRank(inOriginY) == 3){
			fillBlocks = new BlockRandomizer(rand, new MetaBlock(Block.netherBrick.blockID));
			fillBlocks.addBlock(new MetaBlock(Block.netherrack.blockID), 10);
			fillBlocks.addBlock(new MetaBlock(Block.oreNetherQuartz.blockID), 20);
		} else {
			fillBlocks = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID));
			fillBlocks.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 3);
			fillBlocks.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 3);
			fillBlocks.addBlock(new MetaBlock(Block.cobblestone.blockID, 1), 10);
			fillBlocks.addBlock(new MetaBlock(Block.gravel.blockID, 1), 20);
		}
		
		
		// fill air
		WorldGenPrimitive.fillRectSolid(world, originX - 6, originY, originZ - 6, originX + 6, originY + 3, originZ + 6, 0);
		
		// shell
		WorldGenPrimitive.fillRectHollow(world, originX - 7, originY - 2, originZ - 7, originX + 7, originY + 4, originZ + 7, fillBlocks, false, true);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, originX - 7, originY - 2, originZ - 7, originX + 7, originY - 1, originZ + 7, fillBlocks);
		
		pool(originX - 4, originZ - 4);
		pool(originX - 4, originZ + 4);
		pool(originX + 4, originZ - 4);
		pool(originX + 4, originZ + 4);
		
		// centre top lip
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY + 3, originZ - 2, originX + 1, originY + 3, originZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY + 3, originZ + 2, originX + 1, originY + 3, originZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY + 3, originZ - 1, originX - 2, originY + 3, originZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY + 3, originZ - 1, originX + 2, originY + 3, originZ + 1, fillBlocks);
		
		if(Dungeon.getRank(originY) < 3){
			WorldGenPrimitive.randomVines(world, rand, originX - 7, originY + 2, originZ - 7, originX + 7, originY + 5, originZ + 7);
			Spawner.generate(world, rand, originX, originY + 5, originZ, Spawner.SLIME);
		} else {
			Spawner.generate(world, rand, originX, originY + 5, originZ, Spawner.LAVASLIME);
		}
		
		return true;
	}
	
	private void pool(int inX, int inZ){
		// water pool
		WorldGenPrimitive.fillRectSolid(world, inX - 1, originY - 1, inZ - 1, inX + 1, originY - 1, inZ + 1, liquid);
		
		// pillars
		WorldGenPrimitive.fillRectSolid(world, inX - 2, originY - 1, inZ - 2, inX - 2, originY + 3, inZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX - 2, originY - 1, inZ + 2, inX - 2, originY + 3, inZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX + 2, originY - 1, inZ - 2, inX + 2, originY + 3, inZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX + 2, originY - 1, inZ + 2, inX + 2, originY + 3, inZ + 2, fillBlocks);
		
		// base lip
		WorldGenPrimitive.fillRectSolid(world, inX - 1, originY - 1, inZ - 2, inX + 1, originY - 1, inZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX - 1, originY - 1, inZ + 2, inX + 1, originY - 1, inZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX - 2, originY - 1, inZ - 1, inX - 2, originY - 1, inZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX + 2, originY - 1, inZ - 1, inX + 2, originY - 1, inZ + 1, fillBlocks);
		
		// top lip
		WorldGenPrimitive.fillRectSolid(world, inX - 1, originY + 3, inZ - 2, inX + 1, originY + 3, inZ - 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX - 1, originY + 3, inZ + 2, inX + 1, originY + 3, inZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX - 2, originY + 3, inZ - 1, inX - 2, originY + 3, inZ + 1, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX + 2, originY + 3, inZ - 1, inX + 2, originY + 3, inZ + 1, fillBlocks);

		// carve ceiling air
		WorldGenPrimitive.fillRectSolid(world, inX - 1, originY + 1, inZ - 1, inX + 1, originY + 6, inZ + 1, 0);
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, inX - 2, originY + 7, inZ - 2, inX + 2, originY + 8, inZ + 2, fillBlocks);
		WorldGenPrimitive.fillRectSolid(world, inX - 1, originY + 8, inZ - 1, inX + 1, originY + 8, inZ + 1, liquid);
		
		
	}
	
	public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ) {
		return false;
	}	
}
