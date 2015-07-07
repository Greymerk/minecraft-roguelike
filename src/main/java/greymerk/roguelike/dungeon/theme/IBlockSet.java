package greymerk.roguelike.dungeon.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public interface IBlockSet {

	public IBlockFactory getFloor();
	public IBlockFactory getFill();
	public MetaBlock getStair();
	public IBlockFactory getPillar();
	
}
