package com.greymerk.roguelike.editor.blocks.stair;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class RandomStair implements IStair, IBlockFactory {

	private Cardinal dir;
	private boolean upsideDown;
	private WeightedRandomizer<IStair> stairs;
	
	public RandomStair() {
		this.stairs = new WeightedRandomizer<IStair>();
		this.dir = Cardinal.NORTH;
		this.upsideDown = false;
	}

	public RandomStair add(IStair toAdd, int weight) {
		this.stairs.add(new WeightedChoice<IStair>(toAdd, weight));
		return this;
	}
	
	@Override
	public IStair setOrientation(Cardinal dir, Boolean upsideDown) {
		this.dir = Cardinal.directions.contains(dir) ? dir : Cardinal.NORTH;
		this.upsideDown = upsideDown;
		return this;
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		if(this.stairs.isEmpty()) return false;
		return this.stairs.get(rand).setOrientation(this.dir, this.upsideDown).set(editor, rand, pos);
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		if(this.stairs.isEmpty()) return false;
		return this.stairs.get(rand).setOrientation(this.dir, this.upsideDown).set(editor, rand, pos, fillAir, replaceSolid);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid) {
		shape.forEach(pos -> {
			this.set(editor, rand, pos, fillAir, replaceSolid);
		});
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape) {
		this.fill(editor, rand, shape, true, true);
	}
	
}
