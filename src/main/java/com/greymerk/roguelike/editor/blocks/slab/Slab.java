package com.greymerk.roguelike.editor.blocks.slab;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum Slab {

	STONE, SMOOTH_STONE,
	STONEBRICK, MOSSY_STONEBRICK,
	COBBLE, MOSSY_COBBLE,
	OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARK_OAK,
	ANDESITE, GRANITE, DIORITE,
	NETHER_BRICK, RED_NETHER_BRICK,
	WARPED, CRIMSON,
	BLACK_STONE, BLACKSTONE_BRICK,
	COBBLED_SLATE, TILED_SLATE, POLISHED_SLATE, SLATE_BRICK;
	
	public static Block fromType(Slab type) {
		switch(type) {
		case SMOOTH_STONE: return Blocks.SMOOTH_STONE_SLAB;
		case STONE: return Blocks.STONE_SLAB;
		case STONEBRICK: return Blocks.STONE_BRICK_SLAB;
		case MOSSY_STONEBRICK: return Blocks.MOSSY_STONE_BRICK_SLAB;
		case COBBLE: return Blocks.COBBLESTONE_SLAB;
		case MOSSY_COBBLE: return Blocks.MOSSY_COBBLESTONE_SLAB;
		case OAK: return Blocks.OAK_SLAB;
		case BIRCH: return Blocks.BIRCH_SLAB;
		case SPRUCE: return Blocks.SPRUCE_SLAB;
		case JUNGLE: return Blocks.JUNGLE_SLAB;
		case ACACIA: return Blocks.ACACIA_SLAB;
		case DARK_OAK: return Blocks.DARK_OAK_SLAB;
		case ANDESITE: return Blocks.ANDESITE_SLAB;
		case DIORITE: return Blocks.DIORITE_SLAB;
		case GRANITE: return Blocks.GRANITE_SLAB;
		case NETHER_BRICK: return Blocks.NETHER_BRICK_SLAB;
		case RED_NETHER_BRICK: return Blocks.RED_NETHER_BRICK_SLAB;
		case WARPED: return Blocks.WARPED_SLAB;
		case CRIMSON: return Blocks.CRIMSON_SLAB;
		case BLACK_STONE: return Blocks.POLISHED_BLACKSTONE_SLAB;
		case BLACKSTONE_BRICK: return Blocks.POLISHED_BLACKSTONE_BRICK_SLAB;
		case COBBLED_SLATE: return Blocks.COBBLED_DEEPSLATE_SLAB;
		case TILED_SLATE: return Blocks.DEEPSLATE_TILE_SLAB;
		case POLISHED_SLATE: return Blocks.POLISHED_DEEPSLATE_SLAB;
		case SLATE_BRICK: return Blocks.DEEPSLATE_BRICK_SLAB;
		default: return Blocks.STONE_SLAB;
		}
	}
	
	public static ISlab get(Slab type, boolean upsideDown) {
		MetaSlab slab = new MetaSlab(fromType(type));
		slab.upsideDown(upsideDown);
		return slab;
	}
	
	public static ISlab get(Slab type) {
		return get(type, false);
	}
}
