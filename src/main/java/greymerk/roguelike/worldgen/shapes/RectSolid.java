package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

public class RectSolid implements IShape {

  private Coord start;
  private Coord end;

  public RectSolid(Coord start, Coord end) {
    this.start = start;
    this.end = end;
  }

  public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block) {
    fill(editor, rand, start, end, block, true, true);
  }

  public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
    RectSolid rect = new RectSolid(start, end);
    for (Coord c : rect) {
      block.set(editor, rand, c, fillAir, replaceSolid);
    }
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
    fill(editor, rand, block, true, true);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
    for (Coord c : this) {
      block.set(editor, rand, c, fillAir, replaceSolid);
    }
  }

  @Override
  public List<Coord> get() {
    List<Coord> coords = new ArrayList<Coord>();

    for (Coord c : this) {
      coords.add(c);
    }

    return coords;
  }

  @Override
  public Iterator<Coord> iterator() {
    return new RectSolidIterator(this.start, this.end);
  }

  private class RectSolidIterator implements Iterator<Coord> {

    Coord cursor;
    Coord c1;
    Coord c2;

    public RectSolidIterator(Coord c1, Coord c2) {
      this.c1 = new Coord(c1);
      this.c2 = new Coord(c2);

      Coord.correct(this.c1, this.c2);
      cursor = new Coord(this.c1);
    }

    @Override
    public boolean hasNext() {
      return this.cursor.getY() <= this.c2.getY();
    }

    @Override
    public Coord next() {

      Coord toReturn = new Coord(cursor);

      if (cursor.getZ() == c2.getZ() && cursor.getX() == c2.getX()) {
        cursor = new Coord(c1.getX(), cursor.getY(), c1.getZ());
        cursor.add(Cardinal.UP);
        return toReturn;
      }

      if (cursor.getX() == c2.getX()) {
        cursor = new Coord(c1.getX(), cursor.getY(), cursor.getZ());
        cursor.add(Cardinal.SOUTH);
        return toReturn;
      }

      cursor.add(Cardinal.EAST);
      return toReturn;

    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
