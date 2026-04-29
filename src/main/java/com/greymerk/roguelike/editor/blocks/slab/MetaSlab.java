package com.greymerk.roguelike.editor.blocks.slab;

import java.util.function.Predicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

public class MetaSlab implements ISlab {

	MetaBlock slab;
	
	public MetaSlab(Block slab) {
		this.slab = MetaBlock.of(slab);
	}
	
	@Override
	public MetaBlock get() {
		return this.slab;
	}
	
	@Override
	public ISlab upsideDown(boolean upsideDown) {
		this.slab.with(SlabBlock.TYPE, upsideDown ? SlabType.TOP : SlabType.BOTTOM);
		return this;
	}

	@Override
	public boolean set(IWorldEditor editor, Coord pos) {
		return editor.set(pos, slab);
	}

	@Override
	public boolean set(IWorldEditor editor, Coord pos, Predicate<BlockContext> p) {
		return editor.set(pos, slab, p);
	}


}
