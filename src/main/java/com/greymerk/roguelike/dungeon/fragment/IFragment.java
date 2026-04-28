package com.greymerk.roguelike.dungeon.fragment;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.settings.ILevelSettings;
import net.minecraft.util.RandomSource;

public interface IFragment {

	public void generate(IWorldEditor editor, RandomSource rand, ILevelSettings settings, Coord origin, Cardinal dir);
	
}
