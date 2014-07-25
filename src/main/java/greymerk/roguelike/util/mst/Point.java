package greymerk.roguelike.util.mst;

import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

public class Point {
	
	private Coord position;
	private Coord adjusted;
	private int rank;
	private Point parent;
	
	public Point(Coord pos, Random rand){
		this.position = pos;
		this.adjusted = new Coord(pos);
		this.adjusted.add(Cardinal.directions[rand.nextInt(Cardinal.directions.length)]);
		this.rank = 0;
		this.parent = this;
	}
	
	public double distance(Point other){
		double side1 = Math.abs(adjusted.getX() - other.adjusted.getX());
		double side2 = Math.abs(adjusted.getZ() - other.adjusted.getZ());
		
		return Math.sqrt((side1 * side1) + (side2 * side2));
	}
	
	public Coord getPosition(Coord absolute){
		Coord toReturn = new Coord(position);
		toReturn.add(absolute);
		return toReturn;
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
}
