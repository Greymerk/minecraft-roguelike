package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.world.World;

public abstract class BlockFactoryBase implements IBlockFactory {
	
	@Override
	public boolean setBlock(World world, Random rand, Coord pos) {
		return setBlock(world, rand, pos, true, true);
	}
	
	@Override
	public void fillRectSolid(World world, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid){
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, this, fillAir, replaceSolid);
	}
	
	@Override
	public void fillRectHollow(World world, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid){
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, this, fillAir, replaceSolid);
	}
}
