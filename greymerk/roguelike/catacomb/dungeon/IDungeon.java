package greymerk.roguelike.catacomb.dungeon;

import java.util.Random;

import net.minecraft.src.World;

public interface IDungeon {

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ);
		
}
