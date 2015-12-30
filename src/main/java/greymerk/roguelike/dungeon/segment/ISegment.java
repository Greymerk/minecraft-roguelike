package greymerk.roguelike.dungeon.segment;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.Random;

public interface ISegment {

	public void generate(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord pos);
	
}
