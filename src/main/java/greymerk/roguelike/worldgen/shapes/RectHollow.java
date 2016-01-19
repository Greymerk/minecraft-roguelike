package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class RectHollow implements IShape {

	private Coord start;
	private Coord end;
	
	public RectHollow(Coord start, Coord end){
		this.start = start;
		this.end = end;
	}
	
	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block){
		fill(editor, rand, start, end, block, true, true);
	}
	
	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block, boolean fillAir, boolean replaceSolid){
		RectHollow rect = new RectHollow(start, end);
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
		
		Coord innerStart = new Coord(start);
		Coord innerEnd = new Coord(end);
		Coord.correct(innerStart, innerEnd);
		innerStart.add(new Coord(1, 1, 1));
		innerEnd.add(new Coord(-1, -1, -1));
		RectSolid.fill(editor, rand, innerStart, innerEnd, BlockType.get(BlockType.AIR));
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
		return new RectHollowIterator(start, end);
	}
	
	private class RectHollowIterator implements Iterator<Coord>{

		Coord cursor;
		Coord c1;
		Coord c2;
		
		public RectHollowIterator(Coord c1, Coord c2){
			this.c1 = new Coord(c1);
			this.c2 = new Coord(c2);
			
			Coord.correct(this.c1, this.c2);
			cursor = new Coord(this.c1);
		}
		
		@Override
		public boolean hasNext() {
			return this.cursor.getY() <= this.c2.getY();
		}

		@Override
		public Coord next() {
			
			Coord toReturn = new Coord(cursor);

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
