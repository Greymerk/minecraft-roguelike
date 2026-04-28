package com.greymerk.roguelike.dungeon.tower;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.theme.ITheme;
import net.minecraft.util.RandomSource;

public interface ITower {

	public void generate(IWorldEditor editor, RandomSource rand, ITheme theme, Coord origin);
		
}
