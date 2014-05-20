package greymerk.roguelike.catacomb.dungeon;

import java.util.Random;

import net.minecraft.src.World;

public interface IDungeon {

	public boolean generate(World world, Random rand, int x, int y, int z);
		
	public int getSize();
	
}
