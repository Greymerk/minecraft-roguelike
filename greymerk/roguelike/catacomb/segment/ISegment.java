package greymerk.roguelike.catacomb.segment;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.src.World;

public interface ISegment {

	public void generate(World world, Random rand, CatacombLevel level, Cardinal dir, int x, int y, int z);
	
}
