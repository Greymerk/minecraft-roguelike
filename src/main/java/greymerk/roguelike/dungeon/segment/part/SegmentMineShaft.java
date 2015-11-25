package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

import java.util.Random;

public class SegmentMineShaft extends SegmentBase {
	
	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		IBlockFactory wall = theme.getSecondaryWall();
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		Coord cursor = new Coord(x, y, z);
		Coord start;
		Coord end;

		start = new Coord(cursor);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 3);
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		cursor.add(Cardinal.UP, 3);
		cursor.add(orth[0]);
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		end.add(Cardinal.UP, 3);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		start = new Coord(end);
		cursor = new Coord(end);
		start.add(orth[0]);
		end.add(orth[1]);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		
		start = new Coord(cursor);
		end = new Coord(cursor);
		end.add(Cardinal.reverse(dir), 2);
		editor.fillRectSolid(rand, start, end, wall, true, true);
	}	
}
