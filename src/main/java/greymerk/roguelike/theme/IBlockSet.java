package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.blocks.door.IDoor;

public interface IBlockSet {

  IBlockFactory getFloor();

  IBlockFactory getWall();

  IStair getStair();

  IBlockFactory getPillar();

  IDoor getDoor();

  IBlockFactory getLightBlock();

  IBlockFactory getLiquid();

}
