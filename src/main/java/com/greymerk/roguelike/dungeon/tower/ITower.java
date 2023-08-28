package com.greymerk.roguelike.dungeon.tower;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.theme.ITheme;

public interface ITower {

	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin);
		
}
