package com.greymerk.roguelike.dungeon.fragment.parts;

import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class Pillar {

	public static void generate(IWorldEditor editor, Random rand, Coord origin, ITheme theme, int height) {
		BoundingBox.of(origin).grow(Cardinal.UP, height).fill(editor, rand, theme.getPrimary().getPillar());
		Cardinal.directions.forEach(dir -> {
			theme.getPrimary().getStair().setOrientation(dir, true).set(editor, rand, origin.copy().add(dir).add(Cardinal.UP, height), true, false);
		});
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, ITheme theme, int height, List<Cardinal> directions) {
		BoundingBox.of(origin).grow(Cardinal.UP, height).fill(editor, rand, theme.getPrimary().getPillar());
		directions.forEach(dir -> {
			theme.getPrimary().getStair().setOrientation(dir, true).set(editor, rand, origin.copy().add(dir).add(Cardinal.UP, height));
		});
	}

}
