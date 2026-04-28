package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.Half;

public enum Trapdoor {

	WOODEN, IRON;
	
	public static MetaBlock getWooden(Wood type, Cardinal dir, boolean bottom, boolean open){
		MetaBlock hatch = Wood.get(type, WoodBlock.TRAPDOOR);
		return Trapdoor.setOrientation(hatch, dir, bottom, open);
	}
	
	public static MetaBlock getIron(Cardinal dir, boolean bottom, boolean open) {
		MetaBlock hatch = MetaBlock.of(Blocks.IRON_TRAPDOOR);
		return Trapdoor.setOrientation(hatch, dir, bottom, open);
	}
	
	public static MetaBlock setOrientation(MetaBlock slab, Cardinal dir, boolean bottom, boolean open){
		slab.with(TrapDoorBlock.FACING, Cardinal.facing(dir));
		slab.with(TrapDoorBlock.HALF, bottom ? Half.BOTTOM : Half.TOP);
		slab.with(TrapDoorBlock.OPEN, open);
		return slab;
	}
}
