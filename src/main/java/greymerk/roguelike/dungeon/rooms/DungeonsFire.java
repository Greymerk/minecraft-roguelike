package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsFire extends DungeonBase {


  public static void genFire(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();


    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    BlockType.get(BlockType.NETHERRACK).set(editor, cursor);
    cursor.add(Cardinal.UP);
    BlockType.get(BlockType.FIRE).set(editor, cursor);

    for (Cardinal dir : Cardinal.directions) {

      start = new Coord(origin);
      start.add(dir);
      start.add(Cardinal.left(dir));
      end = new Coord(start);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, pillar, true, false);

      cursor = new Coord(origin);
      cursor.add(dir);
      stair.setOrientation(dir, false).set(editor, rand, cursor, true, false);
      cursor.add(Cardinal.UP);
      BlockType.get(BlockType.IRON_BAR).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(dir, true).set(editor, rand, cursor, true, false);

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 6);
      cursor.add(dir, 3);

      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o, 2);
        stair.setOrientation(dir, true).set(editor, rand, c, true, false);
        c.add(o);
        stair.setOrientation(dir, true).set(editor, rand, c, true, false);
      }

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP);
      cursor.add(dir, 2);

      if (!editor.isAirBlock(cursor)) {
        continue;
      }

      start = new Coord(origin);
      start.add(Cardinal.UP, 3);
      start.add(dir, 2);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 2);
      end.add(Cardinal.right(dir), 2);
      stair.setOrientation(dir, true);
      RectSolid.fill(editor, rand, start, end, stair, true, false);
    }

    start = new Coord(origin);
    start.add(Cardinal.UP, 3);
    start.add(Cardinal.NORTH, 2);
    start.add(Cardinal.WEST, 2);
    end = new Coord(origin);
    end.add(Cardinal.UP, 7);
    end.add(Cardinal.SOUTH, 2);
    end.add(Cardinal.EAST, 2);

    RectSolid.fill(editor, rand, start, end, wall, true, false);

  }

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    ITheme theme = settings.getTheme();

    IBlockFactory wall = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory pillar = theme.getPrimary().getPillar();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.NORTH, 8);
    start.add(Cardinal.WEST, 8);
    start.add(Cardinal.DOWN);
    end = new Coord(origin);
    end.add(Cardinal.SOUTH, 8);
    end.add(Cardinal.EAST, 8);
    end.add(Cardinal.UP, 7);

    RectHollow.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(Cardinal.NORTH, 8);
    start.add(Cardinal.WEST, 8);
    end.add(Cardinal.SOUTH, 8);
    end.add(Cardinal.EAST, 8);
    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor(), false, true);

    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : Cardinal.orthogonal(dir)) {
        start = new Coord(origin);
        start.add(dir, 7);
        start.add(orth, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 6);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor = new Coord(origin);
        cursor.add(dir, 8);
        cursor.add(orth);
        cursor.add(Cardinal.UP, 2);
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, cursor, true, false);

        cursor.add(Cardinal.reverse(dir));
        cursor.add(Cardinal.UP);
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);

        start = new Coord(cursor);
        start.add(Cardinal.UP);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor.add(Cardinal.reverse(dir));
        cursor.add(orth);
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);

        start = new Coord(cursor);
        start.add(Cardinal.UP);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor.add(dir);
        cursor.add(orth);
        stair.setOrientation(orth, true).set(editor, cursor);

        start = new Coord(cursor);
        start.add(Cardinal.UP);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar);

      }

      cursor = new Coord(origin);
      cursor.add(dir, 6);
      cursor.add(Cardinal.left(dir), 6);

      genFire(editor, rand, theme, cursor);

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 4);
      cursor.add(dir);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(dir, 6);
      RectSolid.fill(editor, rand, start, end, wall);
      cursor.add(Cardinal.left(dir));
      wall.set(editor, rand, cursor);

      start = new Coord(end);
      end.add(Cardinal.UP, 2);
      end.add(Cardinal.reverse(dir));
      RectSolid.fill(editor, rand, start, end, wall);

      stair.setOrientation(Cardinal.reverse(dir), true);

      cursor = new Coord(end);
      start = new Coord(cursor);
      start.add(Cardinal.left(dir), 3);
      end.add(Cardinal.right(dir), 3);
      RectSolid.fill(editor, rand, start, end, wall, true, false);

      start = new Coord(cursor);
      start.add(Cardinal.DOWN);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 3);
      end.add(Cardinal.right(dir), 3);
      RectSolid.fill(editor, rand, start, end, stair, true, false);

      start = new Coord(cursor);
      start.add(Cardinal.reverse(dir));
      end = new Coord(start);
      start.add(Cardinal.left(dir), 3);
      end.add(Cardinal.right(dir), 3);
      RectSolid.fill(editor, rand, start, end, stair, true, false);
    }


    return false;
  }

  public int getSize() {
    return 10;
  }

}
