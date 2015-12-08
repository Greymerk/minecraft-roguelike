package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface ISecretRoom {
	
	public IDungeonRoom genRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos);
	
}
