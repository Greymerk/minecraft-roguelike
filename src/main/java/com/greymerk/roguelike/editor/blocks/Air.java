package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;

public class Air {

	public static MetaBlock get() {
		return MetaBlock.of(Blocks.CAVE_AIR);
	}
	
}
