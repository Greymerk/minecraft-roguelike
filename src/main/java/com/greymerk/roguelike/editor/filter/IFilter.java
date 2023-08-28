package com.greymerk.roguelike.editor.filter;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.theme.ITheme;

public interface IFilter {

	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box);
	
}
