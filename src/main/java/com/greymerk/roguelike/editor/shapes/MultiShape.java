package com.greymerk.roguelike.editor.shapes;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;
import net.minecraft.util.RandomSource;
import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

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
	public void fill(IWorldEditor editor, RandomSource rand, IBlockFactory block) {
		this.fill(editor, rand, block, Fill.ALWAYS);
	}

	@Override
	public List<Coord> get() {
		return StreamSupport.stream(this.spliterator(), false).toList();
	}

	@Override
	public void fill(IWorldEditor editor, RandomSource rand, IBlockFactory block, Predicate<BlockContext> p) {
		shape.forEach(pos -> {
			block.set(editor, rand, pos, p);
		});
	}
}
