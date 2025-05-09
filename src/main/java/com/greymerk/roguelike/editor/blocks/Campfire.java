package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum Campfire {

	NATURAL, SOUL;
	
	public static void generate(IWorldEditor editor, Coord origin, Campfire type) {
		MetaBlock.of(fromType(type)).set(editor, origin);
	}
	
	public static void generate(IWorldEditor editor, Coord origin, ITheme theme) {
		MetaBlock.of(fromType(theme.getPrimary().naturalFire() ? NATURAL : SOUL)).set(editor, origin);
	}
	
	public static Block fromType(Campfire type) {
		switch(type) {
		case NATURAL: return Blocks.CAMPFIRE;
		case SOUL: return Blocks.SOUL_CAMPFIRE;
		default: return Blocks.CAMPFIRE;
		}
	}
	
}
