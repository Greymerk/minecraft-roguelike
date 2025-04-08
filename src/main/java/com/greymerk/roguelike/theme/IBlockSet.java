package com.greymerk.roguelike.theme;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.blocks.door.IDoor;
import com.greymerk.roguelike.editor.blocks.slab.ISlab;
import com.greymerk.roguelike.editor.blocks.stair.IStair;

public interface IBlockSet {
	public IBlockFactory getFloor();
	public IBlockFactory getWall();
	public IBlockFactory getPillar();
	public IStair getStair();
	public ISlab getSlab();
	public IDoor getDoor();
	public IBlockFactory getLightBlock();
	public IBlockFactory getLiquid();	
}
