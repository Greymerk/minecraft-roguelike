package greymerk.roguelike.worldgen.shapes;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

import static java.util.stream.Collectors.toList;

public class MultiShape implements IShape {

  private Set<Coord> shape;

  public MultiShape() {
    shape = new HashSet<>();
  }

  public void addShape(IShape toAdd) {
    for (Coord pos : toAdd) {
      shape.add(pos);
    }
  }

  @Override
  public Iterator<Coord> iterator() {
    return shape.iterator();
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
    return shape.stream()
        .map(Coord::new)
        .collect(toList());
  }
}
