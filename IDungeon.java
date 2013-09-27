package greymerk.roguelike;

import java.util.Random;

import net.minecraft.src.World;

public interface IDungeon {

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ);
	
	public boolean isValidDungeonLocation(World world, int x, int y, int z);
	
}
