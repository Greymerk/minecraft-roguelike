package com.greymerk.roguelike.editor.factories;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class BlockLayers extends BlockBase{

	private Map<Integer, IBlockFactory> layers;
	private IBlockFactory background;
	
	
	public BlockLayers(IBlockFactory background){
		this.background = background;
		this.layers = new HashMap<Integer, IBlockFactory>();	
	}
	
	public void add(int offset, IBlockFactory toAdd){
		this.layers.put(offset, toAdd);
	}
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, Predicate<BlockContext> p) {
		for(int offset : this.layers.keySet()) {
			if(Math.floorMod(pos.getY(), 10) - offset == 0) {
				return this.layers.get(offset).set(editor, rand, pos, p);
			}
		}
		return this.background.set(editor, rand, pos, p);
	}

}
