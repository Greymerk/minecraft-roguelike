package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

import net.minecraft.util.math.random.Random;

public class RectPyramid implements IShape {

	private BoundingBox bb;
	
	public RectPyramid(BoundingBox bb){
		this.bb = bb;
	}
	
	
	@Override
	public Iterator<Coord> iterator() {
		return new SquarePyramidIterator(this.bb);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
		fill(editor, rand, block, true, true);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
		for (Coord pos : this){
			block.set(editor, rand, pos, fillAir, replaceSolid);
		}

	}

	@Override
	public List<Coord> get() {
		List<Coord> shape = new ArrayList<Coord>();
		for (Coord pos : this){
			shape.add(pos);
		}
		return shape;
	}
	
	private class SquarePyramidIterator implements Iterator<Coord>{

		Coord start;
		Coord diff;
		Coord cursor;
		Cardinal dir;
		double thetaX;
		double thetaZ;
		
		public SquarePyramidIterator(BoundingBox bb){
			this.start = bb.getStart();
			Coord s = bb.getStart();
			Coord e = bb.getEnd();
			
			cursor = new Coord(0,0,0);
			dir = Cardinal.NORTH;
			
			diff = e.copy();
			diff.sub(s);
			
			double hx = Math.sqrt(Math.pow(diff.getX(), 2) + Math.pow(diff.getY(), 2));
			thetaX = Math.acos((double)diff.getY() / hx);
			
			double hz = Math.sqrt(Math.pow(diff.getZ(), 2) + Math.pow(diff.getY(), 2));
			thetaZ = Math.acos((double)diff.getY() / hz);
		}
		
		@Override
		public boolean hasNext() {
			return cursor.getY() < this.diff.getY();
		}

		@Override
		public Coord next() {

			Coord toReturn = start.copy();
			toReturn.add(Cardinal.UP, cursor.getY());
			if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
				toReturn.add(Cardinal.left(dir), cursor.getX());
				toReturn.add(dir, cursor.getZ());
			} else {
				toReturn.add(dir, cursor.getX());
				toReturn.add(Cardinal.left(dir), cursor.getZ());
			}

			if(this.dir != Cardinal.NORTH){
				dir = Cardinal.left(dir);
				return toReturn;
			}
			
			cursor.add(Cardinal.SOUTH);
			
			if(inRange(cursor)){	
				dir = Cardinal.left(dir);
				return toReturn;
			}
			
			cursor = new Coord(cursor.getX(), cursor.getY(), 0);
			
			
			cursor.add(Cardinal.EAST);
			
			if(inRange(cursor)){
				dir = Cardinal.left(dir);
				return toReturn;
			}
			
			cursor = new Coord(0, cursor.getY(), cursor.getZ());
			cursor.add(Cardinal.UP);
			dir = Cardinal.left(dir);
			return toReturn;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		private boolean inRange(Coord pos){
			int y = diff.getY() - cursor.getY();
			
			if(!(cursor.getX() < Math.tan(thetaX) * y)){
				return false;
			}
			
			if(!(cursor.getZ() < Math.tan(thetaZ) * y)){
				return false;
			}
			
			return true;
			
		}
		
	}

}
