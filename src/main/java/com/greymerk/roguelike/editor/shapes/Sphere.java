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

import net.minecraft.util.math.random.Random;

public class Sphere implements IShape {

	private BoundingBox bb;
	
	public Sphere(BoundingBox bb){
		this.bb = bb;
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return new SphereIterator(bb);
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
		List<Coord> copy = new ArrayList<Coord>();
		for(Coord pos : this){
			copy.add(pos);
		}
		return copy;
	}

	
	private class SphereIterator implements Iterator<Coord>{
 
		private Coord centre;
		private int radius;
		
		private int layer;
		private int row;
		private int col;

		private Cardinal dir;
		private boolean top;
		
		public SphereIterator(BoundingBox bb){
			this.centre = bb.getStart();
			Coord s = bb.getStart();
			Coord e = bb.getEnd();
			Coord diff = e.sub(s);
			
			int r = diff.getX();
			r = r < diff.getY() ? diff.getY() : r;
			r = r < diff.getZ() ? diff.getZ() : r;
			this.radius = r;
			
			layer = 0;
			row = 0;
			col = 0;
			top = true;
			this.dir = Cardinal.NORTH;
		}
		
		@Override
		public boolean hasNext() {
			return layer < radius;
		}

		@Override
		public Coord next() {
			Coord toReturn = centre.copy();
			toReturn.add(top ? Cardinal.UP : Cardinal.DOWN, layer);
			toReturn.add(dir, row);
			toReturn.add(Cardinal.left(dir), col);
			if(this.dir != Cardinal.NORTH || top){
				if(this.dir == Cardinal.NORTH){
					top = false;
				}
				dir = Cardinal.left(dir);
				return toReturn;
			}
			
			col += 1;
			
			if(inRange(col, layer, row)){
				dir = Cardinal.left(dir);
				top = true;
				return toReturn;
			} else {
				col = 0;
			}
			
			row += 1;

			if(inRange(col, layer, row)){
				dir = Cardinal.left(dir);
				top = true;
				return toReturn;
			} else {
				row = 0;
			}
			
			layer += 1;
			dir = Cardinal.left(dir);
			top = true;
			return toReturn;
		}
		
		private boolean inRange(int x, int y, int z){
			return 	x * x + y * y + z * z < radius * radius;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
