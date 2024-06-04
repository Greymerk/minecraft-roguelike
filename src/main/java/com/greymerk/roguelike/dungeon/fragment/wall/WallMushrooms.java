package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.FlowerPot;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class WallMushrooms implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		theme.getSecondary().getStair().setOrientation(Cardinal.reverse(dir), true)
			.fill(editor, rand, BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)));
	
		BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP).grow(Cardinal.orthogonal(dir)).forEach(pos -> {
			if(rand.nextBoolean()) FlowerPot.generate(editor, rand, pos, FlowerPot.mushrooms);
		});
	}
}
