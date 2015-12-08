package greymerk.roguelike.dungeon.segment;

import java.util.Collection;
import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.WorldEditor;

public interface ISegment {

	public void generate(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z);

	public Collection<? extends ITreasureChest> getChests();
	
}
