package com.greymerk.roguelike.editor.blocks.stair;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum StairType {

	STONE, COBBLE, MOSSY_COBBLE, STONEBRICK, MOSSY_STONEBRICK, 
	BRICK, SANDSTONE, RED_SANDSTONE, QUARTZ, NETHERBRICK,
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK, PURPUR,
	COBBLED_DEEPSLATE, TILED_DEEPSLATE, POLISHED_DEEPSLATE, DEEPSLATE_BRICK, 
	BLACKSTONE, BLACKSTONE_BRICK, POLISHED_BLACKSTONE,
	CRIMSON, WARPED;
	
	public static Block getBlock(StairType type){
		switch(type){
		case STONE: return Blocks.STONE_STAIRS;
		case COBBLE: return Blocks.COBBLESTONE_STAIRS;
		case MOSSY_COBBLE: return Blocks.MOSSY_COBBLESTONE_STAIRS;
		case STONEBRICK: return Blocks.STONE_BRICK_STAIRS;
		case MOSSY_STONEBRICK: return Blocks.MOSSY_STONE_BRICK_STAIRS;
		case BRICK: return Blocks.BRICK_STAIRS;
		case SANDSTONE: return Blocks.SANDSTONE_STAIRS;
		case RED_SANDSTONE: return Blocks.RED_SANDSTONE_STAIRS;
		case QUARTZ: return Blocks.QUARTZ_STAIRS;
		case NETHERBRICK: return Blocks.NETHER_BRICK_STAIRS;
		case OAK: return Blocks.OAK_STAIRS;
		case SPRUCE: return Blocks.SPRUCE_STAIRS;
		case BIRCH: return Blocks.BIRCH_STAIRS;
		case JUNGLE: return Blocks.JUNGLE_STAIRS;
		case ACACIA: return Blocks.ACACIA_STAIRS;
		case DARKOAK: return Blocks.DARK_OAK_STAIRS;
		case PURPUR: return Blocks.PURPUR_STAIRS;
		case COBBLED_DEEPSLATE: return Blocks.COBBLED_DEEPSLATE_STAIRS;
		case TILED_DEEPSLATE: return Blocks.DEEPSLATE_TILE_STAIRS;
		case POLISHED_DEEPSLATE: return Blocks.POLISHED_DEEPSLATE_STAIRS;
		case DEEPSLATE_BRICK: return Blocks.DEEPSLATE_BRICK_STAIRS;
		case BLACKSTONE: return Blocks.BLACKSTONE_STAIRS;
		case BLACKSTONE_BRICK: return Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS;
		case POLISHED_BLACKSTONE: return Blocks.POLISHED_BLACKSTONE_STAIRS;
		case CRIMSON: return Blocks.CRIMSON_STAIRS;
		case WARPED: return Blocks.WARPED_STAIRS;
		default: return Blocks.STONE_STAIRS;
		}
	}
}
