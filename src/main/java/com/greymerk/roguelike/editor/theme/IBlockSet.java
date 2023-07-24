package com.greymerk.roguelike.editor.theme;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.blocks.door.IDoor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;

public interface IBlockSet {

	public IBlockFactory getFloor();
	public IBlockFactory getWall();
	public IStair getStair();
	public IBlockFactory getPillar();
	public IDoor getDoor();
	public IBlockFactory getLightBlock();
	public IBlockFactory getLiquid();
	
}
