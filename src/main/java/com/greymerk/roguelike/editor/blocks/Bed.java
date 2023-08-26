package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.BedPart;

public class Bed {

	public static void generate(IWorldEditor editor, Cardinal dir, Coord origin, Color color){
		Coord pos = new Coord(origin);
		BlockState head = getFromColor(color);
		head = head.with(BedBlock.PART, BedPart.HEAD);
		head = head.with(HorizontalFacingBlock.FACING, Cardinal.facing(dir));
		new MetaBlock(head).set(editor, pos);
		
		pos.add(dir);
		BlockState foot = getFromColor(color);
		foot = foot.with(BedBlock.PART, BedPart.FOOT);
		foot = foot.with(HorizontalFacingBlock.FACING, Cardinal.facing(dir));
		new MetaBlock(foot).set(editor, pos);

	}
	
	public static BlockState getFromColor(Color c) {
		switch(c) {
		case WHITE: return Blocks.WHITE_BED.getDefaultState();
		case ORANGE: return Blocks.ORANGE_BED.getDefaultState();
		case MAGENTA: return Blocks.MAGENTA_BED.getDefaultState();
		case LIGHT_BLUE: return Blocks.LIGHT_BLUE_BED.getDefaultState();
		case YELLOW: return Blocks.YELLOW_BED.getDefaultState();
		case LIME: return Blocks.LIME_BED.getDefaultState();
		case PINK: return Blocks.PINK_BED.getDefaultState();
		case GRAY: return Blocks.GRAY_BED.getDefaultState();
		case LIGHT_GRAY: return Blocks.LIGHT_GRAY_BED.getDefaultState();
		case CYAN: return Blocks.CYAN_BED.getDefaultState();
		case PURPLE: return Blocks.PURPLE_BED.getDefaultState();
		case BLUE: return Blocks.BLUE_BED.getDefaultState();
		case BROWN: return Blocks.BROWN_BED.getDefaultState();
		case GREEN: return Blocks.GREEN_BED.getDefaultState();
		case RED: return Blocks.RED_BED.getDefaultState();
		case BLACK: return Blocks.BLACK_BED.getDefaultState();
		default: return Blocks.RED_BED.getDefaultState();
		}
	}
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, dir, pos, Color.RED);
	}
}
