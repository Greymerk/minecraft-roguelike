package com.greymerk.roguelike.editor.blocks;

import java.util.function.Predicate;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.shapes.IShape;

public class IronBar implements IBlockFactory{

	MetaBlock bar;
	
	public static IronBar get() {
		return new IronBar();
	}

	public static IBlockFactory getBroken() {
		BlockWeightedRandom blocks = new BlockWeightedRandom();
		blocks.add(Air.get(), 1);
		blocks.add(IronBar.get(), 2);
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
		case EAST: this.bar.with(IronBarsBlock.EAST, connects); return;
		case NORTH: this.bar.with(IronBarsBlock.NORTH, connects); return;
		case SOUTH: this.bar.with(IronBarsBlock.SOUTH, connects); return;
		case WEST: this.bar.with(IronBarsBlock.WEST, connects); return;
		default: return;
		}
	}

	@Override
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos){
		this.setShape(editor, pos);
		return bar.set(editor, rand, pos);
	}
	
	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape) {
		this.bar.fill(editor, rand, shape);
	}

	@Override
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos, Predicate<BlockContext> p) {
		this.setShape(editor, pos);
		return bar.set(editor, rand, pos, p);
	}

	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IShape shape, Predicate<BlockContext> p) {
		this.bar.fill(editor, rand, shape, p);
	}
}
