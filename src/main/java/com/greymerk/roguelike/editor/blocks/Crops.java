package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.CocoaBlock;

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
		MetaBlock cocao = MetaBlock.of(Blocks.COCOA);
		cocao.with(CocoaBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		cocao.with(CocoaBlock.AGE, 2);
		return cocao;
	}
	
	public static MetaBlock getPumpkin(Cardinal dir, boolean lit){
		MetaBlock pumpkin = MetaBlock.of(lit 
				? Blocks.JACK_O_LANTERN.getDefaultState() 
				: Blocks.CARVED_PUMPKIN.getDefaultState()); 
		pumpkin.with(CarvedPumpkinBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		return pumpkin;
	}
}
