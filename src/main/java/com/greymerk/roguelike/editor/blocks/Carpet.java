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

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Carpet {

	public static void generate(IWorldEditor editor, RandomSource rand, Coord origin, int radius) {
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
	
	public static MetaBlock getRandom(RandomSource rand) {
		return get(Color.get(rand));
	}
	
	public static MetaBlock get(Color color) {
		return MetaBlock.of(fromColor(color));
	}
	
	private static BlockState fromColor(Color color) {
		return Blocks.CARPET.pick(Color.get(color)).defaultBlockState();
	}
}
