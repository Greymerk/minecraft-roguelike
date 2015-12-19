package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;

public class ThemeBase implements ITheme {
	
	protected IBlockSet primary;
	protected IBlockSet secondary;
	
	public ThemeBase(IBlockSet primary, IBlockSet secondary){
		this.primary = primary;
		this.secondary = secondary;
	}
	
	public ThemeBase(ThemeBase base, IBlockSet primary, IBlockSet secondary){
		this.primary = primary == null ? base.primary : primary;
		this.secondary = secondary == null ? base.secondary : secondary;
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
