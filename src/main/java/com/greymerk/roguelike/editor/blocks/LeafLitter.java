package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeafLitterBlock;
import net.minecraft.util.math.random.Random;

public class LeafLitter {

	public static void set(IWorldEditor editor, Random rand, Coord origin) {
		if(!canPlaceAt(editor, origin.freeze())) return;
		Cardinal dir = Cardinal.randDirs(rand).getFirst();
		int count = rand.nextBetween(1, 4);
		MetaBlock.of(Blocks.LEAF_LITTER)
			.with(LeafLitterBlock.HORIZONTAL_FACING, Cardinal.facing(dir))
			.with(LeafLitterBlock.SEGMENT_AMOUNT, count)
			.set(editor, origin);
	}
	
	public static boolean canPlaceAt(IWorldEditor editor, Coord pos) {
		if(!editor.isAir(pos)) return false;
		if(!editor.isSupported(pos)) return false;
		if(!editor.isFaceFullSquare(pos.add(Cardinal.DOWN), Cardinal.UP)) return false;
		return true;
	}
	
}
