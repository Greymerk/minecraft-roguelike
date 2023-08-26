package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;

public enum Trapdoor {

	WOODEN, IRON;
	
	public static MetaBlock getWooden(Wood type, Cardinal dir, boolean bottom, boolean open){
		MetaBlock hatch = Wood.get(type, WoodBlock.TRAPDOOR);
		return Trapdoor.setOrientation(hatch, dir, bottom, open);
	}
	
	public static MetaBlock getIron(Cardinal dir, boolean bottom, boolean open) {
		MetaBlock hatch = new MetaBlock(Blocks.IRON_TRAPDOOR.getDefaultState());
		return Trapdoor.setOrientation(hatch, dir, bottom, open);
	}
	
	public static MetaBlock setOrientation(MetaBlock slab, Cardinal dir, boolean bottom, boolean open){
	
		
		return slab;
	}
}
