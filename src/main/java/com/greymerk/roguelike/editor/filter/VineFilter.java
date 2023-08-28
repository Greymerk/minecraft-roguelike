package com.greymerk.roguelike.editor.filter;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Vine;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.editor.theme.ITheme;

public class VineFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
		for(Coord pos : box.getShape(Shape.RECTSOLID)){
			if(rand.nextInt(5) == 0) Vine.set(editor, pos);
		}
	}
}
