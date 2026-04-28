package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;

public enum Quartz {

	SMOOTH, CHISELED, PILLAR;
	
	public static MetaBlock get(Quartz type){
		return MetaBlock.of(fromType(type));
	}

	public static MetaBlock getPillar(Cardinal dir){
		MetaBlock block = MetaBlock.of(Blocks.QUARTZ_PILLAR);
		block.with(RotatedPillarBlock.AXIS, Cardinal.axis(dir));
		return block;
	}
	
	public static Block fromType(Quartz type) {
		switch(type) {
		case CHISELED: return Blocks.CHISELED_QUARTZ_BLOCK;
		case PILLAR: return Blocks.QUARTZ_PILLAR;
		case SMOOTH: return Blocks.SMOOTH_QUARTZ;
		default: return Blocks.SMOOTH_QUARTZ;
		}
	}
	
	
}
