package com.greymerk.roguelike.filter;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.theme.ITheme;

public interface IFilter {

	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box);
	
}
