package com.greymerk.roguelike.editor.blocks.slab;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

public interface ISlab {

	public ISlab upsideDown(boolean upsideDown);
	
	public boolean set(IWorldEditor editor, Coord pos);
	
	public boolean set(IWorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid);
	
}
