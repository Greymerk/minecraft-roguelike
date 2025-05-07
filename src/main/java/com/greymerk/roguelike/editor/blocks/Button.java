package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;

public enum Button {

	STONE, OAK;
	
	public static Block fromType(Button type) {
		switch(type) {
		case OAK: return Blocks.OAK_BUTTON;
		case STONE: return Blocks.STONE_BUTTON;
		default: return Blocks.STONE_BUTTON;
		
		}
	}
	
	public static void generate(IWorldEditor editor, Coord origin, Cardinal dir, Button type) {
		MetaBlock button = MetaBlock.of(fromType(type));
		button.with(ButtonBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		button.set(editor, origin);
	}
	
}
