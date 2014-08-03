package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public interface ITheme {

	public IBlockFactory getPrimaryWall();
		
	public MetaBlock getPrimaryStair();
	
	public IBlockFactory getPrimaryPillar();
	
	public IBlockFactory getSecondaryWall();
	
	public MetaBlock getSecondaryStair();
	
	public IBlockFactory getSecondaryPillar();
		
}
