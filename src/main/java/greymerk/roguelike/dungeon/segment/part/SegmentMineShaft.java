package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMineShaft extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);

    IBlockFactory wall = theme.getSecondary().getWall();

    Cardinal[] orth = Cardinal.orthogonal(dir);

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    start = new Coord(cursor);
    start.add(dir, 2);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, air);

    cursor.add(Cardinal.UP, 3);
    cursor.add(orth[0]);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.add(dir, 2);
    end = new Coord(start);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, wall);
    start = new Coord(end);
    cursor = new Coord(end);
    start.add(orth[0]);
    end.add(orth[1]);
    RectSolid.fill(editor, rand, start, end, wall);

    start = new Coord(cursor);
    end = new Coord(cursor);
    end.add(Cardinal.reverse(dir), 2);
    RectSolid.fill(editor, rand, start, end, wall);
  }
}
