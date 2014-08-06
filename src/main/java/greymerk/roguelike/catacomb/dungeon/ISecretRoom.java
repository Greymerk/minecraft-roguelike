package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.Random;

import net.minecraft.world.World;

public interface ISecretRoom {

	
	public boolean isValid(World world, Random rand, Cardinal dir, Coord pos);
	
	public boolean genRoom(World world, Random rand, CatacombLevelSettings settings, Cardinal dir, Coord pos);
	
}
