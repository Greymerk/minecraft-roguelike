package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;

public class Air {

	public static MetaBlock get() {
		return new MetaBlock(Blocks.AIR);
	}
	
}
