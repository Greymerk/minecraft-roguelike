package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum Anvil {

	ANVIL, CHIPPED, DAMAGED;
	
	public static void set(IWorldEditor editor, Coord pos, Anvil damage, Cardinal dir) {
		MetaBlock.of(getFromDamage(damage)).with(AnvilBlock.FACING, Cardinal.facing(dir)).set(editor, pos);
	}
	
	public static MetaBlock get(Anvil damage, Cardinal dir){
		return MetaBlock.of(getFromDamage(damage)).with(AnvilBlock.FACING, Cardinal.facing(dir));
	}
	
	private static Block getFromDamage(Anvil damage) {
		switch(damage){
		case ANVIL: return Blocks.ANVIL;
		case CHIPPED: return Blocks.CHIPPED_ANVIL;
		case DAMAGED: return Blocks.DAMAGED_ANVIL;
		default: return Blocks.ANVIL;
		}
	}
	
}
