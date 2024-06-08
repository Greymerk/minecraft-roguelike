package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

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
	
	public static Block getWool(Color color) {
		switch(color){
		case WHITE: return Blocks.WHITE_WOOL;
		case ORANGE: return Blocks.ORANGE_WOOL;
		case MAGENTA: return Blocks.MAGENTA_WOOL;
		case LIGHT_BLUE: return Blocks.LIGHT_BLUE_WOOL;
		case YELLOW: return Blocks.YELLOW_WOOL;
		case LIME: return Blocks.LIME_WOOL;
		case PINK: return Blocks.PINK_WOOL;
		case GRAY: return Blocks.GRAY_WOOL;
		case LIGHT_GRAY: return Blocks.LIGHT_GRAY_WOOL;
		case CYAN: return Blocks.CYAN_WOOL;
		case PURPLE: return Blocks.PURPLE_WOOL;
		case BLUE: return Blocks.BLUE_WOOL;
		case BROWN: return Blocks.BROWN_BED;
		case GREEN: return Blocks.GREEN_WOOL;
		case RED: return Blocks.RED_WOOL;
		case BLACK: return Blocks.BLACK_WOOL;
		default: return Blocks.WHITE_WOOL;
		}
	}

	private static BlockState getClay(Color color) {
		switch(color){
		case WHITE: return Blocks.WHITE_TERRACOTTA.getDefaultState();
		case ORANGE: return Blocks.ORANGE_TERRACOTTA.getDefaultState();
		case MAGENTA: return Blocks.MAGENTA_TERRACOTTA.getDefaultState();
		case LIGHT_BLUE: return Blocks.LIGHT_BLUE_TERRACOTTA.getDefaultState();
		case YELLOW: return Blocks.YELLOW_TERRACOTTA.getDefaultState();
		case LIME: return Blocks.LIME_TERRACOTTA.getDefaultState();
		case PINK: return Blocks.PINK_TERRACOTTA.getDefaultState();
		case GRAY: return Blocks.GRAY_TERRACOTTA.getDefaultState();
		case LIGHT_GRAY: return Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
		case CYAN: return Blocks.CYAN_TERRACOTTA.getDefaultState();
		case PURPLE: return Blocks.PURPLE_TERRACOTTA.getDefaultState();
		case BLUE: return Blocks.BLUE_TERRACOTTA.getDefaultState();
		case BROWN: return Blocks.BROWN_BED.getDefaultState();
		case GREEN: return Blocks.GREEN_TERRACOTTA.getDefaultState();
		case RED: return Blocks.RED_TERRACOTTA.getDefaultState();
		case BLACK: return Blocks.BLACK_TERRACOTTA.getDefaultState();
		default: return Blocks.TERRACOTTA.getDefaultState();
		}
	}
	
	private static BlockState getGlazed(Color color) {
		switch(color){
		case WHITE: return Blocks.WHITE_GLAZED_TERRACOTTA.getDefaultState();
		case ORANGE: return Blocks.ORANGE_GLAZED_TERRACOTTA.getDefaultState();
		case MAGENTA: return Blocks.MAGENTA_GLAZED_TERRACOTTA.getDefaultState();
		case LIGHT_BLUE: return Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.getDefaultState();
		case YELLOW: return Blocks.YELLOW_GLAZED_TERRACOTTA.getDefaultState();
		case LIME: return Blocks.LIME_GLAZED_TERRACOTTA.getDefaultState();
		case PINK: return Blocks.PINK_GLAZED_TERRACOTTA.getDefaultState();
		case GRAY: return Blocks.GRAY_GLAZED_TERRACOTTA.getDefaultState();
		case LIGHT_GRAY: return Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.getDefaultState();
		case CYAN: return Blocks.CYAN_GLAZED_TERRACOTTA.getDefaultState();
		case PURPLE: return Blocks.PURPLE_GLAZED_TERRACOTTA.getDefaultState();
		case BLUE: return Blocks.BLUE_GLAZED_TERRACOTTA.getDefaultState();
		case BROWN: return Blocks.BROWN_BED.getDefaultState();
		case GREEN: return Blocks.GREEN_GLAZED_TERRACOTTA.getDefaultState();
		case RED: return Blocks.RED_GLAZED_TERRACOTTA.getDefaultState();
		case BLACK: return Blocks.BLACK_GLAZED_TERRACOTTA.getDefaultState();
		default: return Blocks.WHITE_GLAZED_TERRACOTTA.getDefaultState();
		}
	}
	
}
