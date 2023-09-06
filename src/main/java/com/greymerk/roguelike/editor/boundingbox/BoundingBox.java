package com.greymerk.roguelike.editor.boundingbox;

import java.util.Objects;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.nbt.NbtCompound;

public class BoundingBox implements IBounded{

	private Coord start;
	private Coord end;
	
	public BoundingBox(Coord origin) {
		this.start = origin.copy();
		this.end = origin.copy();
	}
	
	public BoundingBox(Coord start, Coord end){
		this.start = start.copy();
		this.end = end.copy();
		Coord.correct(this.start, this.end);
	}
	
	public BoundingBox(NbtCompound tag) {
		this.start = new Coord(tag.getCompound("start"));
		this.end = new Coord(tag.getCompound("end"));
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

	@Override
	public IShape getShape(Shape type) {
		return Shape.get(type, start, end);
	}

	public void grow(Cardinal dir) {
		this.grow(dir, 1);
	}
	
	public void grow(Cardinal dir, int amount) {
		switch(dir) {
		case DOWN: 
		case NORTH:
		case WEST: start.add(dir, amount); break;
		case UP:
		case EAST:
		case SOUTH: end.add(dir, amount); break;
		default:
		}
		Coord.correct(start, end);
	}
	
	public void grow(Iterable<Cardinal> dirs) {
		this.grow(dirs, 1);
	}
	
	public void grow(Iterable<Cardinal> dirs, int amount) {
		for(Cardinal dir : dirs) {
			this.grow(dir, amount);
		}
	}
	
	public void add(Cardinal dir) {
		this.add(dir, 1);
	}
	
	public void add(Cardinal dir, int amount) {
		this.start.add(dir, amount);
		this.end.add(dir, amount);
	}
	
	@Override
	public Coord getStart() {
		return new Coord(start);
	}

	@Override
	public Coord getEnd() {
		return new Coord(end);
	}

	@Override
	public NbtCompound getNbt() {
		NbtCompound nbt = new NbtCompound();
		nbt.put("start", this.start.getNbt());
		nbt.put("end", this.end.getNbt());
		return nbt;
	}

	@Override
	public boolean contains(Coord pos) {
		if(pos.getX() < this.start.getX() || pos.getX() > this.end.getX()) return false;
		if(pos.getY() < this.start.getY() || pos.getY() > this.end.getY()) return false;
		if(pos.getZ() < this.start.getZ() || pos.getZ() > this.end.getZ()) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(end, start);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoundingBox other = (BoundingBox) obj;
		return Objects.equals(end, other.end) && Objects.equals(start, other.start);
	}

}
