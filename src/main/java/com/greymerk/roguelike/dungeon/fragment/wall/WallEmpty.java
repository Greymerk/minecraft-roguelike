package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class WallEmpty implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		// this page intentionally blank
	}

}
