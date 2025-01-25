package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class EncaseFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box) {
		box.getShape(Shape.RECTSOLID).fill(editor, rand, settings.getTheme().getPrimary().getWall());
	}
}
