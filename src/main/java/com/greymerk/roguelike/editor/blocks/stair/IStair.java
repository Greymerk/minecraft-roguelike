package com.greymerk.roguelike.editor.blocks.stair;

import java.util.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

public interface IStair {

	public MetaStair setOrientation(Cardinal dir, Boolean upsideDown);
	
	public boolean set(IWorldEditor editor, Coord pos);
	
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid);

}
