package com.greymerk.roguelike.editor;

import java.util.function.Predicate;
import net.minecraft.util.RandomSource;
import com.greymerk.roguelike.editor.shapes.IShape;

public interface IBlockFactory {
	
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos);
	
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos, Predicate<BlockContext> p);
	
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape, Predicate<BlockContext> p);
	
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape);
	
}
