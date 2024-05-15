package com.greymerk.roguelike.editor.theme;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.blocks.door.IDoor;
import com.greymerk.roguelike.editor.blocks.slab.ISlab;
import com.greymerk.roguelike.editor.blocks.stair.IStair;

public interface IBlockSet {

	public IBlockFactory getFloor();
	public IBlockSet setFloor(IBlockFactory floor);
	
	public IBlockFactory getWall();
	public IBlockSet setWall(IBlockFactory wall);
	
	public IBlockFactory getPillar();
	public IBlockSet setPillar(IBlockFactory pillar);
	
	public IStair getStair();
	public IBlockSet setStair(IStair stair);
	
	public ISlab getSlab();
	public IBlockSet setSlab(ISlab slab);
	
	public IDoor getDoor();
	public IBlockSet setDoor(IDoor door);
	
	public IBlockFactory getLightBlock();
	public IBlockSet setLightBlock(IBlockFactory lightBlock);
	
	public IBlockFactory getLiquid();
	public IBlockSet setLiquid(IBlockFactory liquid);
	
}
