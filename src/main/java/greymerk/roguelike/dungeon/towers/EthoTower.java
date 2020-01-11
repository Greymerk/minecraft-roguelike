package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class EthoTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    IBlockFactory primary = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getSecondary().getPillar();

    IStair stair = theme.getSecondary().getStair();

    Coord floor = Tower.getBaseCoord(editor, dungeon);

    Coord start = new Coord(floor);
    Coord end = new Coord(start);
    Coord cursor;

    int x = dungeon.getX();
    int z = dungeon.getZ();

    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.WEST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.EAST, 3);
    end.add(Cardinal.UP, 4);

    //WorldGenPrimitive.fillRectSolid(rand, start, end, air, true, true);

    start.add(Cardinal.NORTH);
    start.add(Cardinal.WEST);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.SOUTH);
    end.add(Cardinal.EAST);
    end.add(Cardinal.UP);

    RectHollow.fill(editor, rand, start, end, primary, true, true);

    for (Cardinal dir : Cardinal.directions) {

      Cardinal[] orth = Cardinal.orthogonal(dir);
      start = new Coord(floor);
      start.add(dir, 3);
      start.add(orth[0], 3);
      end = new Coord(start);
      end.add(Cardinal.UP, 6);

      RectSolid.fill(editor, rand, start, end, pillar, true, true);

      for (Cardinal o : orth) {
        start = new Coord(floor);
        start.add(dir, 5);
        start.add(o, 4);
        end = new Coord(start);
        end.add(Cardinal.UP, 4);
        start.add(Cardinal.DOWN, 10);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);

        end.add(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, end);

        end.add(Cardinal.reverse(dir));
        end.add(Cardinal.reverse(o));
        stair.setOrientation(Cardinal.reverse(o), false).set(editor, end);
        end.add(Cardinal.reverse(o));
        start = new Coord(end);
        start.add(Cardinal.reverse(o), 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir, false), true, true);

        end.add(Cardinal.reverse(dir));
        end.add(Cardinal.UP);
        start.add(Cardinal.reverse(dir));
        start.add(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir, false), true, true);
        stair.setOrientation(Cardinal.reverse(o), false).set(editor, end);

        start = new Coord(floor);
        start.add(dir, 3);
        start.add(Cardinal.UP, 4);
        end = new Coord(start);
        end.add(o, 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(Cardinal.reverse(dir), true), true, true);
        start.add(Cardinal.reverse(dir));
        start.add(Cardinal.UP);
        end = new Coord(start);
        end.add(o, 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(Cardinal.reverse(dir), true), true, true);
        start.add(Cardinal.UP);
        end.add(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);
        cursor = new Coord(end);
        start = new Coord(end);
        start.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);
        cursor.add(Cardinal.reverse(o));
        cursor.add(Cardinal.UP);
        stair.setOrientation(Cardinal.reverse(o), false).set(editor, cursor);
        cursor.add(Cardinal.UP, 2);
        stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
        start.add(Cardinal.UP);
        end = new Coord(start);
        end.add(Cardinal.reverse(o), 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir, false), true, true);
        cursor = new Coord(end);
        cursor.add(Cardinal.reverse(dir));
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
        cursor.add(o);
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, cursor);
        cursor.add(Cardinal.reverse(o));
        stair.setOrientation(dir, false).set(editor, cursor);


      }


    }

    Cardinal front = Cardinal.NORTH;

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(floor);
      cursor.add(dir, 6);
      if (editor.isAirBlock(cursor)) {
        front = dir;
        break;
      }
    }

    for (Cardinal dir : Cardinal.directions) {

      if (dir == front) {

        for (Cardinal o : Cardinal.orthogonal(dir)) {
          cursor = new Coord(floor);
          cursor.add(dir, 5);
          cursor.add(o, 2);
          primary.set(editor, rand, cursor);
          cursor.add(o);
          stair.setOrientation(o, false).set(editor, cursor);
          cursor.add(dir);
          stair.setOrientation(o, false).set(editor, cursor);
          cursor.add(Cardinal.reverse(o));
          stair.setOrientation(dir, false).set(editor, cursor);
          cursor.add(Cardinal.reverse(dir));
          cursor.add(Cardinal.UP);
          stair.setOrientation(Cardinal.reverse(o), false).set(editor, cursor);
          cursor.add(Cardinal.UP);
          stair.setOrientation(dir, false).set(editor, cursor);
          cursor.add(o);
          stair.setOrientation(o, false).set(editor, cursor);
          cursor.add(Cardinal.reverse(o));
          cursor.add(Cardinal.UP);
          stair.setOrientation(Cardinal.reverse(o), false).set(editor, cursor);
          cursor.add(Cardinal.reverse(o));
          stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
          cursor.add(Cardinal.reverse(o));
          cursor.add(Cardinal.UP);
          stair.setOrientation(dir, false).set(editor, cursor);
          cursor.add(o);
          stair.setOrientation(dir, false).set(editor, cursor);
          cursor.add(o);
          stair.setOrientation(o, false).set(editor, cursor);
        }

        // carve doorway
        Cardinal[] orth = Cardinal.orthogonal(dir);
        cursor = new Coord(floor);
        cursor.add(dir, 4);
        start = new Coord(cursor);
        end = new Coord(start);
        start.add(orth[0]);
        end.add(Cardinal.UP, 2);
        end.add(orth[1]);
        RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR), true, true);

        cursor = new Coord(floor);
        cursor.add(dir, 6);
        cursor.add(Cardinal.DOWN);
        step(editor, rand, theme, dir, cursor);

        continue;
      }


      for (Cardinal o : Cardinal.orthogonal(dir)) {
        start = new Coord(floor);
        start.add(Cardinal.UP, 4);
        start.add(dir, 5);
        end = new Coord(start);
        start.add(o, 2);
        RectSolid.fill(editor, rand, start, end, stair.setOrientation(dir, false), true, true);
        start.add(o);
        stair.setOrientation(Cardinal.reverse(o), false).set(editor, start);
        start.add(Cardinal.DOWN);
        stair.setOrientation(Cardinal.reverse(o), true).set(editor, start);
      }

    }


    for (int i = floor.getY() - 1; i >= 50; --i) {
      editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getPrimary().getPillar());
    }
  }

  private void step(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    if (editor.getBlock(origin).isOpaqueCube()) {
      return;
    }

    Coord start;
    Coord end;

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory blocks = theme.getPrimary().getWall();

    Cardinal[] orth = Cardinal.orthogonal(dir);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(orth[0]);
    end.add(orth[1]);
    end = new Coord(end.getX(), 60, end.getZ());
    RectSolid.fill(editor, rand, start, end, blocks, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(orth[0]);
    end.add(orth[1]);
    stair.setOrientation(dir, false);
    RectSolid.fill(editor, rand, start, end, stair, true, true);

    origin.add(Cardinal.DOWN);
    origin.add(dir);
    step(editor, rand, theme, dir, origin);
  }

}
