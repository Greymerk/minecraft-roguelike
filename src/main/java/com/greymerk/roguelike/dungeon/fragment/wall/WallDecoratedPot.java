package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.DecoratedPot;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class WallDecoratedPot implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		Coord pos = origin.copy().add(dir, 2);
		DecoratedPot.set(editor, rand, settings.getDifficulty(), pos);
	}

}
