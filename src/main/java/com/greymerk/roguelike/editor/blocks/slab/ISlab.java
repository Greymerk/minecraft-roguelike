package com.greymerk.roguelike.editor.blocks.slab;

import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

public interface ISlab{

	public ISlab upsideDown(boolean upsideDown);
	
	public MetaBlock get();
	
	public boolean set(IWorldEditor editor, Coord pos);
	
	public boolean set(IWorldEditor editor, Coord pos, Predicate<BlockContext> p);
	
}
