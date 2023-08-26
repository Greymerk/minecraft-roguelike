package com.greymerk.roguelike.editor.blocks.door;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;

public enum DoorType {

	IRON, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK,
	MANGROVE, CHERRY, BAMBOO, CRIMSON, WARPED;
	
	public static MetaBlock get(DoorType type){	
		switch(type){
		case IRON: return new MetaBlock(Blocks.IRON_DOOR);
		case BIRCH: return new MetaBlock(Blocks.BIRCH_DOOR);
		case SPRUCE: return new MetaBlock(Blocks.SPRUCE_DOOR);
		case JUNGLE: return new MetaBlock(Blocks.JUNGLE_DOOR);
		case ACACIA: return new MetaBlock(Blocks.ACACIA_DOOR);
		case DARKOAK: return new MetaBlock(Blocks.DARK_OAK_DOOR);
		case MANGROVE: return new MetaBlock(Blocks.MANGROVE_DOOR);
		case CHERRY: return new MetaBlock(Blocks.CHERRY_DOOR);
		case BAMBOO: return new MetaBlock(Blocks.BAMBOO_DOOR);
		case CRIMSON: return new MetaBlock(Blocks.CRIMSON_DOOR);
		case WARPED: return new MetaBlock(Blocks.WARPED_DOOR);
		default: return new MetaBlock(Blocks.OAK_DOOR);
		}
	}
}
