package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class Column implements IShape {

	private Coord top;
	
	public Column(Coord top) {
		this.top = top.copy();
	}
	
	public static void fillDown(IWorldEditor editor, Random rand, IBlockFactory blocks, Coord top) {
		new Column(top).fillDown(editor, rand, blocks);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
		this.forEach(c -> block.set(editor, rand, top, Fill.ALWAYS));
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, Predicate<BlockContext> p) {
		this.forEach(c -> block.set(editor, rand, top, p));
	}
	
	public List<Coord> getUntilSolid(IWorldEditor editor){
		Iterator<Coord> itr = new FillDownIterator(editor, top.copy());
		List<Coord> cl = new ArrayList<Coord>();
		itr.forEachRemaining(c -> cl.add(c));
		return cl;
	}
	
	@Override
	public List<Coord> get() {
		return StreamSupport.stream(this.spliterator(), false).toList();
	}
	
	public void fillDown(IWorldEditor editor, Random rand, IBlockFactory blocks) {
		fillDown(editor, rand, blocks, Fill.ALWAYS);
	}
	
	public void fillDown(IWorldEditor editor, Random rand, IBlockFactory blocks, Predicate<BlockContext> p) {
		Iterator<Coord> itr = new FillDownIterator(editor, top.copy());
		itr.forEachRemaining(c -> blocks.set(editor, rand, c, p));
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return new ColumnIterator(top.copy());
	}
	
	private class ColumnIterator implements Iterator<Coord> {
		
		static final int MAX_DEPTH = -62;
		Coord current;
		
		public ColumnIterator(Coord origin) {
			this.current = origin.copy();
		}

		@Override
		public boolean hasNext() {
			return current.getY() > MAX_DEPTH;
		}

		@Override
		public Coord next() {
			Coord last = current.copy();
			this.current.add(Cardinal.DOWN);
			return last;			
		}
	}
	
	private class FillDownIterator extends ColumnIterator {

		private IWorldEditor editor;
		
		public FillDownIterator(IWorldEditor editor, Coord origin) {
			super(origin);
			this.editor = editor;
		}

		@Override
		public boolean hasNext() {
			if(editor.isSolid(this.current)) return false;
			return current.getY() > editor.getInfo().getBottomY();
		}
	}


}
