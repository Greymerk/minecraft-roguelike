package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.TallPlant;
import com.greymerk.roguelike.editor.blocks.Trapdoor;
import com.greymerk.roguelike.editor.blocks.Wood;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class WallPlant implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		MetaBlock.of(Blocks.COARSE_DIRT).set(editor, origin.copy().add(dir, 2));
		Cardinal.directions.forEach(d -> {
			Trapdoor.getWooden(Wood.SPRUCE, Cardinal.reverse(d), true, true)
				.set(editor, rand, origin.copy().add(dir, 2).add(d), Fill.ONLY_AIR);
		});
		TallPlant.generate(editor, rand, origin.copy().add(dir, 2).add(Cardinal.UP));
	}

}
