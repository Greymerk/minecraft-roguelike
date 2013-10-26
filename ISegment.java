package greymerk.roguelike;

import java.util.Random;

import net.minecraft.src.World;

public interface ISegment {

	public void generate(World world, Random rand, Cardinal dir, int x, int y, int z);
	
}
