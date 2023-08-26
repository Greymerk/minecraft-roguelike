package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public enum Anvil {

	ANVIL, CHIPPED, DAMAGED;
	
	public static MetaBlock get(Anvil damage, Cardinal dir){
		BlockState anvil = getFromDamage(damage);
		anvil.with(AnvilBlock.FACING, Cardinal.facing(dir));
		return new MetaBlock(anvil);
	}
	
	private static BlockState getFromDamage(Anvil damage) {
		switch(damage){
		case ANVIL: return Blocks.ANVIL.getDefaultState();
		case CHIPPED: return Blocks.CHIPPED_ANVIL.getDefaultState();
		case DAMAGED: return Blocks.DAMAGED_ANVIL.getDefaultState();
		default: return Blocks.ANVIL.getDefaultState();
		}
	}
	
}
