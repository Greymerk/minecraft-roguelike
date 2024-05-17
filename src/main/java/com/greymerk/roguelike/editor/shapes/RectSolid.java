package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;

import net.minecraft.util.math.random.Random;

public class RectSolid implements IShape {

	private BoundingBox bb;
	
	public RectSolid(BoundingBox bb){
		this.bb = bb;
	}
	
	public static void fill(IWorldEditor editor, Random rand, IBounded box, IBlockFactory blocks) {
		new RectSolid(box.getBoundingBox()).fill(editor, rand, blocks, true, true);
	}
	
	public static void fill(IWorldEditor editor, Random rand, IBounded box, IBlockFactory blocks, boolean fillAir, boolean replaceSolid) {
		new RectSolid(box.getBoundingBox()).fill(editor, rand, blocks, fillAir, replaceSolid);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block){
		this.fill(editor, rand, block, true, true);
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
		return new RectSolidIterator(this.bb);
	}
	
	private class RectSolidIterator implements Iterator<Coord>{

		Coord cursor;
		Coord c1;
		Coord c2;
		
		public RectSolidIterator(BoundingBox bb){
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
