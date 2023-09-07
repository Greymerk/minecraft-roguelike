package com.greymerk.roguelike.editor.blocks.slab;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;

public class MetaSlab extends MetaBlock implements ISlab {

	public MetaSlab(Block slab) {
		super(slab);
	}
	
	public MetaSlab(MetaBlock slab) {
		super(slab);
	}
	
	@Override
	public ISlab upsideDown(boolean upsideDown) {
		this.withProperty(SlabBlock.TYPE, upsideDown ? SlabType.TOP : SlabType.BOTTOM);
		return this;
	}

	@Override
	public boolean set(IWorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {
		return editor.set(pos, this, fillAir, replaceSolid);
	}



}
