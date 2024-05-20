package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlazedTerracottaBlock;

public class Terracotta {
	public static MetaBlock get(Color color, Cardinal dir){
		BlockState block = ColorBlock.getGlazed(color);
		block = block.with(GlazedTerracottaBlock.FACING, Cardinal.facing(dir));
		return new MetaBlock(block);
	}
	
	
}
