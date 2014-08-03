package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.world.World;

public interface IDungeon {

	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, int x, int y, int z);
		
	public int getSize();
	
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z);
}
