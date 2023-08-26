package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;

public class Log {

	public static MetaBlock get(Wood type, Cardinal dir){
		BlockState log = fromType(type).getDefaultState();
		log.with(PillarBlock.AXIS, Cardinal.axis(dir));
		return new MetaBlock(log);
	}
	
	public static MetaBlock get(Wood type){
		return get(type, Cardinal.UP);
	}
	
	public static Block fromType(Wood type) {
		switch(type) {
		case ACACIA: return Blocks.ACACIA_LOG;
		case BIRCH: return Blocks.BIRCH_LOG;
		case CHERRY: return Blocks.CHERRY_LOG;
		case CRIMSON: return Blocks.CRIMSON_STEM;
		case DARKOAK: return Blocks.DARK_OAK_LOG;
		case JUNGLE: return Blocks.JUNGLE_LOG;
		case MANGROVE: return Blocks.MANGROVE_LOG;
		case OAK: return Blocks.OAK_LOG;
		case SPRUCE: return Blocks.SPRUCE_LOG;
		case WARPED: return Blocks.WARPED_STEM;
		default: return Blocks.OAK_LOG;
		}
	}
}
