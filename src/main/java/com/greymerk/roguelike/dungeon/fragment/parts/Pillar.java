package com.greymerk.roguelike.dungeon.fragment.parts;

import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.theme.IBlockSet;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class Pillar {

	public static void generate(IWorldEditor editor, Random rand, Coord origin, ITheme theme, int height) {
		generate(editor, rand, origin, theme, height, Cardinal.directions);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, ITheme theme, int height, List<Cardinal> directions) {
		generate(editor, rand, origin, theme.getPrimary(), height, directions);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks, int height, List<Cardinal> directions) {
		if(height > 1) BoundingBox.of(origin).grow(Cardinal.UP, height - 1).fill(editor, rand, blocks.getPillar());
		blocks.getWall().set(editor, rand, origin.copy().add(Cardinal.UP, height));
		directions.forEach(dir -> {
			blocks.getStair().setOrientation(dir, true).set(editor, rand, origin.copy().add(dir).add(Cardinal.UP, height), Fill.ONLY_AIR);
		});
	}

}
