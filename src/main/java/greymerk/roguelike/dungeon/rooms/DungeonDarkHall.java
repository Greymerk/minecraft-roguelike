package greymerk.roguelike.dungeon.rooms;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonDarkHall extends DungeonBase {

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    ITheme theme = settings.getTheme();

    IBlockFactory outerWall = theme.getPrimary().getWall();
    IBlockFactory wall = theme.getSecondary().getWall();
    IBlockFactory pillar = theme.getSecondary().getPillar();
    IStair stair = theme.getSecondary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(Cardinal.NORTH, 7);
    start.add(Cardinal.WEST, 7);
    end.add(Cardinal.SOUTH, 7);
    end.add(Cardinal.EAST, 7);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.UP, 7);

    RectHollow.fill(editor, rand, start, end, outerWall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(Cardinal.NORTH, 4);
    start.add(Cardinal.WEST, 4);
    end.add(Cardinal.SOUTH, 4);
    end.add(Cardinal.EAST, 4);
    start.add(Cardinal.UP, 6);
    end.add(Cardinal.UP, 9);

    RectHollow.fill(editor, rand, start, end, outerWall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(Cardinal.NORTH, 6);
    start.add(Cardinal.WEST, 6);
    end.add(Cardinal.SOUTH, 6);
    end.add(Cardinal.EAST, 6);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.DOWN);

    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor(), false, true);

    for (Cardinal dir : entrances) {
      Cardinal[] orth = Cardinal.orthogonal(dir);
      start = new Coord(origin);
      start.add(orth[0]);
      end = new Coord(origin);
      end.add(orth[1]);
      end.add(dir, 7);
      RectSolid.fill(editor, rand, start, end, theme.getSecondary().getFloor(), false, true);
    }

    for (Cardinal dir : Cardinal.directions) {

      start = new Coord(origin);
      start.add(dir, 6);
      start.add(Cardinal.left(dir), 6);
      end = new Coord(start);
      end.add(Cardinal.UP, 5);
      RectSolid.fill(editor, rand, start, end, pillar);

      start = new Coord(origin);
      start.add(dir, 6);
      start.add(Cardinal.UP, 6);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 6);
      end.add(Cardinal.right(dir), 6);
      RectSolid.fill(editor, rand, start, end, wall);

      start = new Coord(origin);
      start.add(dir, 3);
      start.add(Cardinal.UP, 6);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 3);
      end.add(Cardinal.right(dir), 3);
      RectSolid.fill(editor, rand, start, end, wall);
      start.add(Cardinal.UP, 2);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, wall);

      start = new Coord(origin);
      start.add(dir, 3);
      start.add(Cardinal.UP, 7);
      pillar.set(editor, rand, start);
      start.add(Cardinal.UP);
      end = new Coord(start);
      end.add(Cardinal.reverse(dir), 3);
      RectSolid.fill(editor, rand, start, end, wall);

      if (Arrays.asList(entrances).contains(dir)) {
        start = new Coord(origin);
        start.add(dir, 7);
        start.add(Cardinal.UP, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        start.add(Cardinal.left(dir), 2);
        end.add(Cardinal.right(dir), 2);
        RectSolid.fill(editor, rand, start, end, wall);

        cursor = new Coord(origin);
        cursor.add(dir, 7);
        cursor.add(Cardinal.UP, 2);
        air.set(editor, cursor);

        for (Cardinal o : Cardinal.orthogonal(dir)) {
          cursor = new Coord(origin);
          cursor.add(dir, 7);
          cursor.add(Cardinal.UP, 2);
          cursor.add(o);
          stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);

          cursor = new Coord(origin);
          cursor.add(dir, 6);
          cursor.add(o, 3);
          pillar(editor, rand, settings, Cardinal.reverse(o), cursor);

          cursor = new Coord(origin);
          cursor.add(dir, 7);
          cursor.add(o, 2);
          pillar.set(editor, rand, cursor);
          cursor.add(Cardinal.UP);
          pillar.set(editor, rand, cursor);
        }
      } else {
        cursor = new Coord(origin);
        cursor.add(dir, 6);
        pillar(editor, rand, settings, Cardinal.reverse(dir), cursor);
      }

      start = new Coord(origin);
      start.add(dir, 6);
      start.add(Cardinal.UP, 6);
      end = new Coord(start);
      end.add(Cardinal.reverse(dir), 2);
      RectSolid.fill(editor, rand, start, end, wall);

      for (Cardinal o : Cardinal.orthogonal(dir)) {
        cursor = new Coord(origin);
        cursor.add(dir, 6);
        cursor.add(o, 3);
        pillar(editor, rand, settings, Cardinal.reverse(dir), cursor);
        start = new Coord(cursor);
        start.add(Cardinal.UP, 6);
        end = new Coord(start);
        end.add(Cardinal.reverse(dir), 6);
        RectSolid.fill(editor, rand, start, end, wall);
      }
    }

    return false;
  }

  private void pillar(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();

    IBlockFactory wall = theme.getSecondary().getWall();
    IBlockFactory pillar = theme.getSecondary().getPillar();
    IStair stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(start);
    end.add(Cardinal.UP, 5);
    RectSolid.fill(editor, rand, start, end, pillar);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 3);
    cursor.add(dir);
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
    cursor.add(dir);
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
    cursor.add(dir);
    if (editor.isAirBlock(cursor)) {
      stair.setOrientation(dir, true).set(editor, cursor);
    } else {
      wall.set(editor, rand, cursor);
    }

  }

  @Override
  public int getSize() {
    return 9;
  }


}
