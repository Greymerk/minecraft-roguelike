package com.greymerk.roguelike.editor;

import com.greymerk.roguelike.editor.shapes.IShape;

import net.minecraft.util.math.random.Random;

public abstract class BlockBase implements IBlockFactory {
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		return set(editor, rand, pos, true, true);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape){
		shape.fill(editor, rand, this, true, true);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid){
		shape.fill(editor, rand, this, fillAir, replaceSolid);
	}
}
