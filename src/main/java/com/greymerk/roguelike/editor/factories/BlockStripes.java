package com.greymerk.roguelike.editor.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

public class BlockStripes extends BlockBase {

	private List<IBlockFactory> blocks;
	
	public BlockStripes(){
		blocks = new ArrayList<IBlockFactory>();
	}

	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
		int size = blocks.size();
		int choice = Math.abs((origin.getX() % size + origin.getY() % size + origin.getZ() % size)) % size;
		IBlockFactory block = blocks.get(choice);
		return block.set(editor, rand, origin, fillAir, replaceSolid);
	}
}
