package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface ITower {

	public void generate(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z);
	
}
