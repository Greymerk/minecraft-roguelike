package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.Skull;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class WallSkulls implements IFragment{

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		ITheme theme = settings.getTheme();
		theme.getSecondary().getStair().setOrientation(Cardinal.reverse(dir), true)
		.fill(editor, rand, BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)));

		BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP).grow(Cardinal.orthogonal(dir)).forEach(pos -> {
			if(rand.nextInt(3) == 0) {
				Skull.set(editor, rand, pos, Cardinal.reverse(dir));
			} else {
				if(rand.nextInt(3) == 0) {
					Candle.generate(editor, rand, pos);
				}
			}
		});
	}

}
