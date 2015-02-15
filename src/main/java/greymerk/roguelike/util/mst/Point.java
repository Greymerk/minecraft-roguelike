package greymerk.roguelike.util.mst;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.Random;

public class Point {
	
	private Coord position;
	private Coord adjusted;
	private int rank;
	private Point parent;
	
	public Point(Coord pos, Random rand){
		this.position = new Coord(pos);
		this.adjusted = new Coord(pos);
		this.adjusted.add(Cardinal.directions[rand.nextInt(Cardinal.directions.length)]);
		
		this.rank = 0;
		this.parent = this;
	}
	
	public double distance(Point other){
		return adjusted.distance(other.adjusted);
	}
	
	public Coord getPosition(){
		return new Coord(position);
	}
	
	public int getRank(){
		return rank;
	}
	
	public void incRank(){
		++rank;
	}
	
	public void setParent(Point p){
		this.parent = p;
	}
	
	public Point getParent(){
		return this.parent;
	}
	
	public void scaleBy(double multiplier){
		double x = (double)this.position.getX();
		double y = (double)this.position.getY();
		double z = (double)this.position.getZ();
		
		x *= multiplier;
		z *= multiplier;
		
		x = Math.floor(x);
		z = Math.floor(z);
		
		this.position = new Coord((int)x, (int)y, (int)z);
	}
}
