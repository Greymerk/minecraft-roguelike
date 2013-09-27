package greymerk.roguelike;

import java.util.Random;

import net.minecraft.src.World;

public interface ITreasureChest {
		
	public boolean generate(World world, Random rand, int x, int y, int z, boolean trapped);
	
	public boolean generate(World world, Random rand, int x, int y, int z);

}