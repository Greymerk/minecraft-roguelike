package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.FragmentBase;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.treasure.ChestType;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.util.math.random.Random;

public class WallFoodBarrel extends FragmentBase implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		Coord pos = origin.copy().add(dir, 2);
		Treasure.generate(editor, rand, pos, Treasure.FOOD, ChestType.BARREL);
	}

}
