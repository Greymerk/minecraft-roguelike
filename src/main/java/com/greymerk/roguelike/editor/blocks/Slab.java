package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;

public enum Slab {

	STONE, STONEBRICK, COBBLE, BRICK, NETHERBRICK, QUARTZ,
	LEGACY_OAK, SANDSTONE, SANDSTONE_RED,
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;
	
	public static MetaBlock get(Slab type, boolean upsideDown){
		MetaBlock slab = new MetaBlock(fromType(type));
		slab.withProperty(SlabBlock.TYPE, (upsideDown ? SlabType.TOP : SlabType.BOTTOM));
		return slab;
	}
	
	public static Block fromType(Slab type) {
		switch(type) {
		case ACACIA: return Blocks.ACACIA_SLAB;
		case BIRCH:	return Blocks.BIRCH_SLAB;
		case BRICK: return Blocks.BRICK_SLAB;
		case COBBLE: return Blocks.COBBLESTONE_SLAB;
		case DARKOAK: return Blocks.DARK_OAK_SLAB;
		case JUNGLE: return Blocks.JUNGLE_SLAB;
		case LEGACY_OAK: return Blocks.PETRIFIED_OAK_SLAB;
		case NETHERBRICK: return Blocks.NETHER_BRICK_SLAB;
		case OAK: return Blocks.OAK_SLAB;
		case QUARTZ: return Blocks.QUARTZ_SLAB;
		case SANDSTONE: return Blocks.SANDSTONE_SLAB;
		case SANDSTONE_RED: return Blocks.RED_SANDSTONE_SLAB;
		case SPRUCE: return Blocks.SPRUCE_SLAB;
		case STONE: return Blocks.STONE_SLAB;
		case STONEBRICK: return Blocks.STONE_BRICK_SLAB;
		default: return Blocks.OAK_SLAB;
		}
	}
	
	public static MetaBlock get(Slab type){
		return get(type, false);
	}
	
}
