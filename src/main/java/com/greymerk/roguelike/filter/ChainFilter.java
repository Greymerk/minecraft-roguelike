package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class ChainFilter implements IFilter {

	@Override
	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box) {
		box.forEach(c -> {
			if(rand.nextInt(50) != 0) return;
			if(!editor.isFaceFullSquare(c.copy().add(Cardinal.UP), Cardinal.DOWN)) return;
			if(!BoundingBox.of(c).grow(Cardinal.DOWN, 4).stream()
				.allMatch(c2 -> editor.isAir(c2))) return;
			BoundingBox.of(c).grow(Cardinal.DOWN, rand.nextInt(3) + 1)
				.fill(editor, rand, MetaBlock.of(Blocks.IRON_CHAIN), Fill.AIR);
		});
	}
}
