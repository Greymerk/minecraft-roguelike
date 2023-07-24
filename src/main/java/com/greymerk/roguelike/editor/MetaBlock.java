package com.greymerk.roguelike.editor;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class MetaBlock extends BlockBase{

	private BlockState state;
    
	public MetaBlock(Block block){
		this.setState(block.getDefaultState());
	}
	
	public MetaBlock(MetaBlock block){
		this.setState(block.state);
	}
	
	public MetaBlock(BlockState state){
		this.setState(state);
	}
	
	public void setState(BlockState bs){
		this.state = bs;
	}
	
	public BlockState getState() {
		return this.state;
	}

	public boolean set(IWorldEditor editor, Coord pos){
		return editor.set(pos, this, true, true);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return editor.set(pos, this, fillAir, replaceSolid);
	}

	public Block getBlock() {
		return this.state.getBlock();
	}
	
	public int getFlag(){
		return 2;
	}

	@Override
	public boolean equals(Object other){
		if(other == this) return true; 
		if(other == null) return false;
		if(!(other instanceof MetaBlock)) return false;		
		
		MetaBlock otherBlock = (MetaBlock)other;
		return this.state.equals(otherBlock.state);
	}
}
