package com.greymerk.roguelike.editor.boundingbox;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class BoundingBox implements IBounded{

	private Coord start;
	private Coord end;
	
	public BoundingBox(Coord start, Coord end){
		this.start = new Coord(start);
		this.end = new Coord(end);
		
		Coord.correct(this.start, this.end);
	}
	
	public BoundingBox(NbtCompound tag) {
		this.start = new Coord(tag.getCompound("start"));
		this.end = new Coord(tag.getCompound("end"));
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

	@Override
	public Coord getStart() {
		return new Coord(start);
	}

	@Override
	public Coord getEnd() {
		return new Coord(end);
	}

	@Override
	public NbtElement getNbt() {
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
}
