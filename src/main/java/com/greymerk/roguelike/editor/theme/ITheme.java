package com.greymerk.roguelike.editor.theme;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;

import net.minecraft.util.math.random.Random;

public interface ITheme {
	
	public IBlockSet getPrimary();
	
	public IBlockSet getSecondary();
	
	public void applyFilters(IWorldEditor editor, Random rand, IBounded box);
	
	public String getName();
	
}
