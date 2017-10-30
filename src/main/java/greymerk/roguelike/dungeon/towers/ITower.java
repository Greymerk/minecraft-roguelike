package greymerk.roguelike.dungeon.towers;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.Random;

public interface ITower {

	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin);
	
}
