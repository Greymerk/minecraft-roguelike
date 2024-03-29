package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CandleBlock;
import net.minecraft.util.math.random.Random;

public class Candle {

	public static void generate(IWorldEditor editor, Coord origin, Color color, int count) {
		generate(editor, origin, color, count, true);
	}
	
	public static void generate(IWorldEditor editor, Coord origin, Color color, int count, boolean lit) {
		int numCandles;
		if(count == 0) return;
		if(count < 0) {
			numCandles = 1;
		} else if(count > 4) {
			numCandles = 4;
		} else {
			numCandles = count;
		}
		MetaBlock candle = new MetaBlock(fromColor(color));
		candle.withProperty(CandleBlock.CANDLES, numCandles);
		candle.withProperty(CandleBlock.LIT, lit);
		candle.set(editor, origin);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, boolean lit) {
		generate(editor, origin, Color.get(rand), rand.nextBetween(1, 4), lit);
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
