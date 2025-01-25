package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.chest.ChestType;

import net.minecraft.util.math.random.Random;

public class WallFoodBarrel implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		Coord pos = origin.copy().add(dir, 2);
		Treasure.generate(editor, rand, settings.getDifficulty(), pos, Treasure.FOOD, ChestType.BARREL);
	}

}
