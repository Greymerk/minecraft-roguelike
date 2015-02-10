package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.world.World;

public abstract class BlockFactoryBase implements IBlockFactory {

	@Override
	public void setBlock(World world, Random rand, int x, int y, int z) {
		setBlock(world, rand, x, y, z, true, true);
	}

	@Override
	public void setBlock(World world, Random rand, Coord pos) {
		setBlock(world, rand, pos.getX(), pos.getY(), pos.getZ(), true, true);
	}
	
	@Override
	public void setBlock(World world, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		setBlock(world, rand, pos.getX(), pos.getY(), pos.getZ(), fillAir, replaceSolid);
	}

}
