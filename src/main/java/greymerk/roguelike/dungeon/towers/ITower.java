package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.WorldEditor;

public interface ITower {

	public void generate(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z);
	
}
