package greymerk.roguelike.worldgen;

import java.util.Random;

public interface IStair extends IBlockFactory{

	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown);
	
	public boolean setBlock(IWorldEditor editor, Coord pos);
	
	public boolean setBlock(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);
	
}
