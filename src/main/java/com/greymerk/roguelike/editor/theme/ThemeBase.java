package com.greymerk.roguelike.editor.theme;

public abstract class ThemeBase implements ITheme {
	
	protected BlockSet primary;
	protected BlockSet secondary;

	@Override
	public IBlockSet getPrimary() {
		return this.primary != null ? primary : new BlockSet();
	}

	@Override
	public IBlockSet getSecondary() {
		return this.secondary != null ? secondary : primary;
	}
}
