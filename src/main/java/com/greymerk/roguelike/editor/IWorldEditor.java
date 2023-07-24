package com.greymerk.roguelike.editor;

import java.util.Random;

import com.greymerk.roguelike.editor.blocks.stair.IStair;

public interface IWorldEditor {
	
	public boolean set(Coord pos, MetaBlock metaBlock, boolean fillAir, boolean replaceSolid);
	
	public boolean set(Coord pos, MetaBlock metaBlock);
	
	MetaBlock getBlock(Coord pos);

	boolean isAir(Coord pos);
	
	//TileEntity getTileEntity(Coord pos);
	
	long getSeed();
		
	void fillDown(Random rand, Coord pos, IBlockFactory pillar);
	
	public Coord findSurface(Coord pos);
	
	public boolean isGround(Coord pos);
	//boolean validGroundBlock(Coord pos);

	void spiralStairStep(Random rand, Coord pos, IStair stair, IBlockFactory pillar);

	//int getStat(Block block);

	//TreasureManager getTreasure();

	//void addChest(ITreasureChest chest);

}
