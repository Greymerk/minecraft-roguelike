package com.greymerk.roguelike.editor;

import java.util.Random;

import com.greymerk.roguelike.editor.blocks.stair.IStair;

public interface IWorldEditor {
	
	public boolean set(Coord pos, MetaBlock metaBlock, boolean fillAir, boolean replaceSolid);
	
	public boolean set(Coord pos, MetaBlock metaBlock);
	
	public MetaBlock getBlock(Coord pos);

	public boolean isAir(Coord pos);
	
	//TileEntity getTileEntity(Coord pos);
	
	public long getSeed();
	
	public Random getRandom(Coord pos);
		
	public void fillDown(Random rand, Coord pos, IBlockFactory pillar);
	
	public Coord findSurface(Coord pos);
	
	public boolean isChunkLoaded(Coord pos);
	
	public boolean isGround(Coord pos);
	//boolean validGroundBlock(Coord pos);

	public void spiralStairStep(Random rand, Coord pos, IStair stair, IBlockFactory pillar);

	public boolean isOverworld();
	
	//int getStat(Block block);

	//TreasureManager getTreasure();

	//void addChest(ITreasureChest chest);

}
