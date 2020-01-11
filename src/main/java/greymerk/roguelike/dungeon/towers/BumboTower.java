package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class BumboTower implements ITower {


  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {


    Coord ground = Tower.getBaseCoord(editor, origin);

    Cardinal dir = Cardinal.directions[rand.nextInt(Cardinal.directions.length)];

    stem(editor, rand, theme, ground, dir);

    Coord pos = new Coord(ground);
    pos.add(Cardinal.right(dir), 5);
    pos.add(Cardinal.UP, 9);
    arm(editor, rand, theme, pos, Cardinal.right(dir));

    pos = new Coord(ground);
    pos.add(Cardinal.left(dir), 5);
    pos.add(Cardinal.UP, 10);
    arm(editor, rand, theme, pos, Cardinal.left(dir));

    pos = new Coord(ground);
    pos.add(Cardinal.UP, 16);
    hat(editor, rand, theme, pos);

    pos = new Coord(ground);
    pos.add(Cardinal.UP, 10);
    pos.add(dir, 4);
    face(editor, rand, theme, pos, dir);

    rooms(editor, rand, theme, ground, dir);

    pos = new Coord(ground);
    pos.add(Cardinal.UP, 11);
    for (Coord c : new RectSolid(origin, pos)) {
      editor.spiralStairStep(rand, c, theme.getPrimary().getStair(), theme.getPrimary().getWall());
    }
  }

  private void stem(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
    IBlockFactory green = theme.getPrimary().getWall();

    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(dir, 4);
    start.add(Cardinal.left(dir), 3);
    end.add(Cardinal.reverse(dir), 4);
    end.add(Cardinal.right(dir), 3);
    start.add(Cardinal.DOWN, 10);
    end.add(Cardinal.UP, 16);

    RectSolid.fill(editor, rand, start, end, green);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.left(dir), 4);
    start.add(dir, 3);
    end.add(Cardinal.left(dir), 4);
    end.add(Cardinal.reverse(dir), 3);
    start.add(Cardinal.DOWN, 10);
    end.add(Cardinal.UP, 16);

    RectSolid.fill(editor, rand, start, end, green);

    start.add(Cardinal.right(dir), 8);
    end.add(Cardinal.right(dir), 8);

    RectSolid.fill(editor, rand, start, end, green);


  }

  private void arm(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
    IBlockFactory green = theme.getPrimary().getWall();

    Coord start;
    Coord end;
    start = new Coord(origin);
    end = new Coord(origin);

    start.add(dir, 5);
    start.add(Cardinal.DOWN, 4);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    end.add(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, green);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(dir, 5);
    end.add(dir, 3);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, green);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    end.add(dir, 2);
    RectSolid.fill(editor, rand, start, end, green);

    start.add(dir, 2);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, green);

    start.add(Cardinal.DOWN, 3);
    start.add(dir, 3);
    end.add(dir, 3);
    RectSolid.fill(editor, rand, start, end, green);

    start = new Coord(origin);
    start.add(dir, 6);
    end = new Coord(start);
    start.add(Cardinal.UP, 2);
    end.add(Cardinal.DOWN, 4);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    RectSolid.fill(editor, rand, start, end, green);

    start = new Coord(origin);
    start.add(Cardinal.DOWN, 5);
    end = new Coord(start);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    end.add(dir, 5);
    RectSolid.fill(editor, rand, start, end, green);

    start = new Coord(origin);
    start.add(dir, 3);
    start.add(Cardinal.UP, 3);
    end = new Coord(start);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    end.add(dir, 2);
    RectSolid.fill(editor, rand, start, end, green);
  }

  private void hat(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
    IBlockFactory yellow = theme.getSecondary().getWall();
    IBlockFactory red = theme.getSecondary().getFloor();

    Coord offset = new Coord(origin.getX(), 0, origin.getZ());
    IBlockFactory rim = new BlockCheckers(red, yellow, offset);
    IBlockFactory rim2 = new BlockCheckers(yellow, red, offset);

    Coord start;
    Coord end;
    Coord pos;

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-2, 0, -2));
    end.add(new Coord(2, 8, 2));
    RectSolid.fill(editor, rand, start, end, yellow);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(dir, 3);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 2);
      end.add(Cardinal.right(dir), 2);
      end.add(Cardinal.UP, 5);
      RectSolid.fill(editor, rand, start, end, yellow);

      start = new Coord(origin);
      start.add(dir, 4);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 2);
      end.add(Cardinal.right(dir), 2);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, yellow);

      start = new Coord(origin);
      start.add(dir, 3);
      start.add(Cardinal.left(dir), 3);
      end = new Coord(start);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, yellow);

      for (Cardinal o : Cardinal.orthogonal(dir)) {
        start = new Coord(origin);
        start.add(dir, 3);
        end = new Coord(start);
        end.add(dir, 3);
        end.add(o, 5);
        RectSolid.fill(editor, rand, start, end, yellow);
      }


      start = new Coord(origin);
      start.add(dir, 7);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 4);
      end.add(Cardinal.right(dir), 4);
      RectSolid.fill(editor, rand, start, end, rim2);
      start.add(dir);
      end.add(dir);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, rim);

      for (Cardinal o : Cardinal.orthogonal(dir)) {
        pos = new Coord(origin);
        pos.add(dir, 5);
        pos.add(o, 5);
        rim.set(editor, rand, pos);
        pos.add(dir);
        rim.set(editor, rand, pos);
        pos.add(Cardinal.UP);
        pos.add(dir);
        rim2.set(editor, rand, pos);
        pos.add(Cardinal.reverse(dir));
        pos.add(o);
        rim.set(editor, rand, pos);
      }
    }
  }

  private void face(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
    IBlockFactory black = theme.getSecondary().getPillar();
    IBlockFactory white = theme.getPrimary().getPillar();
    IBlockFactory moustache = theme.getPrimary().getFloor();
    IBlockFactory green = theme.getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord start;
    Coord end;
    Coord pos;


    // mouth
    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    end.add(dir);
    end.add(Cardinal.DOWN, 3);
    RectSolid.fill(editor, rand, start, end, green);
    start.add(Cardinal.right(dir));
    start.add(Cardinal.DOWN);
    end.add(Cardinal.left(dir));
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
    pos = new Coord(origin);
    pos.add(dir);
    pos.add(Cardinal.right(dir), 2);
    pos.add(Cardinal.DOWN, 3);
    air.set(editor, pos);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.reverse(dir));
    end.add(Cardinal.reverse(dir));
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    start.add(Cardinal.DOWN);
    end.add(Cardinal.DOWN, 2);
    RectSolid.fill(editor, rand, start, end, black);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      pos = new Coord(origin);
      pos.add(Cardinal.DOWN);
      pos.add(o);
      white.set(editor, rand, pos);
    }

    pos = new Coord(origin);
    pos.add(dir, 2);
    moustache.set(editor, rand, pos);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      start = new Coord(origin);
      start.add(dir, 2);
      end = new Coord(start);
      end.add(o, 2);
      start.add(o);
      start.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, moustache);
      start.add(o, 4);
      start.add(Cardinal.DOWN, 2);
      RectSolid.fill(editor, rand, start, end, moustache);
      pos = new Coord(origin);
      pos.add(dir, 2);
      pos.add(o, 4);
      air.set(editor, pos);
    }

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      pos = new Coord(origin);
      pos.add(o, 2);
      if (o == Cardinal.right(dir)) {
        pos.add(Cardinal.UP);
      }
      pos.add(Cardinal.UP, 2);
      air.set(editor, rand, pos);
      pos.add(Cardinal.UP);
      air.set(editor, rand, pos);
    }
  }

  private void rooms(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(Cardinal.UP, 7);
    end = new Coord(start);
    start.add(new Coord(-3, 0, -3));
    end.add(new Coord(3, 3, 3));
    RectSolid.fill(editor, rand, start, end, air);
    start.add(Cardinal.UP, 5);
    end.add(Cardinal.UP, 5);
    RectSolid.fill(editor, rand, start, end, air);

    for (Cardinal d : Cardinal.directions) {
      start = new Coord(origin);
      start.add(Cardinal.UP, 5);
      start.add(d, 3);
      start.add(Cardinal.left(d), 3);
      end = new Coord(start);
      end.add(Cardinal.UP, 10);
      RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
    }
  }
}
