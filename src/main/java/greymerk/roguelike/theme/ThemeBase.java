package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;

public class ThemeBase implements ITheme {
	
	protected IBlockSet primary;
	protected IBlockSet secondary;
	
	public ThemeBase(IBlockSet walls, IBlockSet decor){
		this.primary = walls;
		this.secondary = decor;
	}
	
	public ThemeBase(ThemeBase base, IBlockSet walls, IBlockSet decor){
		this.primary = walls == null ? base.primary : walls;
		this.secondary = decor == null ? base.secondary : decor;
	}
	
	public ThemeBase(){}
	
	@Override
	public IBlockFactory getPrimaryWall() {
		return primary.getFill();
	}
	
	@Override
	public IStair getPrimaryStair() {
		return primary.getStair();
	}

	@Override
	public IBlockFactory getPrimaryPillar() {
		return primary.getPillar();
	}
	
	@Override
	public IBlockFactory getSecondaryPillar() {
		return secondary.getPillar();
	}
	
	@Override
	public IBlockFactory getSecondaryWall() {
		return secondary.getFill();
	}

	@Override
	public IStair getSecondaryStair() {
		return secondary.getStair();
	}

	@Override
	public IBlockFactory getPrimaryFloor() {
		return primary.getFloor();
	}

	@Override
	public IBlockFactory getSecondaryFloor() {
		return secondary.getFloor();
	}
}
