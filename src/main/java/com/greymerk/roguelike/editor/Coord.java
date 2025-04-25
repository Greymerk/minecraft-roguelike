package com.greymerk.roguelike.editor;

import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

/**
Mutable Coordinate 3DVector
**/
public class Coord implements Comparable<Coord> {
	
	public static final Codec<Coord> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Codec.INT.fieldOf("x").forGetter(c -> c.x),
			Codec.INT.fieldOf("y").forGetter(c -> c.y),
			Codec.INT.fieldOf("z").forGetter(c -> c.z)
		).apply(instance, Coord::new)
	);
	
	public static final Coord ZERO = Coord.of(0,0,0).freeze();
	
	boolean frozen;
	
	private int x;
	private int y;
	private int z;
	
	public static Coord of(BlockPos bp){
		return Coord.of(bp.getX(), bp.getY(), bp.getZ());
	}
	
	public static Coord of(ChunkPos cpos) {
		return Coord.of(cpos.x << 4, 0, cpos.z << 4);
	}
	
	public static Coord of(NbtCompound tag) {
		int x = tag.getInt("x").get();
		int y = tag.getInt("y").get();
		int z = tag.getInt("z").get();
		return Coord.of(x, y, z);
	}
	
	public static Coord of(int x, int y, int z) {
		return new Coord(x, y, z);
	}
	
	private Coord(int x, int y, int z){
		this.frozen = false;
		this.x = x;
		this.y = y;
		this.z = z;
	}
		
	public Coord copy() {
		return Coord.of(x, y ,z);
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
	
	public Coord withX(int x) {
		return Coord.of(x, this.y, this.z);
	}
	
	public Coord withY(int y) {
		return Coord.of(this.x, y, this.z);
	}
	
	public Coord withZ(int z) {
		return Coord.of(this.x, this.y, z);
	}

	public Coord add(Cardinal dir){
		return add(dir, 1);
	}
	
	public Coord add(Cardinal dir, int amount){
		switch(dir){
		case EAST: if(this.frozen) {return Coord.of(x + amount, y, z);} else {this.x += amount; return this;}
		case WEST: if(this.frozen) {return Coord.of(x - amount, y, z);} else {this.x -= amount; return this;}
		case UP: if(this.frozen) {return Coord.of(x, y + amount, z);} else {this.y += amount; return this;}
		case DOWN: if(this.frozen) {return Coord.of(x, y - amount, z);} else {this.y -= amount; return this;}
		case NORTH: if(this.frozen) {return Coord.of(x, y, z - amount);} else {this.z -= amount; return this;}
		case SOUTH: if(this.frozen) {return Coord.of(x, y, z + amount);} else {this.z += amount; return this;}
		}
		return this.frozen ? this.copy() : this;
	}
	
	public Coord add(Coord other){
		if(this.frozen) {
			return Coord.of(
					x + other.x,
					y + other.y,
					z + other.z);
		} else {
			x += other.x;
			y += other.y;
			z += other.z;
			return this;
		}
	}
	
	public Coord sub(Coord other){
		if(this.frozen) {
			return Coord.of(
					x - other.x,
					y - other.y,
					z - other.z);
		} else {
			x -= other.x;
			y -= other.y;
			z -= other.z;
			return this;	
		}
	}
	
	public Coord mul(Coord other) {
		return Coord.of(x * other.x, y * other.y, z * other.z);
	}
	
	public int dot(Coord other) {
		int xp = this.x * other.x;
		int yp = this.y * other.y;
		int zp = this.z * other.z;
		return xp + yp + zp;
	}
	
	public Coord project(Coord onto) {
		double dot = (double)this.copy().dot(onto);
		double mag = onto.magnitude();
		double proj = dot / (Math.pow(mag, 2));
		double px = proj * onto.x;
		double py = proj * onto.y;
		double pz = proj * onto.z;
		return Coord.of(
					(int)Math.floor(px), 
					(int)Math.floor(py),
					(int)Math.floor(pz));
	}
	
	public double scalar(Coord onto) {
		int dot = this.copy().dot(onto);
		double mag = onto.magnitude();
		return (double)dot / mag;
	}
	
	public double magnitude() {
		double xS = Math.pow((double)this.x, 2);
		double yS = Math.pow((double)this.y, 2);
		double zS = Math.pow((double)this.z, 2);
		double sumOfSquares = xS + yS + zS;
		return Math.sqrt(sumOfSquares);
	}
	
	public Coord unit() {
		return Coord.of(
			x == 0 ? 0 : x / Math.abs(x),
			y == 0 ? 0 : y / Math.abs(y),
			z == 0 ? 0 : z / Math.abs(z));
	}

	public double distance(Coord other){
		double side1 = Math.abs(this.getX() - other.getX());
		double side2 = Math.abs(this.getZ() - other.getZ());
		
		return Math.sqrt((side1 * side1) + (side2 * side2));
	}
	
	public int manhattanDistance(Coord other) {
		int xdiff = Math.abs(this.x - other.x);
		int ydiff = Math.abs(this.y - other.y);
		int zdiff = Math.abs(this.z - other.z);
		return xdiff + ydiff + zdiff;
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
	
	
	/**
	 * Protects this Coord object from further mutation.
	 * Any operations altering the position of this Coord
	 * will return a copy.
	 */
	public Coord freeze() {
		this.frozen = true;
		return this;
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

	public ChunkPos getChunkPos() {
		return new ChunkPos(this.x >> 4, this.z >> 4);
	}

	@Override
	public int compareTo(Coord other) {
		if(this.x != other.x) return this.x - other.x;
		if(this.y != other.y) return this.y - other.y;
		if(this.z != other.z) return this.z - other.z;
		return 0;
	}
}
