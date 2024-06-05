package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.random.Random;

public enum TallPlant {

	SUNFLOWER, LILAC, TALLGRASS, FERN, ROSE, PEONY;
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin) {
		TallPlant type = TallPlant.values()[rand.nextInt(TallPlant.values().length)];
		generate(editor, type, origin);
	}
	
	public static void generate(IWorldEditor editor, TallPlant type, Coord origin){
		MetaBlock.of(TallPlant.fromType(type)).withProperty(TallPlantBlock.HALF, DoubleBlockHalf.LOWER).set(editor, origin.copy());
		MetaBlock.of(TallPlant.fromType(type)).withProperty(TallPlantBlock.HALF, DoubleBlockHalf.UPPER).set(editor, origin.copy().add(Cardinal.UP));
	}
	
	public static Block fromType(TallPlant type){
		switch(type){
		case SUNFLOWER: return Blocks.SUNFLOWER;
		case LILAC: return Blocks.LILAC;
		case TALLGRASS: return Blocks.TALL_GRASS;
		case FERN: return Blocks.LARGE_FERN;
		case ROSE: return Blocks.ROSE_BUSH;
		case PEONY: return Blocks.PEONY;
		default: return Blocks.SUNFLOWER;
		}	
	}
}
