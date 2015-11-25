package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;

public interface IBlockSet {

	public IBlockFactory getFloor();
	public IBlockFactory getFill();
	public IStair getStair();
	public IBlockFactory getPillar();
	
}
