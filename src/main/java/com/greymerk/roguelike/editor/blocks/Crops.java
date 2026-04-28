package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.CocoaBlock;

public enum Crops {

	WHEAT, CARROTS, NETHERWART, MELON, PUMPKIN, POTATOES;
	
	public static MetaBlock get(Crops type){
		switch(type){
		case WHEAT: return MetaBlock.of(Blocks.WHEAT);
		case CARROTS: return MetaBlock.of(Blocks.CARROTS);
		case NETHERWART: return MetaBlock.of(Blocks.NETHER_WART);
		case MELON: return MetaBlock.of(Blocks.MELON_STEM);
		case PUMPKIN: return MetaBlock.of(Blocks.PUMPKIN_STEM);
		case POTATOES: return MetaBlock.of(Blocks.POTATOES);
		default: return MetaBlock.of(Blocks.WHEAT);
		}
	}
	
	public static MetaBlock getCocao(Cardinal dir){
		return MetaBlock.of(Blocks.COCOA)
			.with(CocoaBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)))
			.with(CocoaBlock.AGE, 2);
	}
	
	public static MetaBlock getPumpkin(Cardinal dir, boolean lit){
		return MetaBlock.of(lit 
				? Blocks.JACK_O_LANTERN.defaultBlockState() 
				: Blocks.CARVED_PUMPKIN.defaultBlockState()) 
			.with(CarvedPumpkinBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
	}
}
