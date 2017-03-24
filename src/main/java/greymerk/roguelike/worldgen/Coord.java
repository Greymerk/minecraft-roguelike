package greymerk.roguelike.worldgen;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import net.minecraft.util.math.BlockPos;

public class Coord{

	private int x;
	private int y;
	private int z;
	
	public Coord(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coord(Coord toClone){
		this.x = toClone.x;
		this.y = toClone.y;
		this.z = toClone.z;
	}
	
	public Coord(BlockPos bp){
		this.x = bp.getX();
		this.y = bp.getY();
		this.z = bp.getZ();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public void add(Cardinal dir, int amount){
		
		switch(dir){
		case EAST: x += amount; return;
		case WEST: x -= amount; return;
		case UP: y += amount; return;
		case DOWN: y -= amount; return;
		case NORTH: z -= amount; return;
		case SOUTH: z += amount; return;

		}		
	}
	
	public Coord add(Coord other){
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	public Coord sub(Coord other){
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}
	
	public void add(Cardinal dir){
		add(dir, 1);
	}

	public double distance(Coord other){
		double side1 = Math.abs(this.getX() - other.getX());
		double side2 = Math.abs(this.getZ() - other.getZ());
		
		return Math.sqrt((side1 * side1) + (side2 * side2));
	}
	
	// Arranges two coords so that the they create a positive cube.
	// used in fill routines.
	public static void correct(Coord one, Coord two){
		
		int temp;
		
		if(two.x < one.x){
			temp = two.x;
			two.x = one.x;
			one.x = temp;
		}

		if(two.y < one.y){
			temp = two.y;
			two.y = one.y;
			one.y = temp;
		}
		
		if(two.z < one.z){
			temp = two.z;
			two.z = one.z;
			one.z = temp;
		}
	}
	
	@Override
	public int hashCode(){
		 return new HashCodeBuilder(17, 31)
		 .append(x)
		 .append(y)
		 .append(z)
		 .toHashCode();
	}
	
	@Override
	public String toString(){
		String toReturn = "";
		toReturn += "x: " + x + " ";
		toReturn += "y: " + y + " ";
		toReturn += "z: " + z;
		return toReturn;
	}
	
	@Override
	public boolean equals(Object o){
		Coord other = (Coord)o;
		
		if(x != other.x) return false;
		if(y != other.y) return false;
		if(z != other.z) return false;
		
		return true;
	}
	
	public BlockPos getBlockPos(){
		return new BlockPos(this.x, this.y, this.z);
	}
}
