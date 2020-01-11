package greymerk.roguelike.dungeon.towers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Vine;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class JungleTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    Coord origin = Tower.getBaseCoord(editor, dungeon);
    origin.add(Cardinal.UP);
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock grass = BlockType.get(BlockType.GRASS);

    Coord start;
    Coord end;
    Coord cursor;

    // lower pillars
    for (Cardinal dir : Cardinal.directions) {

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 3);
      cursor.add(dir, 7);
      pillar(editor, rand, theme, cursor);

      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o, 3);
        pillar(editor, rand, theme, c);
        c.add(dir);
        c.add(Cardinal.UP);
        walls.set(editor, rand, c);
        c.add(Cardinal.UP);
        walls.set(editor, rand, c);
        c.add(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, c);
        c.add(Cardinal.reverse(dir));
        walls.set(editor, rand, c);
      }

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 4);
      cursor.add(dir, 8);
      walls.set(editor, rand, cursor);
      cursor.add(Cardinal.UP);
      walls.set(editor, rand, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(dir, false).set(editor, cursor);
      cursor.add(Cardinal.reverse(dir));
      walls.set(editor, rand, cursor);

      start = new Coord(origin);
      start.add(dir, 2);
      start.add(Cardinal.left(dir), 2);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      pillar.fill(editor, rand, new RectSolid(start, end));
      cursor = new Coord(end);
      for (Cardinal d : new Cardinal[]{Cardinal.reverse(dir), Cardinal.right(dir)}) {
        Coord c = new Coord(cursor);
        c.add(d);
        stair.setOrientation(d, true).set(editor, c);
      }

      // corner pillar
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 6);
      cursor.add(dir, 6);
      cursor.add(Cardinal.left(dir), 6);
      editor.fillDown(rand, new Coord(cursor), pillar);
      for (Cardinal d : new Cardinal[]{dir, Cardinal.left(dir)}) {
        start = new Coord(cursor);
        start.add(d);
        stair.setOrientation(d, false).set(editor, start);
        start.add(Cardinal.DOWN);
        end = new Coord(start);
        end.add(Cardinal.DOWN, 2);
        walls.fill(editor, rand, new RectSolid(start, end));
        end.add(Cardinal.DOWN);
        stair.setOrientation(d, true).set(editor, end);
      }
    }


    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 4);
      cursor.add(dir, 7);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.add(Cardinal.left(dir), 5);
      end.add(Cardinal.right(dir), 5);
      walls.fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      grass.fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.reverse(dir));
      end.add(Cardinal.reverse(dir));
      walls.fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.reverse(dir));
      end.add(Cardinal.reverse(dir));
      stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 4);
      cursor.add(dir, 6);
      pillar(editor, rand, theme, cursor);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o, 3);
        pillar(editor, rand, theme, c);
      }

      start = new Coord(origin);
      start.add(Cardinal.UP, 5);
      start.add(dir, 2);
      end = new Coord(start);
      end.add(dir, 3);
      walls.fill(editor, rand, new RectSolid(start, end));
      end.add(Cardinal.left(dir), 3);
      start = new Coord(end);
      start.add(Cardinal.reverse(dir), 10);
      walls.fill(editor, rand, new RectSolid(start, end));

      start = new Coord(origin);
      start.add(Cardinal.UP, 6);
      start.add(dir, 3);
      start.add(Cardinal.left(dir), 2);
      end = new Coord(start);
      end.add(Cardinal.right(dir), 8);
      end.add(dir, 3);
      walls.fill(editor, rand, new RectSolid(start, end));

      start = new Coord(origin);
      start.add(Cardinal.UP, 4);
      start.add(dir, 2);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 2);
      end.add(Cardinal.right(dir));
      end.add(Cardinal.UP, 2);
      walls.fill(editor, rand, new RectSolid(start, end));

      start = new Coord(origin);
      start.add(Cardinal.UP, 4);
      start.add(dir, 3);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 3);
      end.add(Cardinal.right(dir), 2);
      stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    }

    // level 2 grass patches
    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        start = new Coord(origin);
        start.add(Cardinal.UP, 6);
        start.add(dir, 5);
        start.add(o);
        end = new Coord(start);
        end.add(o);
        end.add(dir);
        grass.fill(editor, rand, new RectSolid(start, end));
        start.add(o, 3);
        end.add(o, 3);
        grass.fill(editor, rand, new RectSolid(start, end));
      }
    }

    // second floor pillars
    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 9);
      cursor.add(dir, 5);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o, 2);
        pillar(editor, rand, theme, c);
        c.add(dir);
        c.add(Cardinal.UP);
        walls.set(editor, rand, c);
        c.add(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, c);
        c.add(Cardinal.reverse(dir));
        walls.set(editor, rand, c);
        c.add(Cardinal.UP);
        stair.setOrientation(dir, false).set(editor, c);
      }
      cursor.add(Cardinal.left(dir), 5);
      pillar(editor, rand, theme, cursor);
      for (Cardinal d : new Cardinal[]{Cardinal.left(dir), dir}) {
        Coord c = new Coord(cursor);
        c.add(d);
        c.add(Cardinal.UP);
        walls.set(editor, rand, c);
        c.add(Cardinal.UP);
        stair.setOrientation(d, false).set(editor, c);
      }

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 10);
      cursor.add(dir, 2);
      cursor.add(Cardinal.left(dir), 2);
      start = new Coord(cursor);
      end = new Coord(start);
      end.add(Cardinal.DOWN, 3);
      pillar.fill(editor, rand, new RectSolid(start, end));
      for (Cardinal d : new Cardinal[]{Cardinal.right(dir), Cardinal.reverse(dir)}) {
        Coord c = new Coord(cursor);
        c.add(d);
        stair.setOrientation(d, true).set(editor, c);
      }

      cursor.add(Cardinal.DOWN);
      for (Cardinal d : new Cardinal[]{Cardinal.left(dir), dir}) {
        Coord c = new Coord(cursor);
        c.add(d);
        stair.setOrientation(d, true).set(editor, c);
        c.add(Cardinal.UP);
        walls.set(editor, rand, c);
        c.add(d);
        walls.set(editor, rand, c);
      }

    }

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(Cardinal.UP, 10);
      start.add(dir, 5);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 5);
      end.add(Cardinal.right(dir), 4);
      walls.fill(editor, rand, new RectSolid(start, end));

      start = new Coord(origin);
      start.add(Cardinal.UP, 11);
      start.add(dir, 2);
      end = new Coord(start);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.right(dir), 4);
      end.add(dir, 2);
      walls.fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 11);
      cursor.add(dir, 5);
      start = new Coord(cursor);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 4);
      end.add(Cardinal.right(dir), 4);
      grass.fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 12);
      cursor.add(dir, 3);
      start = new Coord(cursor);
      end = new Coord(start);
      end.add(dir);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.right(dir), 4);
      grass.fill(editor, rand, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 12);
      cursor.add(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.add(Cardinal.left(dir), 4);
      end.add(Cardinal.right(dir), 4);
      walls.fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 11);
      cursor.add(dir, 5);
      cursor.add(Cardinal.left(dir), 5);
      walls.set(editor, rand, cursor);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir, 2);
      cursor.add(Cardinal.left(dir), 2);
      cursor.add(Cardinal.UP, 15);
      pillar(editor, rand, theme, cursor);
      for (Cardinal d : new Cardinal[]{dir, Cardinal.left(dir)}) {
        Coord c = new Coord(cursor);
        c.add(d);
        c.add(Cardinal.UP);
        walls.set(editor, rand, c);
        c.add(Cardinal.UP);
        stair.setOrientation(d, false).set(editor, c);
      }

      start = new Coord(origin);
      start.add(Cardinal.UP, 16);
      start.add(dir, 2);
      end = new Coord(start);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.right(dir), 2);
      walls.fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 17);
      cursor.add(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.right(dir));
      grass.fill(editor, rand, new RectSolid(start, end));
      cursor.add(Cardinal.left(dir), 2);
      walls.set(editor, rand, cursor);

      start = new Coord(origin);
      start.add(Cardinal.UP, 17);
      end = new Coord(start);
      start.add(dir);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.reverse(dir));
      end.add(Cardinal.right(dir));
      walls.fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      grass.fill(editor, rand, new RectSolid(start, end));
    }

    start = new Coord(origin);
    start.add(Cardinal.NORTH, 2);
    start.add(Cardinal.EAST, 2);
    end = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
    end.add(Cardinal.SOUTH, 2);
    end.add(Cardinal.WEST, 2);
    walls.fill(editor, rand, new RectSolid(start, end), false, true);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 12);
    start = new Coord(cursor.getX(), dungeon.getY(), cursor.getZ());
    end = new Coord(cursor);
    for (Coord c : new RectSolid(start, end)) {
      editor.spiralStairStep(rand, c, stair, pillar);
    }

    decorate(editor, rand, theme, origin);
  }

  private void decorate(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
    List<Coord> spots = new ArrayList<Coord>();
    for (Cardinal dir : Cardinal.directions) {
      Coord cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 6);
      cursor.add(dir, 7);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        spots.add(new Coord(c));
        c.add(o);
        spots.add(new Coord(c));
        c.add(o, 2);
        spots.add(new Coord(c));
        c.add(o);
        spots.add(new Coord(c));
      }

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 12);
      cursor.add(dir, 5);
      spots.add(new Coord(cursor));
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        spots.add(new Coord(c));
        c.add(o, 2);
        spots.add(new Coord(c));
        c.add(o);
        spots.add(new Coord(c));
      }

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 13);
      cursor.add(dir, 4);
      spots.add(new Coord(cursor));
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        spots.add(new Coord(c));
        c.add(o, 2);
        spots.add(new Coord(c));
        c.add(o);
        spots.add(new Coord(c));
      }

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 18);
      cursor.add(dir, 2);
      spots.add(new Coord(cursor));
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        spots.add(new Coord(c));
      }

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 19);
      Coord start = new Coord(cursor);
      Coord end = new Coord(cursor);
      start.add(dir);
      end.add(Cardinal.reverse(dir));
      start.add(Cardinal.left(dir));
      end.add(Cardinal.right(dir));
      spots.addAll(new RectSolid(start, end).get());
    }

    for (Coord c : spots) {
      if (rand.nextBoolean()) {
        tree(editor, rand, theme, c);
      }
    }

    Coord start = new Coord(origin);
    Coord end = new Coord(origin);
    end.add(Cardinal.UP, 20);
    start.add(Cardinal.NORTH, 8);
    start.add(Cardinal.EAST, 8);
    end.add(Cardinal.SOUTH, 8);
    end.add(Cardinal.WEST, 8);
    Vine.fill(editor, start, end);
  }

  private void tree(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    MetaBlock leaves = Leaves.get(Wood.JUNGLE, false);

    Coord cursor = new Coord(origin);
    Log.getLog(Wood.JUNGLE).set(editor, cursor);
    for (Cardinal dir : Cardinal.directions) {
      Coord c = new Coord(cursor);
      c.add(dir);
      leafSpill(editor, rand, theme, c, rand.nextInt(6));
    }
    if (rand.nextBoolean()) {
      cursor.add(Cardinal.UP);
      Log.getLog(Wood.JUNGLE).set(editor, cursor);
      for (Cardinal dir : Cardinal.directions) {
        Coord c = new Coord(cursor);
        c.add(dir);
        leaves.set(editor, rand, c, true, false);
      }
    }
    if (rand.nextInt(3) == 0) {
      cursor.add(Cardinal.UP);
      Log.getLog(Wood.JUNGLE).set(editor, cursor);
      for (Cardinal dir : Cardinal.directions) {
        Coord c = new Coord(cursor);
        c.add(dir);
        leaves.set(editor, rand, c, true, false);
      }
    }
    cursor.add(Cardinal.UP);
    leaves.set(editor, cursor);
  }

  public void leafSpill(IWorldEditor editor, Random rand, ITheme theme, Coord origin, int count) {
    if (count < 0) {
      return;
    }
    MetaBlock leaves = Leaves.get(Wood.JUNGLE, false);
    leaves.set(editor, origin);
    Coord cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    if (!editor.getBlock(cursor).getMaterial().isOpaque()) {
      leaves.set(editor, origin);
      if (rand.nextBoolean()) {
        leafSpill(editor, rand, theme, cursor, count - 1);
      }
      return;
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir);
      if (editor.getBlock(cursor).getMaterial().isOpaque()) {
        continue;
      }
      leaves.set(editor, origin);
      cursor.add(Cardinal.DOWN);
      if (editor.getBlock(cursor).getMaterial().isOpaque()) {
        continue;
      }
      leafSpill(editor, rand, theme, cursor, count - 1);
    }
  }

  private void pillar(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;

    editor.fillDown(rand, new Coord(origin), pillar);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, cursor);
    }
  }

}
