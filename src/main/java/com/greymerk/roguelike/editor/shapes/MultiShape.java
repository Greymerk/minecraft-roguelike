package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class MultiShape implements IShape {
	
	private Set<Coord> shape;
	
	public MultiShape(){
		shape = new TreeSet<Coord>();
	}
	
	public void addShape(IShape toAdd){
		for(Coord pos : toAdd){
			shape.add(pos);
		}
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return shape.iterator();
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
		this.fill(editor, rand, block, Fill.ALWAYS);
	}

	@Override
	public List<Coord> get() {
		List<Coord> coords = new ArrayList<Coord>();
		for(Coord pos : this.shape){
			coords.add(pos.copy());
		}
		return coords;
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, Predicate<BlockContext> p) {
		shape.forEach(pos -> {
			block.set(editor, rand, pos, p);
		});
	}
}
