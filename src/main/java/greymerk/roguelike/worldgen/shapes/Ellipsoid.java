package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

public class Ellipsoid implements IShape {

  private Coord start;
  private Coord end;

  public Ellipsoid(Coord start, Coord end) {
    this.start = new Coord(start);
    this.end = new Coord(end);
  }

  @Override
  public Iterator<Coord> iterator() {
    return new EllipsoidIterator(start, end);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
    this.fill(editor, rand, block, true, true);

  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
    for (Coord pos : this) {
      block.set(editor, rand, pos, fillAir, replaceSolid);
    }
  }

  @Override
  public List<Coord> get() {
    List<Coord> copy = new ArrayList<>();
    for (Coord pos : this) {
      copy.add(pos);
    }
    return copy;
  }

  private class EllipsoidIterator implements Iterator<Coord> {

    private Coord centre;
    private Coord diff;
    private Coord cursor;

    private Cardinal dir;
    private boolean top;


    public EllipsoidIterator(Coord centre, Coord end) {
      this.centre = new Coord(centre);
      Coord s = new Coord(centre);
      Coord e = new Coord(end);

      this.diff = e.sub(s);
      this.diff = new Coord(Math.abs(diff.getX()), Math.abs(diff.getY()), Math.abs(diff.getZ()));

      cursor = new Coord(0, 0, 0);
      top = true;
      this.dir = Cardinal.NORTH;
    }

    @Override
    public boolean hasNext() {
      return cursor.getY() < diff.getY();
    }

    @Override
    public Coord next() {
      Coord toReturn = new Coord(centre);
      toReturn.add(top ? Cardinal.UP : Cardinal.DOWN, cursor.getY());
      if (dir == Cardinal.NORTH || dir == Cardinal.SOUTH) {
        toReturn.add(Cardinal.left(dir), cursor.getX());
        toReturn.add(dir, cursor.getZ());
      } else {
        toReturn.add(dir, cursor.getX());
        toReturn.add(Cardinal.left(dir), cursor.getZ());
      }

      if (this.dir != Cardinal.NORTH || top) {
        if (this.dir == Cardinal.NORTH) {
          top = false;
        }
        dir = Cardinal.left(dir);
        return toReturn;
      }

      cursor.add(Cardinal.SOUTH);

      if (inRange(cursor)) {
        dir = Cardinal.left(dir);
        top = true;
        return toReturn;
      } else {
        cursor = new Coord(cursor.getX(), cursor.getY(), 0);
      }

      cursor.add(Cardinal.EAST);

      if (inRange(cursor)) {
        dir = Cardinal.left(dir);
        top = true;
        return toReturn;
      } else {
        cursor = new Coord(0, cursor.getY(), cursor.getZ());
      }

      cursor.add(Cardinal.UP);
      dir = Cardinal.left(dir);
      top = true;
      return toReturn;
    }

    private boolean inRange(Coord pos) {
      double x = pos.getX();
      double y = pos.getY();
      double z = pos.getZ();

      double rx = this.diff.getX() == 0 ? 1 : this.diff.getX();
      double ry = this.diff.getY() == 0 ? 1 : this.diff.getY();
      double rz = this.diff.getZ() == 0 ? 1 : this.diff.getZ();

      return ((x / rx) * (x / rx)) +
          ((y / ry) * (y / ry)) +
          ((z / rz) * (z / rz))
          <= 1;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
