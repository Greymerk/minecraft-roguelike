package com.greymerk.roguelike.editor.blocks.door;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum DoorType {

	IRON, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK,
	MANGROVE, CHERRY, BAMBOO, CRIMSON, WARPED, COPPER;
	
	public static MetaBlock get(DoorType type){	
		return MetaBlock.of(from(type));
	}
	
	private static Block from(DoorType type) {
		switch(type){
		case IRON: return Blocks.IRON_DOOR;
		case BIRCH: return Blocks.BIRCH_DOOR;
		case SPRUCE: return Blocks.SPRUCE_DOOR;
		case JUNGLE: return Blocks.JUNGLE_DOOR;
		case ACACIA: return Blocks.ACACIA_DOOR;
		case DARKOAK: return Blocks.DARK_OAK_DOOR;
		case MANGROVE: return Blocks.MANGROVE_DOOR;
		case CHERRY: return Blocks.CHERRY_DOOR;
		case BAMBOO: return Blocks.BAMBOO_DOOR;
		case CRIMSON: return Blocks.CRIMSON_DOOR;
		case WARPED: return Blocks.WARPED_DOOR;
		case COPPER: return Blocks.COPPER_DOOR;
		default: return Blocks.OAK_DOOR;
		}
	}
}
