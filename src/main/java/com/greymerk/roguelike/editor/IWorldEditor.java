package com.greymerk.roguelike.editor;

import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.treasure.ITreasureChest;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.random.Random;

public interface IWorldEditor {
	
	public boolean set(Coord pos, MetaBlock metaBlock, boolean fillAir, boolean replaceSolid);
	
	public boolean set(Coord pos, MetaBlock metaBlock);
	
	public MetaBlock getBlock(Coord pos);

	public boolean isAir(Coord pos);
	
	public BlockEntity getBlockEntity(Coord pos);
	
	public long getSeed();
	
	public Random getRandom(Coord pos);
		
	public void fillDown(Random rand, Coord pos, IBlockFactory pillar);
	
	public Coord findSurface(Coord pos);
	
	public boolean isChunkLoaded(Coord pos);
	
	public boolean isGround(Coord pos);
	
	public void spiralStairStep(Random rand, Coord pos, IStair stair, IBlockFactory pillar);

	public boolean isOverworld();

	public boolean isSolid(Coord pos);
		
	public boolean isFaceFullSquare(Coord pos, Cardinal dir);
	
	public void fillChest(ITreasureChest chest, Random rand);
	
	//int getStat(Block block);

	//TreasureManager getTreasure();

	//void addChest(ITreasureChest chest);

}
