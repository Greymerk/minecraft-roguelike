package com.greymerk.roguelike.editor;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.greymerk.roguelike.editor.shapes.IShape;

import net.minecraft.util.math.random.Random;

public abstract class BlockBase implements IBlockFactory {
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		return set(editor, rand, pos, Fill.ALWAYS);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, Predicate<Pair<IWorldEditor, Coord>> p) {
		shape.fill(editor, rand, this, p);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape){
		shape.fill(editor, rand, this);
	}
}
