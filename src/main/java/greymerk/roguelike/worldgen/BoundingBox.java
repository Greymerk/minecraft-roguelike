package greymerk.roguelike.worldgen;

public class BoundingBox {

	private Coord start;
	private Coord end;
	
	public BoundingBox(Coord start, Coord end){
		this.start = new Coord(start);
		this.end = new Coord(end);
		
		Coord.correct(this.start, this.end);
	}
	
	public boolean collide(BoundingBox other){
		
		if(end.getX() < other.start.getX()
			|| other.end.getX() < start.getX()) return false; 
		
		if(end.getY() < other.start.getY()
			|| other.end.getY() < start.getY()) return false; 
		
		if(end.getZ() < other.start.getZ()
			|| other.end.getZ() < start.getZ()) return false; 
		
		return true;
	}
}
