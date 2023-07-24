package com.greymerk.roguelike.editor.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

public class BlockLayers extends BlockBase{

	private List<IBlockFactory> blocks;
	
	public BlockLayers(){
		blocks = new ArrayList<IBlockFactory>();
	}
	
	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = this.blocks.get(pos.getY() % this.blocks.size());
		return block.set(editor, rand, pos, fillAir, replaceSolid);
	}

}
