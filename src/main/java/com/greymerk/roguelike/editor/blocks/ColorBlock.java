package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public enum ColorBlock {

	CLAY, GLAZED, WOOL, CARPET, GLASS, PANE, CONCRETE, POWDER;
	
	public static MetaBlock get(ColorBlock type, Color color){
		switch(type) {
		case CLAY: return MetaBlock.of(getClay(color));
		case GLAZED: return MetaBlock.of(getGlazed(color));
		case WOOL: return MetaBlock.of(getWool(color));
		default: return MetaBlock.of(getClay(color));
		}
	}
	
	public static BlockState getWool(Color color) {
		return Blocks.WOOL.pick(Color.get(color)).defaultBlockState();
	}

	private static BlockState getClay(Color color) {
		return Blocks.DYED_TERRACOTTA.pick(Color.get(color)).defaultBlockState();
	}
	
	private static BlockState getGlazed(Color color) {
		return Blocks.GLAZED_TERRACOTTA.pick(Color.get(color)).defaultBlockState();
	}
	
}
