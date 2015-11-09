package greymerk.roguelike.worldgen;

import java.util.Random;

public interface IStair extends IBlockFactory{

	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown);
	
	public boolean setBlock(WorldEditor editor, Coord pos);
	
	public boolean setBlock(WorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);
	
}
