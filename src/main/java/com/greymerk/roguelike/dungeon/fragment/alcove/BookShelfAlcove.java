package com.greymerk.roguelike.dungeon.fragment.alcove;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BookShelf;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class BookShelfAlcove implements IFragment{

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		Coord pos = origin.copy().add(dir, 3).add(Cardinal.UP);
		if(editor.isAir(pos)) return;
		
		BookShelf.set(editor, rand, settings.getDifficulty(), pos, Cardinal.reverse(dir));
	}
}
