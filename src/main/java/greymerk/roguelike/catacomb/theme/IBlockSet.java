package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public interface IBlockSet {

	public IBlockFactory getFill();
	public MetaBlock getStair();
	public IBlockFactory getPillar();
	
}
