package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;

public enum Lantern {

	SOUL, FLAME;
	
	public static void set(IWorldEditor editor, Coord origin) {
		Lantern.set(editor, origin, FLAME, true);
	}
	
	public static void set(IWorldEditor editor, Coord origin, Lantern type, boolean hang) {
		MetaBlock lantern = new MetaBlock(fromType(type).getDefaultState());
		lantern.withProperty(LanternBlock.HANGING, hang);
		lantern.set(editor, origin);
	}
	
	public static Block fromType(Lantern type) {
		switch(type) {
		case FLAME: return Blocks.LANTERN;
		case SOUL: return Blocks.SOUL_LANTERN;
		default: return Blocks.LANTERN;
		
		}
	}
	
}
