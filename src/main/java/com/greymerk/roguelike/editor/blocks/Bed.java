package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BedPart;

public class Bed {

	public static void generate(IWorldEditor editor, RandomSource rand, Cardinal dir, Coord pos) {
		generate(editor, dir, pos, Color.get(rand));
	}
	
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, dir, pos, Color.RED);
	}
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord origin, Color color){
		MetaBlock.of(getFromColor(color))
			.with(BedBlock.PART, BedPart.HEAD)
			.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir))
			.set(editor, origin);
		
		MetaBlock.of(getFromColor(color))
			.with(BedBlock.PART, BedPart.FOOT)
			.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir))
			.set(editor, origin.copy().add(dir));
	}
	
	private static Block getFromColor(Color c) {
		switch(c) {
		case WHITE: return Blocks.WHITE_BED;
		case ORANGE: return Blocks.ORANGE_BED;
		case MAGENTA: return Blocks.MAGENTA_BED;
		case LIGHT_BLUE: return Blocks.LIGHT_BLUE_BED;
		case YELLOW: return Blocks.YELLOW_BED;
		case LIME: return Blocks.LIME_BED;
		case PINK: return Blocks.PINK_BED;
		case GRAY: return Blocks.GRAY_BED;
		case LIGHT_GRAY: return Blocks.LIGHT_GRAY_BED;
		case CYAN: return Blocks.CYAN_BED;
		case PURPLE: return Blocks.PURPLE_BED;
		case BLUE: return Blocks.BLUE_BED;
		case BROWN: return Blocks.BROWN_BED;
		case GREEN: return Blocks.GREEN_BED;
		case RED: return Blocks.RED_BED;
		case BLACK: return Blocks.BLACK_BED;
		default: return Blocks.RED_BED;
		}
	}
	
	
}
