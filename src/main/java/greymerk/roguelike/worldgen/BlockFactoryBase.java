package greymerk.roguelike.worldgen;

import java.util.Random;

public abstract class BlockFactoryBase implements IBlockFactory {
	
	@Override
	public boolean setBlock(WorldEditor editor, Random rand, Coord pos) {
		return setBlock(editor, rand, pos, true, true);
	}
	
	@Override
	public void fillRectSolid(WorldEditor editor, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid){
		editor.fillRectSolid(rand, start, end, this, fillAir, replaceSolid);
	}
	
	@Override
	public void fillRectHollow(WorldEditor editor, Random rand, Coord start, Coord end, boolean fillAir, boolean replaceSolid){
		editor.fillRectHollow(rand, start, end, this, fillAir, replaceSolid);
	}
}
