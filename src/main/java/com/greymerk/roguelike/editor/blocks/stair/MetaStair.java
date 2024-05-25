package com.greymerk.roguelike.editor.blocks.stair;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.util.math.random.Random;

public class MetaStair extends MetaBlock implements IStair{

	public MetaStair(Block block) {
		super(block);
	}

	public MetaStair(MetaBlock block){
		super(block);
	}
	
	public MetaStair(Stair type) {
		super(Stair.getBlock(type));
	}
	
	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown){
		BlockState stair = this.getBlock().getDefaultState();
		stair = stair.with(StairsBlock.FACING, Cardinal.facing(dir));
		stair = stair.with(StairsBlock.HALF, upsideDown ? BlockHalf.TOP : BlockHalf.BOTTOM);
		this.setState(stair);
		return this;
	}
	
	public MetaStair setShape(StairShape shape) {
		BlockState stair = this.getState();
		stair = stair.with(StairsBlock.SHAPE, shape);
		this.setState(stair);
		return this;
	}
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		StairShape shape = Stair.getStairShape(this.getState(), editor, pos.getBlockPos());
		this.setShape(shape);
		return editor.set(pos, this, true, true);
	}
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		StairShape shape = Stair.getStairShape(this.getState(), editor, pos.getBlockPos());
		this.setShape(shape);
		return editor.set(pos, this, fillAir, replaceSolid);
	}
	

	
}
