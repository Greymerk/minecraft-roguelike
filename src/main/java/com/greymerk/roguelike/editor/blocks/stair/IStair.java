package com.greymerk.roguelike.editor.blocks.stair;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.IShape;

import net.minecraft.util.math.random.Random;

public interface IStair {

	public IStair setOrientation(Cardinal dir, Boolean upsideDown);
	
	public IStair waterlog();
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos);
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);

	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid);
	
	public void fill(IWorldEditor editor, Random rand, IShape shape);
}
