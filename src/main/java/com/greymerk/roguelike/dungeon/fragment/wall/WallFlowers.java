package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.FlowerPot;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class WallFlowers implements IFragment {
	
	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		settings.getTheme().getSecondary().getStair().setOrientation(Cardinal.reverse(dir), true)
			.fill(editor, rand, BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)));

		BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP).grow(Cardinal.orthogonal(dir)).forEach(pos -> {
			if(rand.nextBoolean()) FlowerPot.generate(editor, rand, pos);
		});
		
		Lantern.set(editor, origin.copy().add(dir, 2).add(Cardinal.UP, 2));
	}
}
