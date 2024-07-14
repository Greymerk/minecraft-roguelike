package com.greymerk.roguelike.editor.blocks;

import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.shapes.IShape;

import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.util.math.random.Random;

public class IronBar implements IBlockFactory{

	MetaBlock bar;
	
	public static IronBar get() {
		return new IronBar();
	}

	public static IBlockFactory getBroken() {
		BlockWeightedRandom blocks = new BlockWeightedRandom();
		blocks.addBlock(Air.get(), 1);
		blocks.addBlock(IronBar.get(), 2);
		return blocks;
	}
	
	public IronBar() {
		this.bar = MetaBlock.of(Blocks.IRON_BARS);
	}

	private void setShape(IWorldEditor editor, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			setConnection(editor, origin, dir, connects(editor, origin, dir));
		}
	}
	
	private boolean connects(IWorldEditor editor, Coord origin, Cardinal dir) {
		if(editor.isFaceFullSquare(origin.copy().add(dir), Cardinal.reverse(dir))) return true;
		if(editor.getBlock(origin.copy().add(dir)).getBlock() == Blocks.IRON_BARS) return true;		
		return false;
	}
	
	private void setConnection(IWorldEditor editor, Coord origin, Cardinal dir, boolean connects) {
		switch(dir) {
		case EAST: this.bar.with(PaneBlock.EAST, connects); return;
		case NORTH: this.bar.with(PaneBlock.NORTH, connects); return;
		case SOUTH: this.bar.with(PaneBlock.SOUTH, connects); return;
		case WEST: this.bar.with(PaneBlock.WEST, connects); return;
		default: return;
		}
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos){
		this.setShape(editor, pos);
		return bar.set(editor, rand, pos);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape) {
		this.bar.fill(editor, rand, shape);
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, Predicate<BlockContext> p) {
		this.setShape(editor, pos);
		return bar.set(editor, rand, pos, p);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, Predicate<BlockContext> p) {
		this.bar.fill(editor, rand, shape, p);
	}
}
