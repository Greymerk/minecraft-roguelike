package com.greymerk.roguelike.editor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.random.Random;

/**
A Wrapper for Mojang's BlockState objects

MetaBlocks implement IBlockFactory and may
therefore be used interchangeably and
recursively with other IBlockFactory containers
such as BlockWeightedRandom
**/
public class MetaBlock extends BlockBase{

	private BlockState state;
    
	public static MetaBlock of(Block block) {
		return new MetaBlock(block);
	}
	
	public static MetaBlock of(BlockState state) {
		return new MetaBlock(state);
	}
	
	private MetaBlock(Block block){
		this.setState(block.getDefaultState());
	}
	
	private MetaBlock(MetaBlock block){
		this.setState(block.state);
	}
	
	private MetaBlock(BlockState state){
		this.setState(state);
	}
	
	private void setState(BlockState bs){
		this.state = bs;
	}
	
	public BlockState getState() {
		return this.state;
	}
	
	public boolean set(IWorldEditor editor, Coord pos){
		return editor.set(pos, this, true, true);
	}
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return editor.set(pos, this, fillAir, replaceSolid);
	}
	
	public boolean isIn(TagKey<Block> tag) {
		return this.state.isIn(tag);
	}
	
	public <T extends Comparable<T>> T get(Property<T> property) {
		return this.state.get(property);
	}

	public Block getBlock() {
		return this.state.getBlock();
	}
	
	public int getFlag(){
		return Block.NOTIFY_ALL;
	}
	
	public <T extends Comparable<T>, V extends T> MetaBlock withProperty(Property<T> property, V value) {
		this.state = this.state.with(property, value);
		return this;
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
