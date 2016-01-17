package greymerk.roguelike.worldgen;

import java.util.Random;

import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public abstract class BlockBase implements IBlockFactory {
	
	@Override
	public boolean setBlock(IWorldEditor editor, Random rand, Coord pos) {
		return setBlock(editor, rand, pos, true, true);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape){
		shape.fill(editor, rand, this, true, true);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid){
		shape.fill(editor, rand, this, fillAir, replaceSolid);
	}
	
	@Deprecated
	public void fillRectSolid(IWorldEditor editor, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid){
		RectSolid.fill(editor, rand, start, end, this, fillAir, replaceSolid);
	}

	@Deprecated
	public void fillRectHollow(IWorldEditor editor, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid){
		RectHollow.fill(editor, rand, start, end, this, fillAir, replaceSolid);
	}

}
