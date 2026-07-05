package com.greymerk.roguelike.editor.blocks;


import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Candle {

	public static void generate(IWorldEditor editor, RandomSource rand, Coord origin) {
		generate(editor, rand, origin, Color.get(rand));
	}
	
	public static void generate(IWorldEditor editor, RandomSource rand, Coord origin, Color color) {
		generate(editor, origin, color, rand.nextIntBetweenInclusive(1, 4), true);
	}
	
	public static void generate(IWorldEditor editor, Coord origin, Color color, int count, boolean lit) {
		Candle.get(color, count, lit).set(editor, origin, Fill.SUPPORTED);
	}
	
	public static void generate(IWorldEditor editor, RandomSource rand, Coord origin, Predicate<BlockContext> p) {
		generate(editor, rand, origin, Color.get(rand), p);
	}
	
	public static void generate(IWorldEditor editor, RandomSource rand, Coord origin, Color color, Predicate<BlockContext> p) {
		generate(editor, origin, color, rand.nextIntBetweenInclusive(1, 4), true, p);
	}
	
	public static void generate(IWorldEditor editor, Coord origin, Color color, int count, boolean lit, Predicate<BlockContext> p) {
		Candle.get(color, count, lit).set(editor, origin, Fill.SUPPORTED.and(p));
	}
	
	public static MetaBlock get(Color color, int count, boolean lit) {
		return MetaBlock.of(fromColor(color))
				.with(CandleBlock.CANDLES, Math.clamp(count, 1, 4))
				.with(CandleBlock.LIT, lit);
	}
	
	public static BlockState fromColor(Color color) {
		return Blocks.DYED_CANDLE.pick(Color.get(color)).defaultBlockState();
	}
}
