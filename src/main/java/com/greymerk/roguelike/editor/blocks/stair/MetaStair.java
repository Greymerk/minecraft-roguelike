package com.greymerk.roguelike.editor.blocks.stair;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.shapes.IShape;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.util.math.random.Random;

public class MetaStair implements IStair{

	MetaBlock stair;
	
	public static MetaStair of(Block block) {
		return new MetaStair(block);
	}
	
	public static MetaStair of(Stair type) {
		return new MetaStair(type);
	}
	
	private MetaStair(Block block) {
		this.stair = MetaBlock.of(block);
	}
	
	private MetaStair(Stair type) {
		this.stair = MetaBlock.of(Stair.getBlock(type));
	}
	
	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown){
		stair.withProperty(StairsBlock.FACING, Cardinal.facing(dir));
		stair.withProperty(StairsBlock.HALF, upsideDown ? BlockHalf.TOP : BlockHalf.BOTTOM);
		return this;
	}
	
	public MetaStair setShape(StairShape shape) {
		stair.withProperty(StairsBlock.SHAPE, shape);
		return this;
	}
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		this.setShape(Stair.getStairShape(this.stair, editor, pos.getBlockPos()));
		return editor.set(pos, stair);
	}
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		StairShape shape = Stair.getStairShape(this.stair, editor, pos.getBlockPos());
		this.setShape(shape);
		return editor.set(pos, stair, fillAir, replaceSolid);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape, boolean fillAir, boolean replaceSolid) {
		shape.fill(editor, rand, stair, fillAir, replaceSolid);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IShape shape) {
		shape.fill(editor, rand, stair);
	}
}
