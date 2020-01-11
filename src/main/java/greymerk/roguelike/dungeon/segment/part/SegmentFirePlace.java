package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentFirePlace extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    Cardinal[] orth = Cardinal.orthogonal(dir);

    cursor.add(dir, 2);
    start = new Coord(cursor);
    start.add(orth[0], 1);
    end = new Coord(cursor);
    end.add(orth[1], 1);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    // front wall
    start.add(dir, 1);
    end.add(dir, 1);
    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), false, true);

    // stairs
    cursor.add(Cardinal.UP, 2);
    for (Cardinal d : orth) {
      Coord c = new Coord(cursor);
      c.add(d, 1);
      stair.setOrientation(Cardinal.reverse(d), true);
      stair.set(editor, c);
    }

    stair = theme.getPrimary().getStair();

    cursor = new Coord(origin);
    cursor.add(dir, 3);
    stair.setOrientation(Cardinal.reverse(dir), false);
    stair.set(editor, cursor);
    cursor.add(Cardinal.UP);
    BlockType.get(BlockType.IRON_BAR).set(editor, cursor);
    cursor.add(Cardinal.UP);
    stair.setOrientation(Cardinal.reverse(dir), true);
    stair.set(editor, cursor);

    start = new Coord(origin);
    start.add(dir, 4);
    end = new Coord(start);
    start.add(Cardinal.DOWN);
    start.add(orth[0]);
    end.add(Cardinal.UP, 3);
    end.add(orth[1]);
    end.add(dir, 2);
    for (Coord c : new RectHollow(start, end)) {
      if (!editor.getBlock(c).getMaterial().isSolid()) {
        return;
      }
    }

    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());

    cursor = new Coord(origin);
    cursor.add(dir, 4);
    BlockType.get(BlockType.NETHERRACK).set(editor, cursor);
    cursor.add(Cardinal.UP);
    BlockType.get(BlockType.FIRE).set(editor, cursor);
  }
}
