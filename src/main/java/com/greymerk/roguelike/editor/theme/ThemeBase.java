package com.greymerk.roguelike.editor.theme;

import java.util.List;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.filter.IFilter;

import net.minecraft.util.math.random.Random;

public abstract class ThemeBase implements ITheme {
	
	protected BlockSet primary;
	protected BlockSet secondary;
	protected List<IFilter> filters;

	@Override
	public IBlockSet getPrimary() {
		return this.primary != null ? primary : new BlockSet();
	}

	@Override
	public IBlockSet getSecondary() {
		return this.secondary != null ? secondary : primary;
	}
	
	@Override
	public void applyFilters(IWorldEditor editor, Random rand, IBounded box) {
		if(this.filters == null) return;
		for(IFilter filter : this.filters) {
			filter.apply(editor, rand, this, box);
		}
	}
}
