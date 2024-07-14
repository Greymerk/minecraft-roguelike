package com.greymerk.roguelike.editor;

import java.util.function.Predicate;

import com.greymerk.roguelike.editor.shapes.IShape;

import net.minecraft.util.math.random.Random;

public interface IBlockFactory {
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos);
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos, Predicate<BlockContext> p);
	
	public void fill(IWorldEditor editor, Random rand, IShape shape, Predicate<BlockContext> p);
	
	public void fill(IWorldEditor editor, Random rand, IShape shape);
	
}
