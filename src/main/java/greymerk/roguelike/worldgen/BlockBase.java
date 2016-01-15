package greymerk.roguelike.worldgen;

import java.util.Random;

import greymerk.roguelike.worldgen.shapes.IShape;

public abstract class BlockBase implements IBlockFactory {
	
	@Override
	public boolean setBlock(IWorldEditor editor, Random rand, Coord pos) {
		return setBlock(editor, rand, pos, true, true);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid){
		shape.fill(editor, rand, this, fillAir, replaceSolid);
	}
	
	public void fillRectSolid(IWorldEditor editor, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid){
		editor.fillRectSolid(rand, start, end, this, fillAir, replaceSolid);
	}

	public void fillRectHollow(IWorldEditor editor, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid){
		editor.fillRectHollow(rand, start, end, this, fillAir, replaceSolid);
	}

}
