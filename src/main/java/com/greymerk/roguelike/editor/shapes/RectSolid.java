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
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;

import net.minecraft.util.math.random.Random;

public class RectSolid implements IShape {

	private BoundingBox bb;
	
	public RectSolid(IBounded box){
		this.bb = BoundingBox.of(box);
	}
	
	public static void fill(IWorldEditor editor, Random rand, IBounded box, IBlockFactory blocks) {
		new RectSolid(BoundingBox.of(box)).fill(editor, rand, blocks, Fill.ALWAYS);
	}
	
	public static void fill(IWorldEditor editor, Random rand, IBounded box, IBlockFactory blocks, Predicate<BlockContext> p) {
		new RectSolid(BoundingBox.of(box)).fill(editor, rand, blocks, p);
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block){
		this.forEach(c -> block.set(editor, rand, c));
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, Predicate<BlockContext> p) {
		this.forEach(c -> block.set(editor, rand, c, p));
	}

	@Override
	public List<Coord> get(){
		return StreamSupport.stream(this.spliterator(), false).toList();
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return new RectSolidIterator(this.bb);
	}
	
	private class RectSolidIterator implements Iterator<Coord>{

		Coord cursor;
		Coord start;
		Coord end;
		
		public RectSolidIterator(BoundingBox bb){
			this.start = bb.getStart();
			this.end = bb.getEnd();
			cursor = this.start.copy();
		}
		
		@Override
		public boolean hasNext() {
			return this.cursor.getY() <= this.end.getY();
		}

		@Override
		public Coord next() {
			
			Coord current = cursor.copy();
			
			if(cursor.getZ() == end.getZ() && cursor.getX() == end.getX()){
				cursor = start.withY(cursor.getY()).add(Cardinal.UP);
				return current;
			}
			
			if(cursor.getX() == end.getX()){
				cursor = cursor.withX(start.getX()).add(Cardinal.SOUTH);
				return current;
			}
			
			cursor.add(Cardinal.EAST);
			return current;
			
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();	
		}
	}
}
