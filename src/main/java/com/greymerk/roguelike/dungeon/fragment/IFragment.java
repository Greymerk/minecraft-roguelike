package com.greymerk.roguelike.dungeon.fragment;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public interface IFragment {

	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir);
	
}
