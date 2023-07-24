package com.greymerk.roguelike.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.StructureWorldAccess;

public class WorldEditor implements IWorldEditor{

	StructureWorldAccess world;
	private Map<Block, Integer> stats;
	//private TreasureManager chests;
	
	public WorldEditor(StructureWorldAccess world){
		this.world = world;
		stats = new HashMap<Block, Integer>();
		//this.chests = new TreasureManager();
	}

	@Override
	public boolean set(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid) {
		MetaBlock currentBlock = getBlock(pos);
		
		if(currentBlock.getBlock() == Blocks.CHEST) return false;
		if(currentBlock.getBlock() == Blocks.TRAPPED_CHEST) return false;
		if(currentBlock.getBlock() == Blocks.SPAWNER) return false;
		
		//boolean isAir = world.isAirBlock(pos.getBlockPos());
		boolean isAir = currentBlock.getBlock() == Blocks.AIR;
		
		if(!fillAir && isAir) return false;
		if(!replaceSolid && !isAir)	return false;
		
		try{
			world.setBlockState(pos.getBlockPos(), block.getState(), block.getFlag());
		} catch(NullPointerException npe){
			//ignore it.
		}
		
		Block type = block.getBlock();
		Integer count = stats.get(type);
		if(count == null){
			stats.put(type, 1);	
		} else {
			stats.put(type, count + 1);
		}
		
		return true;
	}
	
	@Override
	public boolean set(Coord pos, MetaBlock metaBlock) {
		return this.set(pos, metaBlock, true, true);
	}
	
	@Override
	public MetaBlock getBlock(Coord pos) {
		BlockState state = world.getBlockState(pos.getBlockPos());
		return new MetaBlock(state);
	}

	@Override
	public boolean isAir(Coord pos) {
		return world.isAir(pos.getBlockPos());
	}

	@Override
	public long getSeed() {
		return world.getSeed();
	}

	@Override
	public void fillDown(Random rand, Coord origin, IBlockFactory blocks) {
		Coord cursor = new Coord(origin);
		
		while(!isSolid(cursor) && cursor.getY() > 1){
			blocks.set(this, rand, cursor);
			cursor.add(Cardinal.DOWN);
		}
	}
	

	public Coord findSurface(Coord pos) {
		
		Coord cursor = new Coord(pos.getX(), 256, pos.getZ());
		
		while(cursor.getY() > 60) {
			if(!isAir(cursor) && !isPlant(cursor)) return cursor;
			cursor.add(Cardinal.DOWN);
		}
		
		return cursor;
	}
	
	public boolean isSolid(Coord pos) {
		return this.world.getBlockState(pos.getBlockPos()).isSolidBlock(world, pos.getBlockPos());
	}
	
	public boolean isPlant(Coord pos) {
		BlockState bs = getBlock(pos).getState();
		if(bs.isIn(BlockTags.LOGS)) return true;
		if(bs.isIn(BlockTags.SWORD_EFFICIENT)) return true;
		return false;
	}
	
	public boolean isGround(Coord pos) {
		MetaBlock m = getBlock(pos);
		if(isPlant(pos)) return false;
		if(m.getState().isIn(BlockTags.BASE_STONE_OVERWORLD)) return true;
		if(m.getState().isIn(BlockTags.DIRT)) return true;
		return false;
	}
	
	@Override
	public void spiralStairStep(Random rand, Coord origin, IStair stair, IBlockFactory fill){
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(new Coord(-1, 0, -1));
		end = new Coord(origin);
		end.add(new Coord(1, 0, 1));
		
		RectSolid.fill(this, rand, start, end, air);
		fill.set(this, rand, origin);
		
		Cardinal dir = Cardinal.directions[origin.getY() % 4];
		cursor = new Coord(origin);
		cursor.add(dir);
		stair.setOrientation(Cardinal.left(dir), false).set(this, cursor);
		cursor.add(Cardinal.right(dir));
		stair.setOrientation(Cardinal.right(dir), true).set(this, cursor);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).set(this, cursor);
	}
	
	
}
