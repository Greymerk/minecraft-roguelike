package com.greymerk.roguelike.editor.factories;

import net.minecraft.util.math.random.Random;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

public class BlockWeightedRandom extends BlockBase {

	private WeightedRandomizer<IBlockFactory> blocks;
	
	public BlockWeightedRandom(){
		blocks = new WeightedRandomizer<IBlockFactory>();
	}

	public BlockWeightedRandom addBlock(IBlockFactory toAdd, int weight){
		blocks.add(new WeightedChoice<IBlockFactory>(toAdd, weight));
		return this;
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, Predicate<Pair<IWorldEditor, Coord>> p) {
		IBlockFactory block = blocks.get(rand);
		return block.set(editor, rand, origin, p);
	}
}
