package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum Campfire {

	NATURAL, SOUL;
	
	public static void generate(IWorldEditor editor, Coord origin, Campfire type) {
		MetaBlock fire = new MetaBlock(fromType(type));
		fire.set(editor, origin);
	}
	
	public static Block fromType(Campfire type) {
		switch(type) {
		case NATURAL:
			return Blocks.CAMPFIRE;
		case SOUL:
			return Blocks.SOUL_CAMPFIRE;
		default:
			return Blocks.CAMPFIRE;
		}
	}
	
}
