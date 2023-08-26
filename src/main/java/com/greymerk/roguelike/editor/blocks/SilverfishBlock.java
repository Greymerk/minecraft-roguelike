package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.factories.BlockJumble;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum SilverfishBlock {

	STONE, COBBLE, STONEBRICK, STONEBRICK_MOSSY, STONEBRICK_CRACKED, STONEBRICK_CHISELED;
	
	public static MetaBlock get(SilverfishBlock type){
		return new MetaBlock(fromType(type));
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
		
		BlockJumble jumble = new BlockJumble();
		
		SilverfishBlock[] types = new SilverfishBlock[]{
				COBBLE,
				STONEBRICK,
				STONEBRICK_MOSSY,
				STONEBRICK_CRACKED
		};
		
		for(SilverfishBlock type : types){
			jumble.addBlock(get(type));	
		}		
		
		return jumble;
		
	}
	
}
