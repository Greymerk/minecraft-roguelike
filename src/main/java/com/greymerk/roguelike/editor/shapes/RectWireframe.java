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
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;

import net.minecraft.util.math.random.Random;

public class RectWireframe implements IShape {

	private BoundingBox bb;
	
	private RectWireframe(BoundingBox bb){
		this.bb = bb;
	}
	
	public static RectWireframe of(IBounded box) {
		return new RectWireframe(BoundingBox.of(box));
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block){
		fill(editor, rand, block, Fill.ALWAYS);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, Predicate<BlockContext> p) {
		for(Coord c : this){
			block.set(editor, rand, c, p);
		}
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
		return new RectWireframeIterator(this.bb);
	}
	
	private class RectWireframeIterator implements Iterator<Coord>{

		Coord cursor;
		Coord c1;
		Coord c2;
		
		public RectWireframeIterator(BoundingBox bb){
			this.c1 = bb.getStart();
			this.c2 = bb.getEnd();
			cursor = this.c1.copy();
		}
		
		@Override
		public boolean hasNext() {
			return this.cursor.getY() <= this.c2.getY();
		}

		@Override
		public Coord next() {
			
			Coord toReturn = cursor.copy();	
			
			if(cursor.getZ() == c2.getZ() && cursor.getX() == c2.getX()){
				cursor = new Coord(c1.getX(), cursor.getY(), c1.getZ());
				cursor.add(Cardinal.UP);
				return toReturn;
			}
			
			if(cursor.getY() == c1.getY() || cursor.getY() == c2.getY()){
				
				if(cursor.getX() == c2.getX()){
					cursor = new Coord(c1.getX(), cursor.getY(), cursor.getZ());
					cursor.add(Cardinal.SOUTH);
					return toReturn;
				}
				
				if(cursor.getZ() == c1.getZ() || cursor.getZ() == c2.getZ()){
					cursor.add(Cardinal.EAST);
					return toReturn;
				}
				
				if(cursor.getX() == c1.getX()){
					cursor = new Coord(c2.getX(), cursor.getY(), cursor.getZ());
					return toReturn;
				}
				

			}

			if(cursor.getX() == c1.getX()){
				cursor = new Coord(c2.getX(), cursor.getY(), cursor.getZ());
				return toReturn;
			}
			
			if(cursor.getX() == c2.getX()){
				cursor = new Coord(c1.getX() ,cursor.getY(), c2.getZ());
				return toReturn;
			}
			
			cursor = new Coord(c2.getX(), cursor.getY(), cursor.getZ());
			return toReturn;
			
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();	
		}
	}


}
