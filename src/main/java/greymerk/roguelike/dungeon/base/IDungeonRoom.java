package greymerk.roguelike.dungeon.base;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.Random;

import net.minecraft.world.World;

public interface IDungeonRoom {

	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin);
		
	public int getSize();
	
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z);
}
