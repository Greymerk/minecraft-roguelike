package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.util.math.random.Random;

public class IronBar extends MetaBlock{

	public static MetaBlock get() {
		return new IronBar();
	}
	
	public IronBar() {
		super(Blocks.IRON_BARS);
	}

	@Override
	public boolean set(IWorldEditor editor, Coord pos){
		this.setShape(editor, pos);
		return editor.set(pos, this, true, true);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		this.setShape(editor, pos);
		return editor.set(pos, this, fillAir, replaceSolid);
	}
	
	private void setShape(IWorldEditor editor, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			setConnection(editor, origin, dir, connects(editor, origin, dir));
		}
	}
	
	private boolean connects(IWorldEditor editor, Coord origin, Cardinal dir) {
		if(editor.isFaceFullSquare(origin.copy().add(dir), Cardinal.reverse(dir))) return true;
		if(editor.getBlock(origin.copy().add(dir)).getBlock() == Blocks.IRON_BARS) return true;		
		return false;
	}
	
	private void setConnection(IWorldEditor editor, Coord origin, Cardinal dir, boolean connects) {
		switch(dir) {
		case EAST: this.withProperty(PaneBlock.EAST, connects); return;
		case NORTH: this.withProperty(PaneBlock.NORTH, connects); return;
		case SOUTH: this.withProperty(PaneBlock.SOUTH, connects); return;
		case WEST: this.withProperty(PaneBlock.WEST, connects); return;
		default: return;
		}
	}
}
