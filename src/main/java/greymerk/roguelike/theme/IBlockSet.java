package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.blocks.door.IDoor;

public interface IBlockSet {

	public IBlockFactory getFloor();
	public IBlockFactory getWall();
	public IStair getStair();
	public IBlockFactory getPillar();
	public IDoor getDoor();
	public IBlockFactory getLightBlock();
	public IBlockFactory getLiquid();
	
}
