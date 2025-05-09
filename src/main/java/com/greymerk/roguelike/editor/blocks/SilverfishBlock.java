package com.greymerk.roguelike.editor.blocks;

import java.util.List;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.factories.BlockJumble;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum SilverfishBlock {

	STONE, COBBLE, STONEBRICK, STONEBRICK_MOSSY, STONEBRICK_CRACKED, STONEBRICK_CHISELED;
	
	public static MetaBlock get(SilverfishBlock type){
		return MetaBlock.of(fromType(type));
	}
	
	public static Block fromType(SilverfishBlock type) {
		switch(type) {
		case COBBLE: return Blocks.INFESTED_COBBLESTONE;
		case STONE: return Blocks.INFESTED_STONE;
		case STONEBRICK: return Blocks.INFESTED_STONE_BRICKS;
		case STONEBRICK_CHISELED: return Blocks.INFESTED_STONE_BRICKS;
		case STONEBRICK_CRACKED: return Blocks.INFESTED_CRACKED_STONE_BRICKS;
		case STONEBRICK_MOSSY: return Blocks.INFESTED_MOSSY_STONE_BRICKS;
		default: return Blocks.INFESTED_STONE;
		}
	}
	
	public static IBlockFactory getJumble(){	
		return BlockJumble.of(List.of(
			get(COBBLE),
			get(STONEBRICK),
			get(STONEBRICK_MOSSY),
			get(STONEBRICK_CRACKED)
		));
	}
}
