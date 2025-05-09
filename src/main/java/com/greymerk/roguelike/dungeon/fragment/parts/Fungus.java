package com.greymerk.roguelike.dungeon.fragment.parts;

import java.util.List;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class Fungus implements IFragment {

	static final int SIZE = 8;
	
	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		grow(editor, rand, origin.freeze(), SIZE);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin) {
		new Fungus().grow(editor, rand, origin.freeze(), SIZE);
	}
	
	private void grow(IWorldEditor editor, Random rand, Coord origin, int counter) {
		if(!editor.isAir(origin)) return;
		if((counter < 2 && rand.nextBoolean()) || counter == 0) {
			cap(editor, rand, origin);
			return;
		}
		MetaBlock.of(Blocks.MUSHROOM_STEM).set(editor, origin);
		List<Cardinal> randDirs = Cardinal.randDirs(rand);
		if(rand.nextInt(5) == 0) branch(editor, rand, origin.copy().add(randDirs.getFirst()).freeze(), randDirs.getFirst(), counter);
		grow(editor, rand, origin.add(findPath(editor, rand, origin)), counter - 1);
	}
	
	private Cardinal findPath(IWorldEditor editor, Random rand, Coord origin) {
		if(editor.isAir(origin.copy().add(Cardinal.UP)) && rand.nextInt(3) != 0) return Cardinal.UP;
		
		for(Cardinal dir : Cardinal.randDirs(rand)) {
			if(editor.isAir(origin.copy().add(dir))) return dir;
		};
		
		return Cardinal.UP;
	}
	
	private void branch(IWorldEditor editor, Random rand, Coord origin, Cardinal dir, int counter) {
		MetaBlock stem = MetaBlock.of(Blocks.MUSHROOM_STEM);
		if(editor.isAir(origin)) {
			stem.set(editor, origin);
		} else {
			return;
		}
		
		if(editor.isAir(origin.copy().add(dir))) {
			stem.set(editor, origin.copy().add(dir));
		} else {
			return;
		}
		
		grow(editor, rand, origin.copy().add(dir).add(Cardinal.UP), counter - 1);
		
	}
	
	private void cap(IWorldEditor editor, Random rand, Coord origin) {
		BlockWeightedRandom flesh = new BlockWeightedRandom()
			.add(MetaBlock.of(rand.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK), 3)
			.add(Air.get(), 1);
		
		if(rand.nextInt(3) != 0) {
			BoundingBox.of(origin).grow(Cardinal.directions).fill(editor, rand, flesh, Fill.AIR);	
		} else {
			flesh.set(editor, rand, origin);
		}
		
	}

}
