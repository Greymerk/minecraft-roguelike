package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class Line implements IShape{

	Coord start;
	Coord end;
	
	public static Line of(Coord start, Coord end) {
		return new Line(start, end);
	}
	
	public Line(Coord start, Coord end){
		this.start = start.copy();
		this.end = end.copy();
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
		this.fill(editor, rand, block, Fill.ALWAYS);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, Predicate<BlockContext> p) {
		for(Coord c : this){
			block.set(editor, rand, c, p);
		}
	}
	
	@Override
	public List<Coord> get() {
		List<Coord> coords = new ArrayList<Coord>();
		for(Coord c : this){
			coords.add(c);
		}
		return coords;
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return new LineIterator();
	}
	
	// https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
	private class LineIterator implements Iterator<Coord>{
		
		int x; int y; int z;
		int dx; int dy; int dz;
		int l; int m; int n;
		int x_inc; int y_inc; int z_inc;
		int err_1; int err_2;
		int dx2; int dy2; int dz2;
		int counter;
		Coord current;
		
		public LineIterator(){
			
			this.current = start.copy();
			
			x = start.getX();
			y = start.getY();
			z = start.getZ();
			
			// delta value by dimension
			dx = end.getX() - start.getX();
			dy = end.getY() - start.getY();
			dz = end.getZ() - start.getZ();
			
			//direction of increment per dimension
			x_inc = dx < 0 ? -1 : 1;
			y_inc = dy < 0 ? -1 : 1;
			z_inc = dz < 0 ? -1 : 1;
			
			//distance value on each dimension
			l = Math.abs(dx);
			m = Math.abs(dy);
			n = Math.abs(dz);
			
			// double the distance values
			dx2 = l << 1;
			dy2 = m << 1;
			dz2 = n << 1;
			counter = 0;
			
			if(l >= m && l >= n){
				err_1 = dy2 - l;
				err_2 = dz2 - l;
			} else if(m >= l && m >= n){
				err_1 = dx2 - m;
				err_2 = dz2 - m;
			} else {
				err_1 = dy2 - n;
				err_2 = dx2 - n;
			}
			
		}
		
		@Override
		public boolean hasNext() {			
			if(l >= m && l >= n){
				return counter <= l;
			} else if(m >= l && m >= n){
				return counter <= m;
			} else {
				return counter <= n;
			}
		}

		@Override
		public Coord next() {
			//x increment
			if(l > m && l > n){
				if(err_1 > 0){
					y += y_inc;
					err_1 -= dx2;
				}
				if(err_2 > 0){
					z += z_inc;
					err_2 -= dx2;
				}
				err_1 += dy2;
				err_2 += dz2;
				x += x_inc;
			//y increment
			} else if(m > l && m > n){
				if(err_1 > 0){
					x += x_inc;
					err_1 -= dy2;
				}
				if(err_2 > 0){
					z += z_inc;
					err_2 -= dy2;
				}
				err_1 += dx2;
				err_2 += dz2;
				y += y_inc;
			//z increment
			} else {
				if(err_1 > 0){
					y += y_inc;
					err_1 -= dz2;
				}
				if(err_2 > 0){
					x += x_inc;
					err_2 -= dz2;
				}
				err_1 += dy2;
				err_2 += dx2;
				z += z_inc;
			}
			
			++counter;
			
			Coord toReturn = this.current.copy();
			this.current = new Coord(x, y, z);
			return toReturn;
		}
	}



}
