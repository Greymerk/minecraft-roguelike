package com.greymerk.roguelike.editor;

import java.util.function.Predicate;
import net.minecraft.util.RandomSource;
import com.greymerk.roguelike.editor.shapes.IShape;

public abstract class BlockBase implements IBlockFactory {
	
	@Override
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos) {
		return set(editor, rand, pos, Fill.ALWAYS);
	}
	
	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape, Predicate<BlockContext> p) {
		shape.fill(editor, rand, this, p);
	}
	
	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape){
		shape.fill(editor, rand, this);
	}
}
