package greymerk.roguelike.worldgen;

import java.util.Random;

public interface IStair extends IBlockFactory {

  MetaStair setOrientation(Cardinal dir, Boolean upsideDown);

  boolean set(IWorldEditor editor, Coord pos);

  boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);

}
