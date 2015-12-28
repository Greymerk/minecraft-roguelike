package greymerk.roguelike.dungeon.segment;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface IAlcove {
	
	public void generate(IWorldEditor editor, Random rand, LevelSettings settings, int x, int y, int z, Cardinal dir);
	
	public boolean isValidLocation(IWorldEditor editor, int x, int y, int z, Cardinal dir);
	
}
