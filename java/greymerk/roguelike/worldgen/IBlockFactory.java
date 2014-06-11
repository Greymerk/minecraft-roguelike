package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.world.World;

public interface IBlockFactory {
	
	public void setBlock(World world, Random rand, int x, int y, int z);
	
	public void setBlock(World world, Random rand, int x, int y, int z, boolean fillAir, boolean replaceSolid);
	
}
