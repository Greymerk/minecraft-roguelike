package com.greymerk.roguelike.theme;

public abstract class ThemeBase implements ITheme {
	
	protected IBlockSet primary;
	protected IBlockSet secondary;

	
	
	@Override
	public IBlockSet getPrimary() {
		return this.primary != null ? primary : BlockSet.builder().build();
	}

	@Override
	public IBlockSet getSecondary() {
		return this.secondary != null ? secondary : primary;
	}
}
