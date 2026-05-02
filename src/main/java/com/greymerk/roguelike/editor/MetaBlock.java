package com.greymerk.roguelike.editor;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

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
		this.state = block.defaultBlockState();
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
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos) {
		return editor.set(pos, this);
	}
	
	@Override
	public boolean set(IWorldEditor editor, RandomSource rand, Coord pos, Predicate<BlockContext> p) {
		return editor.set(pos, this, p);
	}
		
	public boolean isIn(TagKey<Block> tag) {
		return this.state.is(tag);
	}
	
	public boolean isIn(List<TagKey<Block>> tags) {
		for(TagKey<Block> tag : tags) {
			if(isIn(tag)) return true;
		}
		
		return false;
	}
	
	public <T extends Comparable<T>> T get(Property<T> property) {
		return this.state.getValue(property);
	}

	public Block getBlock() {
		return this.state.getBlock();
	}
	
	public int getFlag(){
		return Block.UPDATE_ALL;
	}
	
	public <T extends Comparable<T>, V extends T> MetaBlock with(Property<T> property, V value) {
		this.state = this.state.setValue(property, value);
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
		return this.state.canBeReplaced();
	}
	
	public boolean isGround() {
		if(this.isIn(BlockTags.AIR)) return false;
		
		// plants etc
		if(this.isIn(List.of(
			BlockTags.SWORD_EFFICIENT,
			BlockTags.MINEABLE_WITH_HOE,
			BlockTags.MINEABLE_WITH_AXE,
			BlockTags.SNOW,
			BlockTags.CLIMBABLE,
			BlockTags.REPLACEABLE_BY_TREES,
			BlockTags.FLOWERS
			))) return false;
		
		if(this.getBlock() == Blocks.CACTUS) return false;
		if(this.getBlock() == Blocks.ICE) return false;
		
		// stone & dirt etc
		return this.isIn(List.of(
				BlockTags.BASE_STONE_OVERWORLD, 
				BlockTags.DIRT, 
				BlockTags.SAND,
				BlockTags.ICE,
				BlockTags.STONE_ORE_REPLACEABLES, 
				BlockTags.MINEABLE_WITH_SHOVEL,
				BlockTags.BADLANDS_TERRACOTTA));
		
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
