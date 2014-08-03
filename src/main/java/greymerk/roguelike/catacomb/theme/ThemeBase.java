package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public class ThemeBase implements ITheme {
	
	protected IBlockSet walls;
	protected IBlockSet decor;
	
	public ThemeBase(IBlockSet walls, IBlockSet decor){
		this.walls = walls;
		this.decor = decor;
	}
	
	public ThemeBase(ThemeBase base, IBlockSet walls, IBlockSet decor){
		this.walls = walls == null ? base.walls : walls;
		this.decor = decor == null ? base.decor : decor;
	}
	
	public ThemeBase(){}
	
	@Override
	public IBlockFactory getPrimaryWall() {
		return walls.getFill();
	}
	
	@Override
	public MetaBlock getPrimaryStair() {
		return walls.getStair();
	}

	@Override
	public IBlockFactory getPrimaryPillar() {
		return walls.getPillar();
	}
	
	@Override
	public IBlockFactory getSecondaryPillar() {
		return decor.getPillar();
	}
	
	@Override
	public IBlockFactory getSecondaryWall() {
		return decor.getFill();
	}

	@Override
	public MetaBlock getSecondaryStair() {
		return decor.getStair();
	}
}
