package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HopperBlock;

public class Hopper {

	public static void generate(IWorldEditor editor, RandomSource rand, Coord origin, Cardinal dir) {
		MetaBlock.of(Blocks.HOPPER)
			.with(HopperBlock.FACING, Cardinal.facing(Cardinal.directions.contains(dir) ? Cardinal.reverse(dir) : dir))
			.set(editor, origin);
	}
}
