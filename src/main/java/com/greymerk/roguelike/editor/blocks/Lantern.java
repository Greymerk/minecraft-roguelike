package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;

public enum Lantern {

	SOUL, FLAME;
	
	public static void set(IWorldEditor editor, Coord origin) {
		Lantern.set(editor, origin, FLAME, true);
	}
	
	public static void set(IWorldEditor editor, Coord origin, Lantern type, boolean hang) {
		if(!editor.getBlock(origin).isReplaceable()) return;
		MetaBlock.of(fromType(type).getDefaultState())
			.with(LanternBlock.HANGING, hang)
			.set(editor, origin);
	}
	
	public static void set(IWorldEditor editor, Coord pos, ITheme theme, boolean hang) {
		Lantern.set(editor, pos, theme.getPrimary().naturalFire() ? FLAME : SOUL, hang);
	}
	
	public static Block fromType(Lantern type) {
		switch(type) {
		case FLAME: return Blocks.LANTERN;
		case SOUL: return Blocks.SOUL_LANTERN;
		default: return Blocks.LANTERN;
		
		}
	}

	
	
}
