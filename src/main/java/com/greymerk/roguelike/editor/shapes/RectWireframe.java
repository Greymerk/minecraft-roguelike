package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

public class RectWireframe implements IShape {

	private Coord start;
	private Coord end;
	
	public RectWireframe(Coord start, Coord end){
		this.start = start;
		this.end = end;
	}
	
	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block){
		fill(editor, rand, start, end, block, true, true);
	}
	
	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block, boolean fillAir, boolean replaceSolid){
		RectWireframe rect = new RectWireframe(start, end);
		rect.fill(editor, rand, block, fillAir, replaceSolid);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block){
		fill(editor, rand, block, true, true);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
		for(Coord c : this){
			block.set(editor, rand, c, fillAir, replaceSolid);
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
		return new RectWireframeIterator(start, end);
	}
	
	private class RectWireframeIterator implements Iterator<Coord>{

		Coord cursor;
		Coord c1;
		Coord c2;
		
		public RectWireframeIterator(Coord c1, Coord c2){
			this.c1 = c1.copy();
			this.c2 = c2.copy();
			
			Coord.correct(this.c1, this.c2);
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
