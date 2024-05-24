package com.greymerk.roguelike.filter;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.theme.ITheme;

public class EncaseFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
		box.getShape(Shape.RECTSOLID).fill(editor, rand, theme.getPrimary().getWall());
	}
}
