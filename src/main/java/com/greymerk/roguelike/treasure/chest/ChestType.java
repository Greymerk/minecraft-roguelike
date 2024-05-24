package com.greymerk.roguelike.treasure.chest;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;

public enum ChestType {

	CHEST, TRAPPED_CHEST, BARREL, SHULKER;
	
	public static MetaBlock get(ChestType type) {
		switch(type) {
		case CHEST: return new MetaBlock(Blocks.CHEST);
		case TRAPPED_CHEST: return new MetaBlock(Blocks.TRAPPED_CHEST);
		case BARREL: return new MetaBlock(Blocks.BARREL);
		case SHULKER: return new MetaBlock(Blocks.SHULKER_BOX);
		default: return new MetaBlock(Blocks.CHEST);
		}
	}
}
