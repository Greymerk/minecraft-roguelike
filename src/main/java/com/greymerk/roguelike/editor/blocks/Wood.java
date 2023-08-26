package com.greymerk.roguelike.editor.blocks;


import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum Wood {
	
	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK,
	MANGROVE, CHERRY, CRIMSON, WARPED;

	public static MetaBlock get(WoodBlock block){
		return get(OAK, block);
	}
	
	public static MetaBlock get(Wood type, WoodBlock block){
		switch(block){
		case LOG: return Log.get(type);
		case PLANK: return new MetaBlock(getPlank(type));
		case FENCE: return new MetaBlock(getFence(type));
		case TRAPDOOR: return new MetaBlock(getTrapdoor(type));
		default: return Log.get(OAK);
		}
	}
	
	

	public static Block getPlank(Wood type){
		switch(type){
		case OAK: return Blocks.OAK_PLANKS;
		case SPRUCE: return Blocks.SPRUCE_PLANKS;
		case BIRCH: return Blocks.BIRCH_PLANKS;
		case JUNGLE: return Blocks.JUNGLE_PLANKS;
		case ACACIA: return Blocks.ACACIA_PLANKS;
		case DARKOAK: return Blocks.DARK_OAK_PLANKS;
		case MANGROVE: return Blocks.MANGROVE_PLANKS;
		case CHERRY: return Blocks.CHERRY_PLANKS;
		case CRIMSON: return Blocks.CRIMSON_PLANKS;
		case WARPED: return Blocks.WARPED_PLANKS;
		default: return Blocks.OAK_PLANKS;
		}
	}
	
	public static Block getFence(Wood type){
		
		switch(type){
		case OAK: return Blocks.OAK_FENCE;
		case SPRUCE: return Blocks.SPRUCE_FENCE;
		case BIRCH: return Blocks.BIRCH_FENCE;
		case JUNGLE: return Blocks.JUNGLE_FENCE;
		case ACACIA: return Blocks.ACACIA_FENCE;
		case DARKOAK: return Blocks.DARK_OAK_FENCE;
		case MANGROVE: return Blocks.MANGROVE_FENCE;
		case CHERRY: return Blocks.CHERRY_FENCE;
		case CRIMSON: return Blocks.CRIMSON_FENCE;
		case WARPED: return Blocks.WARPED_FENCE;
		default: return Blocks.OAK_FENCE;
		}
	}
	
	public static Block getSapling(Wood type){
		switch(type){
		case OAK: return Blocks.OAK_SAPLING;
		case SPRUCE: return Blocks.SPRUCE_SAPLING;
		case BIRCH: return Blocks.BIRCH_SAPLING;
		case JUNGLE: return Blocks.JUNGLE_SAPLING;
		case ACACIA: return Blocks.ACACIA_SAPLING;
		case DARKOAK: return Blocks.DARK_OAK_PLANKS;
		case MANGROVE: return Blocks.MANGROVE_PROPAGULE;
		case CHERRY: return Blocks.CHERRY_SAPLING;
		case CRIMSON: return Blocks.CRIMSON_FUNGUS;
		case WARPED: return Blocks.WARPED_FUNGUS;
		default: return Blocks.OAK_SAPLING;
		}
	}

	public static Block getLeaf(Wood type){
		switch(type){
		case OAK: return Blocks.OAK_LEAVES;
		case SPRUCE: return Blocks.SPRUCE_LEAVES;
		case BIRCH: return Blocks.BIRCH_LEAVES;
		case JUNGLE: return Blocks.JUNGLE_LEAVES;
		case ACACIA: return Blocks.ACACIA_LEAVES;
		case DARKOAK: return Blocks.DARK_OAK_PLANKS;
		case MANGROVE: return Blocks.MANGROVE_LEAVES;
		case CHERRY: return Blocks.CHERRY_LEAVES;
		case CRIMSON: return Blocks.CRIMSON_FUNGUS;
		case WARPED: return Blocks.WARPED_FUNGUS;
		default: return Blocks.OAK_LEAVES;
		}
	}

	
	public static Block getTrapdoor(Wood type) {
		switch(type){
		case OAK: return Blocks.OAK_TRAPDOOR;
		case SPRUCE: return Blocks.SPRUCE_TRAPDOOR;
		case BIRCH: return Blocks.BIRCH_TRAPDOOR;
		case JUNGLE: return Blocks.JUNGLE_TRAPDOOR;
		case ACACIA: return Blocks.ACACIA_TRAPDOOR;
		case DARKOAK: return Blocks.DARK_OAK_TRAPDOOR;
		case MANGROVE: return Blocks.MANGROVE_TRAPDOOR;
		case CHERRY: return Blocks.CHERRY_TRAPDOOR;
		case CRIMSON: return Blocks.CRIMSON_TRAPDOOR;
		case WARPED: return Blocks.WARPED_TRAPDOOR;
		default: return Blocks.OAK_TRAPDOOR;
		}
	}
	
}
