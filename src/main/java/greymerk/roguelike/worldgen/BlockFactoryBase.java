package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.world.World;

public abstract class BlockFactoryBase implements IBlockFactory {
	
	@Override
	public boolean setBlock(World world, Random rand, Coord pos) {
		return setBlock(world, rand, pos, true, true);
	}
}
