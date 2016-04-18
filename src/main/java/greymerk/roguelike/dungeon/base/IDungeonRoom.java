package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface IDungeonRoom {

	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin);
		
	public int getSize();
	
	public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos);

}
