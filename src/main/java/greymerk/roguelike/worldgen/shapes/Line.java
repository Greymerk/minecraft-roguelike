package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

public class Line implements IShape {

  Coord start;
  Coord end;

  public Line(Coord start, Coord end) {
    this.start = new Coord(start);
    this.end = new Coord(end);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
    this.fill(editor, rand, block, true, true);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
    for (Coord c : this) {
      block.set(editor, rand, c, fillAir, replaceSolid);
    }
  }

  @Override
  public List<Coord> get() {
    List<Coord> coords = new ArrayList<>();
    for (Coord c : this) {
      coords.add(c);
    }
    return coords;
  }

  @Override
  public Iterator<Coord> iterator() {
    return new LineIterator();
  }

  private class LineIterator implements Iterator<Coord> {

    int x;
    int y;
    int z;
    int dx;
    int dy;
    int dz;
    int l;
    int m;
    int n;
    int x_inc;
    int y_inc;
    int z_inc;
    int err_1;
    int err_2;
    int dx2;
    int dy2;
    int dz2;
    int i;

    public LineIterator() {

      Coord point = new Coord(start);
      x = point.getX();
      y = point.getY();
      z = point.getZ();
      dx = end.getX() - start.getX();
      dy = end.getY() - start.getY();
      dz = end.getZ() - start.getZ();
      x_inc = dx < 0 ? -1 : 1;
      l = Math.abs(dx);
      y_inc = dy < 0 ? -1 : 1;
      m = Math.abs(dy);
      z_inc = dz < 0 ? -1 : 1;
      n = Math.abs(dz);
      dx2 = l << 1;
      dy2 = m << 1;
      dz2 = n << 1;
      i = 0;

      if (l >= m && l >= n) {
        err_1 = dy2 - l;
        err_2 = dz2 - l;
      } else if (m >= l && m >= n) {
        err_1 = dx2 - m;
        err_2 = dz2 - m;
      } else {
        err_1 = dy2 - n;
        err_2 = dz2 - n;
      }

    }

    @Override
    public boolean hasNext() {
      if (l >= m && l >= n) {
        return i < l;
      } else if (m >= l && m >= n) {
        return i < m;
      } else {
        return i < n;
      }
    }

    @Override
    public Coord next() {
      if (l >= m && l >= n) {
        if (err_1 > 0) {
          y += y_inc;
          err_1 -= dx2;
        }
        if (err_2 > 0) {
          z += z_inc;
          err_2 -= dx2;
        }
        err_1 += dy2;
        err_2 += dz2;
        x += x_inc;
      } else if (m >= l && m >= n) {
        if (err_1 > 0) {
          x += x_inc;
          err_1 -= dy2;
        }
        if (err_2 > 0) {
          z += z_inc;
          err_2 -= dy2;
        }
        err_1 += dx2;
        err_2 += dz2;
        y += y_inc;
      } else {
        if (err_1 > 0) {
          y += y_inc;
          err_1 -= dz2;
        }
        if (err_2 > 0) {
          x += x_inc;
          err_2 -= dz2;
        }
        err_1 += dy2;
        err_2 += dx2;
        z += z_inc;
      }

      ++i;

      return new Coord(x, y, z);
    }
  }

}
