package com.greymerk.roguelike.treasure.chest;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;

public enum ChestType {

	CHEST, TRAPPED_CHEST, BARREL, SHULKER;
	
	public static MetaBlock get(ChestType type) {
		switch(type) {
		case CHEST: return MetaBlock.of(Blocks.CHEST);
		case TRAPPED_CHEST: return MetaBlock.of(Blocks.TRAPPED_CHEST);
		case BARREL: return MetaBlock.of(Blocks.BARREL);
		case SHULKER: return MetaBlock.of(Blocks.SHULKER_BOX);
		default: return MetaBlock.of(Blocks.CHEST);
		}
	}
}
