package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum Deepslate {

	SLATE, CHISELED, REINFORCED, INFESTED,
	COBBLED, COBBLED_STAIR, COBBLED_SLAB, COBBLED_WALL, 
	POLISHED, POLISHED_STAIR, POLISHED_SLAB, POLISHED_WALL,
	TILE, CRACKED_TILE, TILE_STAIR, TILE_SLAB, TILE_WALL,
	BRICK, CRACKED_BRICK, BRICK_STAIR, BRICK_SLAB, BRICK_WALL,
	COAL, IRON, COPPER, GOLD, REDSTONE, EMERALD, LAPIS, DIAMOND;
	
	public static MetaBlock get(Deepslate type) {
		return new MetaBlock(fromType(type));
	}
	
	public static Block fromType(Deepslate type) {
		switch(type) {
		case BRICK: return Blocks.DEEPSLATE_BRICKS;
		case BRICK_SLAB: return Blocks.DEEPSLATE_BRICK_SLAB;
		case BRICK_STAIR: return Blocks.BRICK_STAIRS;
		case BRICK_WALL: return Blocks.DEEPSLATE_BRICK_WALL;
		case CHISELED: return Blocks.CHISELED_DEEPSLATE;
		case COAL: return Blocks.DEEPSLATE_COAL_ORE;
		case COBBLED: return Blocks.COBBLED_DEEPSLATE;
		case COBBLED_SLAB: return Blocks.COBBLED_DEEPSLATE_SLAB;
		case COBBLED_STAIR: return Blocks.COBBLED_DEEPSLATE_STAIRS;
		case COBBLED_WALL: return Blocks.COBBLED_DEEPSLATE_WALL;
		case COPPER: return Blocks.DEEPSLATE_COPPER_ORE;
		case CRACKED_BRICK: return Blocks.CRACKED_DEEPSLATE_BRICKS;
		case CRACKED_TILE: return Blocks.CRACKED_DEEPSLATE_TILES;
		case DIAMOND: return Blocks.DEEPSLATE_DIAMOND_ORE;
		case EMERALD: return Blocks.DEEPSLATE_EMERALD_ORE;
		case GOLD: return Blocks.DEEPSLATE_GOLD_ORE;
		case INFESTED: return Blocks.INFESTED_DEEPSLATE;
		case IRON: return Blocks.DEEPSLATE_IRON_ORE;
		case LAPIS: return Blocks.DEEPSLATE_LAPIS_ORE;
		case POLISHED: return Blocks.POLISHED_DEEPSLATE;
		case POLISHED_SLAB: return Blocks.POLISHED_DEEPSLATE_SLAB;
		case POLISHED_STAIR: return Blocks.POLISHED_DEEPSLATE_STAIRS;
		case POLISHED_WALL: return Blocks.POLISHED_DEEPSLATE_WALL;
		case REDSTONE: return Blocks.DEEPSLATE_REDSTONE_ORE;
		case REINFORCED: return Blocks.REINFORCED_DEEPSLATE;
		case SLATE: return Blocks.DEEPSLATE;
		case TILE: return Blocks.DEEPSLATE_TILES;
		case TILE_SLAB: return Blocks.DEEPSLATE_TILE_SLAB;
		case TILE_STAIR: return Blocks.DEEPSLATE_TILE_STAIRS;
		case TILE_WALL: return Blocks.DEEPSLATE_TILE_WALL;
		default: return Blocks.DEEPSLATE;
		}
	}
	
}
