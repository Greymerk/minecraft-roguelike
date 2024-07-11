package com.greymerk.roguelike.editor.factories;

import net.minecraft.util.math.random.Random;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.IShape;

public abstract class BlockBase implements IBlockFactory {
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		return set(editor, rand, pos, Fill.of(true, true));
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return set(editor, rand, pos, Fill.of(fillAir, replaceSolid));
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape){
		shape.fill(editor, rand, this, true, true);
	}
	
	public void fill(IWorldEditor editor, Random rand, IShape shape, Predicate<Pair<IWorldEditor, Coord>> p) {
		shape.fill(editor, rand, this, p);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid){
		shape.fill(editor, rand, this, fillAir, replaceSolid);
	}
}
