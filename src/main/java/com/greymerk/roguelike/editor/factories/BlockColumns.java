package com.greymerk.roguelike.editor.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class BlockColumns extends BlockBase{

	private List<IBlockFactory> blocks;
	
	public BlockColumns(){
		blocks = new ArrayList<IBlockFactory>();
	}
	
	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, Predicate<BlockContext> p) {
		int size = blocks.size();
		int choice = Math.abs((pos.getX() % size + pos.getZ() % size)) % size;
		IBlockFactory block = blocks.get(choice);
		return block.set(editor, rand, pos, p);
	}
	
	

}
