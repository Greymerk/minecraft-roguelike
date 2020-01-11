package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PyramidTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    Coord floor = Tower.getBaseCoord(editor, dungeon);
    floor.add(Cardinal.UP);
    IBlockFactory blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    int x = dungeon.getX();
    int y = dungeon.getY();
    int z = dungeon.getZ();

    start = new Coord(x - 8, floor.getY() - 1, z - 8);
    end = new Coord(x + 8, y + 10, z + 8);
    RectSolid.fill(editor, rand, start, end, blocks, true, true);

    start = new Coord(x - 6, floor.getY() - 1, z - 6);
    end = new Coord(x + 6, floor.getY() + 3, z + 6);
    RectHollow.fill(editor, rand, start, end, blocks, true, true);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(floor);
      cursor.add(dir, 6);
      wall(editor, rand, theme, dir, cursor);
      cursor.add(Cardinal.left(dir), 6);
      corner(editor, rand, theme, dir, cursor);
    }

    cursor = new Coord(floor);
    cursor.add(Cardinal.EAST, 6);
    entrance(editor, rand, theme, Cardinal.EAST, cursor);

    cursor = new Coord(floor);
    cursor.add(Cardinal.UP, 4);
    spire(editor, rand, theme, cursor);

    for (int i = floor.getY() + 3; i >= y; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), theme.getPrimary().getStair(), theme.getPrimary().getPillar());
    }

  }

  private void entrance(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    IBlockFactory blocks = theme.getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(Cardinal.UP, 3);
    end = new Coord(start);
    end.add(Cardinal.reverse(dir));
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    RectSolid.fill(editor, rand, start, end, blocks);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      start = new Coord(origin);
      start.add(dir);
      start.add(o, 2);
      end = new Coord(start);
      end.add(Cardinal.reverse(dir));
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, blocks);

      cursor = new Coord(origin);
      cursor.add(dir, 2);
      cursor.add(o, 2);
      blocks.set(editor, rand, cursor);
      cursor.add(Cardinal.UP);
      blocks.set(editor, rand, cursor);
    }

    // door
    start = new Coord(origin);
    end = new Coord(start);
    start.add(Cardinal.reverse(dir));
    end.add(dir);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.add(dir);
    end = new Coord(start);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 2);
    blocks.set(editor, rand, cursor);

    // door cap
    start = new Coord(origin);
    start.add(Cardinal.UP, 3);
    start.add(dir);
    end = new Coord(start);
    end.add(Cardinal.UP, 2);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    RectSolid.fill(editor, rand, start, end, blocks);

    cursor = new Coord(origin);
    cursor.add(dir);
    cursor.add(Cardinal.UP, 4);
    BlockType.get(BlockType.LAPIS_BLOCK).set(editor, cursor);

    cursor.add(Cardinal.UP, 2);
    blocks.set(editor, rand, cursor);
    cursor.add(Cardinal.UP);
    blocks.set(editor, rand, cursor);
  }

  private void spire(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
    IBlockFactory blocks = theme.getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);
    Coord cursor;
    Coord start;
    Coord end;

    for (Cardinal dir : Cardinal.directions) {

      // outer wall
      start = new Coord(origin);
      start.add(dir, 3);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 3);
      end.add(Cardinal.right(dir), 3);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, blocks);

      // doors
      cursor = new Coord(origin);
      cursor.add(dir, 3);
      air.set(editor, cursor);
      cursor.add(Cardinal.UP);
      air.set(editor, cursor);

      // wall cap
      start = new Coord(origin);
      start.add(dir, 2);
      start.add(Cardinal.UP, 3);
      end = new Coord(start);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.right(dir));
      end.add(dir);
      RectSolid.fill(editor, rand, start, end, blocks);

      start = new Coord(origin);
      start.add(dir);
      start.add(Cardinal.UP, 4);
      end = new Coord(start);
      end.add(dir, 2);
      RectSolid.fill(editor, rand, start, end, blocks);

      // corner spikes
      start = new Coord(origin);
      start.add(dir, 3);
      start.add(Cardinal.left(dir), 3);
      start.add(Cardinal.UP, 3);
      end = new Coord(start);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, blocks);

      start = new Coord(origin);
      start.add(dir, 2);
      start.add(Cardinal.left(dir), 2);
      start.add(Cardinal.UP, 3);
      end = new Coord(start);
      end.add(Cardinal.UP, 4);
      RectSolid.fill(editor, rand, start, end, blocks);

      start = new Coord(origin);
      start.add(dir);
      start.add(Cardinal.left(dir));
      start.add(Cardinal.UP, 4);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, blocks);

      start = new Coord(origin);
      start.add(dir);
      start.add(Cardinal.UP, 7);
      end = new Coord(start);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, blocks);
    }

    start = new Coord(origin);
    start.add(Cardinal.UP, 7);
    end = new Coord(start);
    end.add(Cardinal.UP, 6);
    RectSolid.fill(editor, rand, start, end, blocks);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 7);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);

  }

  private void wall(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos) {
    IBlockFactory blocks = theme.getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);
    Coord cursor;
    Coord start;
    Coord end;

    // upper wall lip
    start = new Coord(pos);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 5);
    end.add(Cardinal.right(dir), 5);
    RectSolid.fill(editor, rand, start, end, blocks);

    // inner wall
    start = new Coord(pos);
    start.add(Cardinal.reverse(dir));
    end = new Coord(start);
    end.add(Cardinal.reverse(dir));
    end.add(Cardinal.UP, 2);
    start.add(Cardinal.left(dir), 4);
    end.add(Cardinal.right(dir), 4);
    RectSolid.fill(editor, rand, start, end, blocks);

    cursor = new Coord(pos);
    cursor.add(Cardinal.reverse(dir), 2);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      Coord c2 = new Coord(pos);
      for (int i = 0; i < 5; ++i) {
        if (i % 2 == 0) {
          cursor = new Coord(c2);
          cursor.add(Cardinal.UP, 5);
          blocks.set(editor, rand, cursor);

          start = new Coord(c2);
          start.add(Cardinal.UP);
          end = new Coord(start);
          end.add(Cardinal.UP, 2);
          RectSolid.fill(editor, rand, start, end, air);
        } else {
          cursor = new Coord(c2);
          cursor.add(dir);
          blocks.set(editor, rand, cursor);
          cursor.add(Cardinal.UP);
          blocks.set(editor, rand, cursor);
        }
        c2.add(o);
      }

      cursor = new Coord(pos);
      cursor.add(Cardinal.reverse(dir), 2);
      cursor.add(o, 2);
      air.set(editor, cursor);
      cursor.add(Cardinal.UP);
      air.set(editor, cursor);
    }
  }

  private void corner(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos) {

    IBlockFactory blocks = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] faces = {dir, Cardinal.left(dir)};

    for (Cardinal face : faces) {
      start = new Coord(pos);
      start.add(face);
      end = new Coord(start);
      end.add(Cardinal.left(face));
      start.add(Cardinal.right(face));
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, blocks);

      cursor = new Coord(pos);
      cursor.add(face, 2);
      blocks.set(editor, rand, cursor);
      cursor.add(Cardinal.UP);
      blocks.set(editor, rand, cursor);

      cursor = new Coord(pos);
      cursor.add(face);
      cursor.add(Cardinal.UP, 2);
      blocks.set(editor, rand, cursor);
      cursor.add(Cardinal.UP);
      blocks.set(editor, rand, cursor);
    }

    start = new Coord(pos);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, blocks);
  }


}
