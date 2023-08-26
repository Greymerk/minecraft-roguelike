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
		case WHEAT: return new MetaBlock(Blocks.WHEAT);
		case CARROTS: return new MetaBlock(Blocks.CARROTS);
		case NETHERWART: return new MetaBlock(Blocks.NETHER_WART);
		case MELON: return new MetaBlock(Blocks.MELON_STEM);
		case PUMPKIN: return new MetaBlock(Blocks.PUMPKIN_STEM);
		case POTATOES: return new MetaBlock(Blocks.POTATOES);
		default: return new MetaBlock(Blocks.WHEAT);
		}
	}
	
	public static MetaBlock getCocao(Cardinal dir){
		MetaBlock cocao = new MetaBlock(Blocks.COCOA);
		cocao.withProperty(CocoaBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		cocao.withProperty(CocoaBlock.AGE, 2);
		return cocao;
	}
	
	public static MetaBlock getPumpkin(Cardinal dir, boolean lit){
		MetaBlock pumpkin = new MetaBlock(lit 
				? Blocks.JACK_O_LANTERN.getDefaultState() 
				: Blocks.CARVED_PUMPKIN.getDefaultState()); 
		pumpkin.withProperty(CarvedPumpkinBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		return pumpkin;
	}
}
