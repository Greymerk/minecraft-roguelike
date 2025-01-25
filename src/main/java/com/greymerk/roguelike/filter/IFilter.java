package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public interface IFilter {

	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box);
	
}
