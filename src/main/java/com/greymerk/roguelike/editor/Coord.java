package com.greymerk.roguelike.editor;

import java.util.Objects;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.util.math.BlockPos;

public class Coord {
	private int x;
	private int y;
	private int z;
	
	public Coord(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coord(Coord other) {
		this.x = other.getX();
		this.y = other.getY();
		this.z = other.getZ();
	}
	
	public Coord(NbtCompound tag){
		this.x = tag.getInt("x");
		this.y = tag.getInt("y");
		this.z = tag.getInt("z");
	}
	
	public Coord(BlockPos bp){
		this.x = bp.getX();
		this.y = bp.getY();
		this.z = bp.getZ();
	}
	
	public Coord copy() {
		return new Coord(this);
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
	
	public Coord add(Cardinal dir, int amount){
		switch(dir){
		case EAST: x += amount; return this;
		case WEST: x -= amount; return this;
		case UP: y += amount; return this;
		case DOWN: y -= amount; return this;
		case NORTH: z -= amount; return this;
		case SOUTH: z += amount; return this;
		}
		return this;
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
	
	public Coord add(Cardinal dir){
		add(dir, 1);
		return this;
	}
	
	public Coord mul(Coord other) {
		return new Coord(x * other.x, y * other.y, z * other.z);
	}
	
	public Coord unit() {
		return new Coord(
			x == 0 ? 0 : x / Math.abs(x),
			y == 0 ? 0 : y / Math.abs(y),
			z == 0 ? 0 : z / Math.abs(z));
	}

	public double distance(Coord other){
		double side1 = Math.abs(this.getX() - other.getX());
		double side2 = Math.abs(this.getZ() - other.getZ());
		
		return Math.sqrt((side1 * side1) + (side2 * side2));
	}
	
	public Cardinal dirTo(Coord other){
		int xdiff = other.x - x;
		int ydiff = other.y - y;
		int zdiff = other.z - z;
		
		if(
			Math.abs(ydiff) > Math.abs(xdiff)
			&& Math.abs(ydiff) > Math.abs(zdiff)){
				return Cardinal.UP;		
		}
		
		
		if (Math.abs(xdiff) < Math.abs(zdiff)){
			if(zdiff < 0){
				return Cardinal.NORTH;
			} else {
				return Cardinal.SOUTH;
			}
		} else {
			if(xdiff < 0){
				return Cardinal.WEST;
			} else {
				return Cardinal.EAST;
			}
		}
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
			return Objects.hash(x, y, z);
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

	public NbtElement getNbt() {
		NbtCompound nbt = new NbtCompound();
		nbt.put("x", NbtInt.of(x));
		nbt.put("y", NbtInt.of(y));
		nbt.put("z", NbtInt.of(z));
		return nbt;
	}
}
