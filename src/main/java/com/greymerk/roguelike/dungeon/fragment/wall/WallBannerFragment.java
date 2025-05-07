package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.WallBanner;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class WallBannerFragment implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		if(editor.isAir(origin.copy().add(dir, 3).add(Cardinal.UP, 2))) return;
		if(!editor.isSupported(origin.copy().add(dir, 3).add(Cardinal.UP, 2))) return;
		WallBanner.generate(editor, rand, origin.copy().add(dir, 2).add(Cardinal.UP, 2), dir);
	}

}
