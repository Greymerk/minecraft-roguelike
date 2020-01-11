package greymerk.roguelike.worldgen;

import java.util.Random;

import greymerk.roguelike.worldgen.shapes.IShape;

public interface IBlockFactory {

  boolean set(IWorldEditor editor, Random rand, Coord pos);

  boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);

  void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid);

  void fill(IWorldEditor editor, Random rand, IShape shape);

}
