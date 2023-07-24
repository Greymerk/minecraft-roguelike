package com.greymerk.roguelike.editor.blocks.door;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

public interface IDoor {
	
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir);
	
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean open);
}
