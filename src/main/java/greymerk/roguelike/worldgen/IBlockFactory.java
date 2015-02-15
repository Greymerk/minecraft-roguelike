package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.world.World;

public interface IBlockFactory {
	
	public boolean setBlock(World world, Random rand, Coord pos);
	
	public boolean setBlock(World world, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);
	
	public void fillRectSolid(World world, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid);
	
	public void fillRectHollow(World world, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid);
}
