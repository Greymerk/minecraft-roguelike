package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.LeavesBlock;

public class Leaves {
	
	public static MetaBlock get(Wood type, boolean persistent){
		MetaBlock leaf = new MetaBlock(Wood.getLeaf(type));
		leaf.withProperty(LeavesBlock.PERSISTENT, persistent);
		return leaf;
	}
}
