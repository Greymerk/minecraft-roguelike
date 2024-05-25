package com.greymerk.roguelike.editor.blocks.stair;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class RandomStair implements IStair {

	private Cardinal dir;
	private boolean upsideDown;
	private WeightedRandomizer<IStair> stairs;
	
	public RandomStair() {
		this.stairs = new WeightedRandomizer<IStair>();
	}

	public RandomStair add(IStair toAdd, int weight) {
		this.stairs.add(new WeightedChoice<IStair>(toAdd, weight));
		return this;
	}
	
	@Override
	public IStair setOrientation(Cardinal dir, Boolean upsideDown) {
		this.dir = dir;
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
	
}
