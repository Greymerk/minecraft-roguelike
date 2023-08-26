package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum BlackStone {

	GILDED, PRESSURE_PLATE, BUTTON,
	STONE, STONE_STAIR, STONE_SLAB, STONE_WALL,
	CHISELED_POLISHED, POLISHED, POLISHED_STAIR, POLISHED_SLAB, POLISHED_WALL,
	BRICK, CRACKED_BRICK, BRICK_STAIR, BRICK_SLAB, BRICK_WALL;
	
	public static MetaBlock get(BlackStone type) {
		return new MetaBlock(fromType(type));
	}
	
	public static Block fromType(BlackStone type) {
		switch(type) {
		case BRICK: return Blocks.POLISHED_BLACKSTONE_BRICKS;
		case BRICK_SLAB: return Blocks.POLISHED_BLACKSTONE_BRICK_SLAB;
		case BRICK_STAIR: return Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS;
		case BRICK_WALL: return Blocks.POLISHED_BLACKSTONE_BRICK_WALL;
		case BUTTON: return Blocks.POLISHED_BLACKSTONE_BUTTON;
		case CHISELED_POLISHED: return Blocks.CHISELED_POLISHED_BLACKSTONE;
		case CRACKED_BRICK: return Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS;
		case GILDED: return Blocks.GILDED_BLACKSTONE;
		case POLISHED: return Blocks.POLISHED_BLACKSTONE;
		case POLISHED_SLAB: return Blocks.POLISHED_BLACKSTONE_SLAB;
		case POLISHED_STAIR: return Blocks.POLISHED_BLACKSTONE_STAIRS;
		case POLISHED_WALL: return Blocks.POLISHED_BLACKSTONE_WALL;
		case PRESSURE_PLATE: return Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE;
		case STONE: return Blocks.BLACKSTONE;
		case STONE_SLAB: return Blocks.BLACKSTONE_SLAB;
		case STONE_STAIR: return Blocks.BLACKSTONE_STAIRS;
		case STONE_WALL: return Blocks.BLACKSTONE_WALL;
		default: return Blocks.BLACKSTONE;
		}
	}
}
