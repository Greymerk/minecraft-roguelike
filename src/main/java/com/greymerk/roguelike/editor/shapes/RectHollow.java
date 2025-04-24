package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;

import net.minecraft.util.math.random.Random;

public class RectHollow implements IShape {

	private BoundingBox bb;
	
	public RectHollow(BoundingBox bb){
		this.bb = bb;
	}

	public static void fill(IWorldEditor editor, Random rand, IBounded bb, IBlockFactory block) {
		bb.getShape(Shape.RECTHOLLOW).fill(editor, rand, block, Fill.ALWAYS);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block){
		fill(editor, rand, block, Fill.ALWAYS);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, Predicate<BlockContext> p) {
		this.forEach(c -> {
			block.set(editor, rand, c, p);
		});
		
		BoundingBox inner = this.bb.copy().grow(Cardinal.all, -1);
		RectSolid.fill(editor, rand, inner, Air.get());
	}
	
	@Override
	public List<Coord> get(){
		List<Coord> coords = new ArrayList<Coord>();
		
		for(Coord c : this){
			coords.add(c);
		}
		
		return coords;
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return new RectHollowIterator(bb);
	}
	
	private class RectHollowIterator implements Iterator<Coord>{

		Coord cursor;
		Coord c1;
		Coord c2;
		
		public RectHollowIterator(BoundingBox bb){
			this.c1 = bb.getStart();
			this.c2 = bb.getEnd();
			cursor = c1.copy();
		}
		
		@Override
		public boolean hasNext() {
			return this.cursor.getY() <= this.c2.getY();
		}

		@Override
		public Coord next() {
			
			Coord toReturn = cursor.copy();

			if(cursor.getZ() == c2.getZ() && cursor.getX() == c2.getX()){
				cursor = c1.withY(cursor.getY());
				cursor.add(Cardinal.UP);
				return toReturn;
			}
			
			if(cursor.getX() == c2.getX()){
				cursor = cursor.withX(c1.getX());
				cursor.add(Cardinal.SOUTH);
				return toReturn;
			}

			if(
				cursor.getY() != c1.getY()
				&& cursor.getY() != c2.getY()
				&& cursor.getZ() != c1.getZ()
				&& cursor.getZ() != c2.getZ()
				&& cursor.getX() == c1.getX()
			){
				cursor.add(Cardinal.EAST, c2.getX() - c1.getX());
			} else {
				cursor.add(Cardinal.EAST);	
			}
			
			return toReturn;
			
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();	
		}
	}


}
