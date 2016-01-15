package greymerk.roguelike.worldgen;

import java.util.Random;

import greymerk.roguelike.worldgen.shapes.IShape;

public interface IBlockFactory {
	
	public boolean setBlock(IWorldEditor editor, Random rand, Coord pos);
	
	public boolean setBlock(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);
	
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid);
	
	@Deprecated
	public void fillRectSolid(IWorldEditor editor, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid);

	@Deprecated
	public void fillRectHollow(IWorldEditor editor, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid);
	
}
