package com.greymerk.roguelike.editor.blocks;


import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;
import com.greymerk.roguelike.util.math.MathHelper;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CandleBlock;
import net.minecraft.util.math.random.Random;

public class Candle {

	public static void generate(IWorldEditor editor, Random rand, Coord origin) {
		generate(editor, rand, origin, Color.get(rand));
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, Color color) {
		generate(editor, origin, color, rand.nextBetween(1, 4), true);
	}
	
	public static void generate(IWorldEditor editor, Coord origin, Color color, int count, boolean lit) {
		Candle.get(color, count, lit).set(editor, origin, Fill.SUPPORTED);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, Predicate<BlockContext> p) {
		generate(editor, rand, origin, Color.get(rand), p);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, Color color, Predicate<BlockContext> p) {
		generate(editor, origin, color, rand.nextBetween(1, 4), true, p);
	}
	
	public static void generate(IWorldEditor editor, Coord origin, Color color, int count, boolean lit, Predicate<BlockContext> p) {
		Candle.get(color, count, lit).set(editor, origin, Fill.SUPPORTED.and(p));
	}
	
	public static MetaBlock get(Color color, int count, boolean lit) {
		return MetaBlock.of(fromColor(color))
				.with(CandleBlock.CANDLES, MathHelper.clamp(count, 1, 4))
				.with(CandleBlock.LIT, lit);
	}
	
	public static Block fromColor(Color color) {
		switch(color) {
		case BLACK: return Blocks.BLACK_CANDLE;
		case BLUE: return Blocks.BLUE_CANDLE;
		case BROWN: return Blocks.BROWN_CANDLE;
		case CYAN: return Blocks.CYAN_CANDLE;
		case GRAY: return Blocks.GRAY_CANDLE;
		case GREEN: return Blocks.GREEN_CANDLE;
		case LIGHT_BLUE: return Blocks.LIGHT_BLUE_CANDLE;
		case LIGHT_GRAY: return Blocks.LIGHT_GRAY_CANDLE;
		case LIME: return Blocks.LIME_CANDLE;
		case MAGENTA: return Blocks.MAGENTA_CANDLE;
		case ORANGE: return Blocks.ORANGE_CANDLE;
		case PINK: return Blocks.PINK_CANDLE;
		case PURPLE: return Blocks.PURPLE_CANDLE;
		case RED: return Blocks.RED_CANDLE;
		case WHITE: return Blocks.WHITE_CANDLE;
		case YELLOW: return Blocks.YELLOW_CANDLE;
		default: return Blocks.CANDLE;
		
		}
	}
}
