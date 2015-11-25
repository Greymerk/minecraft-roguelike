package greymerk.roguelike.dungeon.segment;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IAlcove {
	
	public void generate(WorldEditor editor, Random rand, LevelSettings settings, int x, int y, int z, Cardinal dir);
	
	public boolean isValidLocation(WorldEditor editor, int x, int y, int z, Cardinal dir);
	
}
