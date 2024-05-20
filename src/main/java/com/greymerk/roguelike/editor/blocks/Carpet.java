package com.greymerk.roguelike.editor.blocks;

import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockCheckers;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.util.Color;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class Carpet {

	public static void generate(IWorldEditor editor, Random rand, Coord origin, int radius) {
		List<Color> colors = Arrays.asList(Color.values());
		RandHelper.shuffle(colors, rand);
		
		BoundingBox bb = BoundingBox.of(origin).grow(Cardinal.directions, radius);
		RectSolid.fill(editor, rand, bb, get(colors.get(0)));
		
		if(radius < 3) return;
		
		BlockCheckers checkers = new BlockCheckers(get(colors.get(1)), get(colors.get(2)));
		bb = BoundingBox.of(origin).grow(Cardinal.directions, radius - 1);
		RectSolid.fill(editor, rand, bb, checkers);
		
		bb = BoundingBox.of(origin).grow(Cardinal.directions);
		RectSolid.fill(editor, rand, bb, get(colors.get(3)));
		
	}
	
	public static MetaBlock getRandom(Random rand) {
		return get(Color.get(rand));
	}
	
	public static MetaBlock get(Color color) {
		return MetaBlock.of(fromColor(color));
	}
	
	private static Block fromColor(Color color) {
		switch(color) {
		case BLACK: return Blocks.BLACK_CARPET;
		case BLUE: return Blocks.BLUE_CARPET;
		case BROWN: return Blocks.BROWN_CARPET;
		case CYAN: return Blocks.CYAN_CARPET;
		case GRAY: return Blocks.GRAY_CARPET;
		case GREEN: return Blocks.GREEN_CARPET;
		case LIGHT_BLUE: return Blocks.LIGHT_BLUE_CARPET;
		case LIGHT_GRAY: return Blocks.LIGHT_GRAY_CARPET;
		case LIME: return Blocks.LIME_CARPET;
		case MAGENTA: return Blocks.MAGENTA_CARPET;
		case ORANGE: return Blocks.ORANGE_CARPET;
		case PINK: return Blocks.PINK_CARPET;
		case PURPLE: return Blocks.PURPLE_CARPET;
		case RED: return Blocks.RED_CARPET;
		case WHITE: return Blocks.WHITE_CARPET;
		case YELLOW: return Blocks.YELLOW_CARPET;
		default: return Blocks.BLACK_CARPET;
		}
	}
}
