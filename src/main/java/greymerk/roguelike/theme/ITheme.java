package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;

public interface ITheme {

	public IBlockFactory getPrimaryFloor();
	
	public IBlockFactory getPrimaryWall();
		
	public IStair getPrimaryStair();
	
	public IBlockFactory getPrimaryPillar();
	
	public IBlockFactory getSecondaryFloor();
	
	public IBlockFactory getSecondaryWall();
	
	public IStair getSecondaryStair();
	
	public IBlockFactory getSecondaryPillar();
		
}
