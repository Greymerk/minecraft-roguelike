package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.DecoratedPot;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class DecoratedPotFilter implements IFilter {

	@Override
	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box) {
		box.getShape(Shape.RECTSOLID).forEach(c -> {
			if(rand.nextInt(500) == 0) this.set(editor, rand, settings.getDifficulty(), c);
		});
	}
	
	private void set(IWorldEditor editor, Random rand, Difficulty diff, Coord origin) {
		if(!editor.isAir(origin)) return;
		if(!editor.isFaceFullSquare(origin.copy().add(Cardinal.DOWN), Cardinal.UP)) return;
		DecoratedPot.set(editor, rand, origin);
	}

}
