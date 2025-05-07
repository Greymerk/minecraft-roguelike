package com.greymerk.roguelike.editor.blocks.door;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;

public enum DoorType {

	IRON, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK,
	MANGROVE, CHERRY, BAMBOO, CRIMSON, WARPED;
	
	public static MetaBlock get(DoorType type){	
		switch(type){
		case IRON: return MetaBlock.of(Blocks.IRON_DOOR);
		case BIRCH: return MetaBlock.of(Blocks.BIRCH_DOOR);
		case SPRUCE: return MetaBlock.of(Blocks.SPRUCE_DOOR);
		case JUNGLE: return MetaBlock.of(Blocks.JUNGLE_DOOR);
		case ACACIA: return MetaBlock.of(Blocks.ACACIA_DOOR);
		case DARKOAK: return MetaBlock.of(Blocks.DARK_OAK_DOOR);
		case MANGROVE: return MetaBlock.of(Blocks.MANGROVE_DOOR);
		case CHERRY: return MetaBlock.of(Blocks.CHERRY_DOOR);
		case BAMBOO: return MetaBlock.of(Blocks.BAMBOO_DOOR);
		case CRIMSON: return MetaBlock.of(Blocks.CRIMSON_DOOR);
		case WARPED: return MetaBlock.of(Blocks.WARPED_DOOR);
		default: return MetaBlock.of(Blocks.OAK_DOOR);
		}
	}
}
