package greymerk.roguelike.dungeon.settings;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;


public interface ISettings {

	public boolean isValid(WorldEditor editor, Coord pos);
	
	public LevelSettings getLevelSettings(int level);
	
	public TowerSettings getTower();
	
	public int getNumLevels();
}
