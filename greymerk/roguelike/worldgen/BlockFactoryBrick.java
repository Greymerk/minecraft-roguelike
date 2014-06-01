package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class BlockFactoryBrick implements IBlockFactory{
	
	Random rand;
	
	public BlockFactoryBrick(Random rand){

		this.rand = rand;
	}
	
	@Override
	public void setBlock(World world, int x, int y, int z) {
		setBlock(world, x, y, z, true, true);
	}

	@Override
	public void setBlock(World world, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		WorldGenPrimitive.setBlock(world, x, y, z, Block.stoneBrick.blockID, rand.nextInt(3), 2, fillAir, replaceSolid);
	}
}
