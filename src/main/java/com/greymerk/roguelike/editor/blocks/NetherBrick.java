package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum NetherBrick {

	BRICK, CRACKED, CHISELED, STAIR, RED_BRICK, RED_STAIR,
	SLAB, RED_SLAB;
	
	public static Block fromType(NetherBrick type) {
		
		switch(type) {
		case BRICK: return Blocks.NETHER_BRICKS;
		case CHISELED: return Blocks.CHISELED_NETHER_BRICKS;
		case CRACKED: return Blocks.CRACKED_NETHER_BRICKS;
		case RED_BRICK: return Blocks.RED_NETHER_BRICKS;
		case RED_STAIR: return Blocks.RED_NETHER_BRICK_STAIRS;
		case STAIR: return Blocks.NETHER_BRICK_STAIRS;
		case SLAB: return Blocks.NETHER_BRICK_SLAB;
		case RED_SLAB: return Blocks.RED_NETHER_BRICK_SLAB;
		default: return Blocks.NETHER_BRICKS;
		}
	}
	
	public MetaBlock get(NetherBrick type) {
		return new MetaBlock(fromType(type));
	}
}
