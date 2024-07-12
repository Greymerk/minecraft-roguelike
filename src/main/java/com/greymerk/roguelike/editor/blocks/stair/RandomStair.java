package com.greymerk.roguelike.editor.blocks.stair;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class RandomStair implements IStair, IBlockFactory {

	private Cardinal dir;
	private boolean upsideDown;
	private boolean waterlogged;
	private WeightedRandomizer<IStair> stairs;
	
	public RandomStair() {
		this.stairs = new WeightedRandomizer<IStair>();
		this.dir = Cardinal.NORTH;
		this.upsideDown = false;
		this.waterlogged = false;
	}

	public RandomStair add(IStair toAdd, int weight) {
		this.stairs.add(new WeightedChoice<IStair>(toAdd, weight));
		return this;
	}
	
	@Override
	public IStair setOrientation(Cardinal dir, Boolean upsideDown) {
		this.dir = Cardinal.directions.contains(dir) ? dir : Cardinal.NORTH;
		this.upsideDown = upsideDown;
		this.waterlogged = false;
		return this;
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		if(this.stairs.isEmpty()) return false;
		return this.stairs.get(rand).setOrientation(this.dir, this.upsideDown).set(editor, rand, pos);
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, Predicate<Pair<IWorldEditor, Coord>> p) {
		if(this.stairs.isEmpty()) return false;
		IStair stair = this.stairs.get(rand).setOrientation(this.dir, this.upsideDown);
		if(waterlogged) stair.waterlog();
		return stair.set(editor, rand, pos, p);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, Predicate<Pair<IWorldEditor, Coord>> p) {
		shape.forEach(pos -> {
			this.set(editor, rand, pos, p);
		});
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape) {
		this.fill(editor, rand, shape, Fill.ALWAYS);
	}

	@Override
	public IStair waterlog() {
		this.waterlogged = true;
		return this;
	}
}
