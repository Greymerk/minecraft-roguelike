package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum Warped {

	STEM, HYPHAE, PLANK, SLAB, STAIRS, PLATE, BUTTON,
	TRAPDOOR, DOOR, GATE, FENCE, SIGN,
	NYLIUM, FUNGUS, WART, ROOT, VINE;
	
	public static MetaBlock get(Warped type) {
		return new MetaBlock(fromType(type));
	}
	
	public static Block fromType(Warped type) {
		switch(type) {
		case BUTTON: return Blocks.WARPED_BUTTON;
		case DOOR: return Blocks.WARPED_DOOR;
		case FENCE: return Blocks.WARPED_FENCE;
		case GATE: return Blocks.WARPED_FENCE_GATE;
		case HYPHAE: return Blocks.WARPED_HYPHAE;
		case PLANK: return Blocks.WARPED_PLANKS;
		case PLATE: return Blocks.WARPED_PRESSURE_PLATE;
		case SIGN: return Blocks.WARPED_SIGN;
		case SLAB: return Blocks.WARPED_SLAB;
		case STAIRS: return Blocks.WARPED_STAIRS;
		case STEM: return Blocks.WARPED_STEM;
		case TRAPDOOR: return Blocks.WARPED_TRAPDOOR;
		case FUNGUS: return Blocks.WARPED_FUNGUS;
		case NYLIUM: return Blocks.WARPED_NYLIUM;
		case ROOT: return Blocks.WARPED_ROOTS;
		case VINE: return Blocks.TWISTING_VINES;
		case WART: return Blocks.WARPED_WART_BLOCK;
		default: return Blocks.WARPED_PLANKS;	
		}
	}
	
}
