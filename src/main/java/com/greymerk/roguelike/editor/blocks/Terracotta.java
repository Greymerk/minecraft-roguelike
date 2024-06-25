package com.greymerk.roguelike.editor.blocks;

import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockCheckers;
import com.greymerk.roguelike.util.Color;

import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.util.math.random.Random;

public class Terracotta {	
	public static MetaBlock getGlazed(Color color, Cardinal dir){
		return ColorBlock.get(ColorBlock.GLAZED, color)
				.with(GlazedTerracottaBlock.FACING, Cardinal.facing(dir));
	}
	
	public static void fillSquare(IWorldEditor editor, Random rand, Coord origin, Cardinal direction, int size, Color c) {
		List<Cardinal> dirs = List.of(direction, Cardinal.left(direction), Cardinal.reverse(direction), Cardinal.right(direction));
		Coord pos = origin.copy();
		dirs.forEach(dir -> {
			BlockCheckers checkers = Cardinal.orthogonal(dir).contains(direction) 
				? new BlockCheckers(getGlazed(c, dir), getGlazed(c, Cardinal.left(dir)))
				: new BlockCheckers(getGlazed(c, Cardinal.left(dir)), getGlazed(c, dir));
			
			for(int i = 0; i < (size / 2); ++i) {
				BoundingBox.of(pos).add(dir, i).add(Cardinal.left(dir), i).grow(dir, size - ((2 * i) + 2)).fill(editor, rand, checkers);
			}
			
			pos.add(dir, size - 1);
		});
	}
}
