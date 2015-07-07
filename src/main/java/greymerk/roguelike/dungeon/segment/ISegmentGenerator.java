package greymerk.roguelike.dungeon.segment;

import greymerk.roguelike.dungeon.CatacombLevel;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.Random;

import net.minecraft.world.World;

public interface ISegmentGenerator {
	
	public void genSegment(World world, Random rand, CatacombLevel level, Cardinal dir, Coord pos);
	
}
