package com.greymerk.roguelike.editor;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
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
	
	public static List<MetaBlock> listOf(List<Block> blocks){
		return blocks.stream().map(b -> MetaBlock.of(b)).toList();
	}
	
	private MetaBlock(Block block){
		this.state = block.getDefaultState();
	}
	
	private MetaBlock(BlockState state){
		this.state = state;
	}
	
	public BlockState getState() {
		return this.state;
	}
	
	public boolean set(IWorldEditor editor, Coord pos){
		return editor.set(pos, this);
	}
	
	public boolean set(IWorldEditor editor, Coord pos, Predicate<BlockContext> p) {
		return editor.set(pos, this, p);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		return editor.set(pos, this);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, Predicate<BlockContext> p) {
		return editor.set(pos, this, p);
	}
		
	public boolean isIn(TagKey<Block> tag) {
		return this.state.isIn(tag);
	}
	
	public boolean isIn(List<TagKey<Block>> tags) {
		for(TagKey<Block> tag : tags) {
			if(isIn(tag)) return true;
		}
		
		return false;
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
	
	public <T extends Comparable<T>, V extends T> MetaBlock with(Property<T> property, V value) {
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
	
	public boolean isReplaceable() {
		return this.state.isReplaceable();
	}
	
	public boolean isPlant() {
		if(this.isIn(BlockTags.LOGS)) return true;
		if(this.isIn(BlockTags.SWORD_EFFICIENT)) return true;
		return false;
	}
	
	public boolean isGround() {
		if(this.isPlant()) return false;
		
		List<TagKey<Block>> tags = List.of(
				BlockTags.BASE_STONE_OVERWORLD, 
				BlockTags.DIRT, 
				BlockTags.SAND, 
				BlockTags.SNOW,
				BlockTags.STONE_ORE_REPLACEABLES, 
				BlockTags.SHOVEL_MINEABLE,
				BlockTags.BADLANDS_TERRACOTTA);
		
		return this.isIn(tags);
	}

	public boolean isLiquid() {
		Block b = this.state.getBlock();
		return b == Blocks.WATER || b == Blocks.LAVA;
	}
	
	@Override
	public String toString() {
		return this.state.toString();
	}
}
