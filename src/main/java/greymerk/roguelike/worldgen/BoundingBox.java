package greymerk.roguelike.worldgen;

import java.util.Random;

import greymerk.roguelike.worldgen.shapes.RectWireframe;

public class BoundingBox implements IBounded{

	private Coord start;
	private Coord end;
	
	public BoundingBox(Coord start, Coord end){
		this.start = new Coord(start);
		this.end = new Coord(end);
		
		Coord.correct(this.start, this.end);
	}
	
	public BoundingBox getBoundingBox(){
		return this;
	}
	
	public boolean collide(IBounded other){
		
		BoundingBox otherBox = other.getBoundingBox();
		
		if(end.getX() < otherBox.start.getX()
			|| otherBox.end.getX() < start.getX()) return false; 
		
		if(end.getY() < otherBox.start.getY()
			|| otherBox.end.getY() < start.getY()) return false; 
		
		if(end.getZ() < otherBox.start.getZ()
			|| otherBox.end.getZ() < start.getZ()) return false; 
		
		return true;
	}
	
	public void generate(IWorldEditor editor, Random rand, IBlockFactory blocks){
		Coord s = new Coord(start);
		Coord e = new Coord(end);
		s.add(Cardinal.UP, 100);
		e.add(Cardinal.UP, 100);
		RectWireframe.fill(editor, rand, s, e, blocks);
	}
}
