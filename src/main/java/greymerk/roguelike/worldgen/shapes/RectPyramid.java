package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

public class RectPyramid implements IShape {

  private Coord start;
  private Coord end;

  public RectPyramid(Coord start, Coord end) {
    this.start = new Coord(start);
    this.end = new Coord(end);
  }


  @Override
  public Iterator<Coord> iterator() {
    return new SquarePyramidIterator(start, end);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
    fill(editor, rand, block, true, true);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
    for (Coord pos : this) {
      block.set(editor, rand, pos, fillAir, replaceSolid);
    }

  }

  @Override
  public List<Coord> get() {
    List<Coord> shape = new ArrayList<>();
    for (Coord pos : this) {
      shape.add(pos);
    }
    return shape;
  }

  private class SquarePyramidIterator implements Iterator<Coord> {

    Coord start;
    Coord diff;
    Coord cursor;
    Cardinal dir;
    double thetaX;
    double thetaZ;

    public SquarePyramidIterator(Coord start, Coord end) {
      this.start = new Coord(start);
      Coord s = new Coord(start);
      Coord e = new Coord(end);
      Coord.correct(s, e);

      cursor = new Coord(0, 0, 0);
      dir = Cardinal.NORTH;

      diff = new Coord(e);
      diff.sub(s);

      double hx = Math.sqrt(Math.pow(diff.getX(), 2) + Math.pow(diff.getY(), 2));
      thetaX = Math.acos((double) diff.getY() / hx);

      double hz = Math.sqrt(Math.pow(diff.getZ(), 2) + Math.pow(diff.getY(), 2));
      thetaZ = Math.acos((double) diff.getY() / hz);
    }

    @Override
    public boolean hasNext() {
      return cursor.getY() < this.diff.getY();
    }

    @Override
    public Coord next() {

      Coord toReturn = new Coord(start);
      toReturn.add(Cardinal.UP, cursor.getY());
      if (dir == Cardinal.NORTH || dir == Cardinal.SOUTH) {
        toReturn.add(Cardinal.left(dir), cursor.getX());
        toReturn.add(dir, cursor.getZ());
      } else {
        toReturn.add(dir, cursor.getX());
        toReturn.add(Cardinal.left(dir), cursor.getZ());
      }

      if (this.dir != Cardinal.NORTH) {
        dir = Cardinal.left(dir);
        return toReturn;
      }

      cursor.add(Cardinal.SOUTH);

      if (inRange(cursor)) {
        dir = Cardinal.left(dir);
        return toReturn;
      }

      cursor = new Coord(cursor.getX(), cursor.getY(), 0);


      cursor.add(Cardinal.EAST);

      if (inRange(cursor)) {
        dir = Cardinal.left(dir);
        return toReturn;
      }

      cursor = new Coord(0, cursor.getY(), cursor.getZ());
      cursor.add(Cardinal.UP);
      dir = Cardinal.left(dir);
      return toReturn;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    private boolean inRange(Coord pos) {
      int y = diff.getY() - cursor.getY();

      if (!(cursor.getX() < Math.tan(thetaX) * y)) {
        return false;
      }

      return cursor.getZ() < Math.tan(thetaZ) * y;

    }

  }

}
