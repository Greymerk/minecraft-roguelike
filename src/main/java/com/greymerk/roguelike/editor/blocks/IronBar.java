package com.greymerk.roguelike.editor.blocks;

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
		case EAST: this.bar.withProperty(PaneBlock.EAST, connects); return;
		case NORTH: this.bar.withProperty(PaneBlock.NORTH, connects); return;
		case SOUTH: this.bar.withProperty(PaneBlock.SOUTH, connects); return;
		case WEST: this.bar.withProperty(PaneBlock.WEST, connects); return;
		default: return;
		}
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos){
		this.setShape(editor, pos);
		return editor.set(pos, this.bar, true, true);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		this.setShape(editor, pos);
		return editor.set(pos, bar, fillAir, replaceSolid);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid) {
		this.bar.fill(editor, rand, shape, fillAir, replaceSolid);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape) {
		this.bar.fill(editor, rand, shape);
	}
}
