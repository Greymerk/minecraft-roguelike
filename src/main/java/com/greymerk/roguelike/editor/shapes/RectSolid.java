package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;

import net.minecraft.util.math.random.Random;

public class RectSolid implements IShape {

	private Coord start;
	private Coord end;
	
	public RectSolid(Coord start, Coord end){
		this.start = start;
		this.end = end;
	}
	
	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block){
		fill(editor, rand, start, end, block, true, true);
	}
	
	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block, boolean fillAir, boolean replaceSolid){
		new RectSolid(start, end).fill(editor, rand, block, fillAir, replaceSolid);
	}
	
	public static void fill(IWorldEditor editor, Random rand, IBounded box, IBlockFactory blocks) {
		fill(editor, rand, box.getStart(), box.getEnd(), blocks, true, true);
	}
	
	public static void fill(IWorldEditor editor, Random rand, IBounded box, IBlockFactory blocks, boolean fillAir, boolean replaceSolid) {
		fill(editor, rand, box.getStart(), box.getEnd(), blocks, fillAir, replaceSolid);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block){
		fill(editor, rand, block, true, true);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
		this.forEach(c -> block.set(editor, rand, c, fillAir, replaceSolid));
	}

	@Override
	public List<Coord> get(){
		List<Coord> coords = new ArrayList<Coord>();
		this.forEach(c -> coords.add(c));
		return coords;
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return new RectSolidIterator(this.start, this.end);
	}
	
	private class RectSolidIterator implements Iterator<Coord>{

		Coord cursor;
		Coord c1;
		Coord c2;
		
		public RectSolidIterator(Coord c1, Coord c2){
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
			
			if(cursor.getX() == c2.getX()){
				cursor = new Coord(c1.getX(), cursor.getY(), cursor.getZ());
				cursor.add(Cardinal.SOUTH);
				return toReturn;
			}
			
			cursor.add(Cardinal.EAST);
			return toReturn;
			
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();	
		}
	}
}
