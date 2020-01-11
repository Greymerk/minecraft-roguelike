package greymerk.roguelike.worldgen;

import java.util.Random;

import greymerk.roguelike.worldgen.shapes.IShape;

public abstract class BlockBase implements IBlockFactory {

  @Override
  public boolean set(IWorldEditor editor, Random rand, Coord pos) {
    return set(editor, rand, pos, true, true);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IShape shape) {
    shape.fill(editor, rand, this, true, true);
  }

  @Override
  public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid) {
    shape.fill(editor, rand, this, fillAir, replaceSolid);
  }
}
