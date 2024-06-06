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
	
	private MetaStair(MetaBlock stair) {
		this.stair = stair;
	}
	
	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown){
		stair.withProperty(StairsBlock.FACING, Cardinal.facing(dir));
		stair.withProperty(StairsBlock.HALF, upsideDown ? BlockHalf.TOP : BlockHalf.BOTTOM);
		return this;
	}
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos) {
		setStairShape(editor, pos);
		return editor.set(pos, stair);
	}
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		setStairShape(editor, pos);
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
	
	private void setStairShape(IWorldEditor editor, Coord pos) {
		
		// in front
		MetaBlock mb = editor.getBlock(pos.copy().add(this.direction()));
		if(isStair(mb)){
			MetaStair other = new MetaStair(mb);
			if(other.isUpsideDown() == this.isUpsideDown()){
				if(other.direction() == Cardinal.left(this.direction())) {
					this.setShape(StairShape.INNER_LEFT);
					return;
				}
				if(other.direction() == Cardinal.right(this.direction())) {
					this.setShape(StairShape.INNER_RIGHT);
					return;
				}
			}
		}
		
		// behind
		mb = editor.getBlock(pos.copy().add(Cardinal.reverse(this.direction())));
		if(isStair(mb)) {
			MetaStair other = new MetaStair(mb);
			if(other.isUpsideDown() == this.isUpsideDown()){
				if(other.direction() == Cardinal.left(this.direction())) {
					this.setShape(StairShape.OUTER_LEFT);
					return;
				}
				if(other.direction() == Cardinal.right(this.direction())) {
					this.setShape(StairShape.OUTER_RIGHT);
					return;
				}
			}
		}
		
		this.setShape(StairShape.STRAIGHT); // resetting by default
	}
	
    public Cardinal direction() {
    	return Cardinal.of(this.stair.get(StairsBlock.FACING));
    }
    
    public boolean isUpsideDown() {
    	return this.stair.get(StairsBlock.HALF) == BlockHalf.TOP;
    }
    
    private boolean isStair(MetaBlock mb) {
    	return mb.getBlock() instanceof StairsBlock;
    }
    
    private MetaStair setShape(StairShape shape) {
		stair.withProperty(StairsBlock.SHAPE, shape);
		return this;
	}
}
