package com.greymerk.roguelike.editor.shapes;

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

public class Ellipsoid implements IShape {

	private Coord start;
	private Coord end;

	public Ellipsoid(Coord start, Coord end){
		this.start = start.copy();
		this.end = end.copy();
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return new EllipsoidIterator(start, end);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
		this.fill(editor, rand, block, Fill.ALWAYS);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, Predicate<BlockContext> p) {
		for(Coord pos : this){
			block.set(editor, rand, pos, p);
		}
	}
	
	@Override
	public List<Coord> get() {
		return StreamSupport.stream(this.spliterator(), false).toList();
	}

	private class EllipsoidIterator implements Iterator<Coord>{
 
		private Coord centre;
		private Coord diff;
		private Coord cursor;

		private Cardinal dir;
		private boolean top;
		
		public EllipsoidIterator(Coord centre, Coord end){
			this.centre = centre.copy(); 
			Coord s = centre.copy();
			Coord e = end.copy();
			
			this.diff = e.sub(s);
			this.diff = Coord.of(Math.abs(diff.getX()), Math.abs(diff.getY()), Math.abs(diff.getZ()));
			
			cursor = Coord.ZERO;
			top = true;
			this.dir = Cardinal.NORTH;
		}
		
		@Override
		public boolean hasNext() {
			return cursor.getY() < diff.getY();
		}

		@Override
		public Coord next() {
			Coord toReturn = centre.copy();
			toReturn.add(top ? Cardinal.UP : Cardinal.DOWN, cursor.getY());
			if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
				toReturn.add(Cardinal.left(dir), cursor.getX());
				toReturn.add(dir, cursor.getZ());
			} else {
				toReturn.add(dir, cursor.getX());
				toReturn.add(Cardinal.left(dir), cursor.getZ());
			}
			
			if(this.dir != Cardinal.NORTH || top){
				if(this.dir == Cardinal.NORTH){
					top = false;
				}
				dir = Cardinal.left(dir);
				return toReturn;
			}
			
			cursor.add(Cardinal.SOUTH);
			
			if(inRange(cursor)){
				dir = Cardinal.left(dir);
				top = true;
				return toReturn;
			} else {
				cursor = Coord.of(cursor.getX(), cursor.getY(), 0);
			}
			
			cursor.add(Cardinal.EAST);

			if(inRange(cursor)){
				dir = Cardinal.left(dir);
				top = true;
				return toReturn;
			} else {
				cursor = Coord.of(0, cursor.getY(), cursor.getZ());
			}
			
			cursor.add(Cardinal.UP);
			dir = Cardinal.left(dir);
			top = true;
			return toReturn;
		}
		
		private boolean inRange(Coord pos){
			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();
			
			double rx = this.diff.getX() == 0 ? 1 : this.diff.getX();
			double ry = this.diff.getY() == 0 ? 1 : this.diff.getY();
			double rz = this.diff.getZ() == 0 ? 1 : this.diff.getZ();
			
			return	((x / rx) * (x / rx)) + 
					((y / ry) * (y / ry)) + 
					((z / rz) * (z / rz))
					<= 1;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}


}
