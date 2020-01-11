package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class BunkerTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {

    Coord origin = Tower.getBaseCoord(editor, dungeon);
    origin.add(Cardinal.UP);
    Coord cursor;
    Coord start;
    Coord end;

    IBlockFactory walls = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock window = ColorBlock.get(ColorBlock.PANE, DyeColor.GRAY);

    start = new Coord(origin);
    end = new Coord(start);
    start.add(Cardinal.DOWN);
    start.add(Cardinal.NORTH, 5);
    start.add(Cardinal.EAST, 5);
    end.add(Cardinal.SOUTH, 5);
    end.add(Cardinal.WEST, 5);
    end.add(Cardinal.UP, 4);
    RectHollow.fill(editor, rand, start, end, walls, true, true);

    start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
    end = new Coord(origin);
    end.add(Cardinal.DOWN);
    start.add(Cardinal.NORTH, 5);
    start.add(Cardinal.EAST, 5);
    end.add(Cardinal.SOUTH, 5);
    end.add(Cardinal.WEST, 5);
    RectSolid.fill(editor, rand, start, end, walls, true, true);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(dir, 5);
      end = new Coord(start);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.right(dir));
      start = new Coord(start.getX(), dungeon.getY() + 10, start.getZ());
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, walls, true, true);
      end.add(Cardinal.DOWN);
      end.add(dir);
      start.add(dir);
      RectSolid.fill(editor, rand, start, end, walls, true, true);
      end.add(Cardinal.DOWN);
      end.add(dir);
      start.add(dir);
      RectSolid.fill(editor, rand, start, end, walls, true, true);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir, 5);
      cursor.add(Cardinal.left(dir), 5);
      start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
      start.add(dir, 6);
      start.add(Cardinal.left(dir), 6);
      end = new Coord(origin);
      end.add(dir, 6);
      end.add(Cardinal.left(dir), 6);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, walls, true, true);
      start.add(dir);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.DOWN);
      end.add(dir);
      end.add(Cardinal.left(dir));
      RectSolid.fill(editor, rand, start, end, walls, true, true);


      for (Cardinal o : Cardinal.orthogonal(dir)) {
        start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
        start.add(dir, 5);
        start.add(o, 5);
        end = new Coord(origin);
        end.add(dir, 5);
        end.add(o, 5);
        end.add(Cardinal.UP, 2);
        end.add(o, 2);
        RectSolid.fill(editor, rand, start, end, walls, true, true);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      stair.setOrientation(dir, false);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        start = new Coord(origin);
        start.add(dir, 6);
        start.add(o, 6);
        start.add(Cardinal.UP, 3);
        end = new Coord(start);
        end.add(Cardinal.reverse(o));
        RectSolid.fill(editor, rand, start, end, stair);
        start.add(Cardinal.DOWN);
        start.add(dir);
        start.add(o);
        end = new Coord(start);
        end.add(Cardinal.reverse(o), 2);
        RectSolid.fill(editor, rand, start, end, stair);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 3);
      cursor.add(dir, 6);
      stair.setOrientation(dir, false).set(editor, cursor);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        stair.setOrientation(dir, false).set(editor, c);
      }
      cursor.add(Cardinal.DOWN);
      cursor.add(dir);
      stair.setOrientation(dir, false).set(editor, cursor);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        stair.setOrientation(dir, false).set(editor, c);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 4);
      cursor.add(dir, 5);
      start = new Coord(cursor);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 5);
      end.add(Cardinal.right(dir), 5);
      stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 5);
      cursor.add(dir, 4);
      stair.setOrientation(dir, false).set(editor, cursor);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        pillar.set(editor, rand, c);
        c.add(o);
        stair.setOrientation(dir, false).set(editor, c);
        c.add(o);
        pillar.set(editor, rand, c);
      }
      cursor.add(Cardinal.UP);
      window.set(editor, cursor);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        pillar.set(editor, rand, c);
        c.add(o);
        window.set(editor, c);
        c.add(o);
        pillar.set(editor, rand, c);
      }
      cursor.add(Cardinal.UP);
      start = new Coord(cursor);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 3);
      end.add(Cardinal.right(dir), 3);
      stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.reverse(dir));
      end.add(Cardinal.reverse(dir));
      stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      start.add(Cardinal.right(dir));
      end.add(Cardinal.left(dir));
      stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
      stair.setOrientation(Cardinal.left(dir), false).set(editor, start);
      stair.setOrientation(Cardinal.right(dir), false).set(editor, end);
      start.add(Cardinal.reverse(dir));
      end.add(Cardinal.reverse(dir));
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      start.add(Cardinal.right(dir));
      end.add(Cardinal.left(dir));
      stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
      stair.setOrientation(Cardinal.left(dir), false).set(editor, start);
      stair.setOrientation(Cardinal.right(dir), false).set(editor, end);
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 8);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.add(Cardinal.NORTH, 2);
    start.add(Cardinal.EAST, 2);
    end.add(Cardinal.SOUTH, 2);
    end.add(Cardinal.WEST, 2);
    RectSolid.fill(editor, rand, start, end, walls);
    cursor.add(Cardinal.UP);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.add(Cardinal.NORTH);
    start.add(Cardinal.EAST);
    end.add(Cardinal.SOUTH);
    end.add(Cardinal.WEST);
    RectSolid.fill(editor, rand, start, end, walls);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(Cardinal.UP, 3);
      start.add(dir, 4);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 4);
      end.add(Cardinal.right(dir), 4);
      stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));
    }

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(dir, 4);
      start.add(Cardinal.left(dir), 4);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar);
    }

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(Cardinal.UP, 5);
      start.add(dir, 3);
      start.add(Cardinal.left(dir), 3);
      end = new Coord(start);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, pillar);
    }

    for (Cardinal dir : Cardinal.directions) {
      Cardinal[] orth = Cardinal.orthogonal(dir);
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 2);
      cursor.add(dir, 5);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      BlockType.get(BlockType.REDSTONE_BLOCK).set(editor, cursor);
      cursor.add(Cardinal.reverse(dir));
      BlockType.get(BlockType.REDSTONE_LAMP_LIT).set(editor, cursor);
      cursor.add(Cardinal.reverse(dir));
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      for (Cardinal o : orth) {
        Coord c = new Coord(cursor);
        c.add(o);
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, c);
        c.add(dir);
        stair.setOrientation(o, true).set(editor, c);
      }
    }

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(dir, 5);
      end = new Coord(start);
      end.add(Cardinal.UP);
      end.add(dir, 3);
      RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));

      cursor = new Coord(start);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        start = new Coord(cursor);
        start.add(o, 2);
        start.add(Cardinal.UP);
        end = new Coord(start);
        end.add(o);
        stair.setOrientation(dir, false).fill(editor, rand, new RectSolid(start, end));
        start.add(Cardinal.UP);
        end.add(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, window);
        start.add(Cardinal.DOWN, 2);
        end.add(Cardinal.DOWN, 2);
        start.add(Cardinal.reverse(dir));
        end.add(Cardinal.reverse(dir));
        RectSolid.fill(editor, rand, start, end, walls);
        start.add(Cardinal.reverse(dir));
        end.add(Cardinal.reverse(dir));
        stair.setOrientation(Cardinal.reverse(dir), false).fill(editor, rand, new RectSolid(start, end));
      }

      cursor = new Coord(origin);
      cursor.add(dir, 3);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        stair.setOrientation(Cardinal.reverse(o), false).set(editor, c);
        c.add(dir);
        stair.setOrientation(Cardinal.reverse(o), false).set(editor, c);
      }


    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    start = new Coord(cursor.getX(), dungeon.getY(), cursor.getZ());
    end = new Coord(cursor);
    for (Coord c : new RectSolid(start, end)) {
      editor.spiralStairStep(rand, c, stair, pillar);
    }
  }
}
