package greymerk.roguelike.worldgen.shapes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;

public class MultiShape implements IShape {

	private List<IShape> shapes;
	
	public MultiShape(){
		this.shapes = new ArrayList<IShape>();
	}
	
	public void addShape(IShape shape){
		this.shapes.add(shape);
	}
	
	@Override
	public Iterator<Coord> iterator() {
		return new MultiShapeIterator(shapes);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block) {
		this.fill(editor, rand, block, true, true);
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
		for(Coord c : this){
			block.set(editor, rand, c, fillAir, replaceSolid);
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
	
	private class MultiShapeIterator implements Iterator<Coord>{
		
		Set<Coord> index;
		Queue<IShape> shapes;
		Iterator<Coord> multishape;
		
		
		public MultiShapeIterator(List<IShape> shapes){
			this.index = new HashSet<Coord>();
			this.shapes = new LinkedList<IShape>();
			this.shapes.addAll(shapes);
			for(IShape shape : shapes){
				for(Coord pos : shape){
					index.add(pos);
				}
			}
			this.multishape = index.iterator();
		}

		@Override
		public boolean hasNext() {
			return multishape.hasNext();
		}

		@Override
		public Coord next() {
			return multishape.next();
		}
	}
}
