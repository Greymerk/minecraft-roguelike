package com.greymerk.roguelike.editor.boundingbox;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;

public class BoundingBox implements IBounded, IShape{

	private Coord start;
	private Coord end;
	
	public static BoundingBox of(Coord origin) {
		return new BoundingBox(origin);
	}
	
	public static BoundingBox of(Coord start, Coord end) {
		return new BoundingBox(start, end);
	}
	
	public BoundingBox copy() {
		return new BoundingBox(start, end);
	}
	
	private BoundingBox(Coord origin) {
		this.start = origin.copy();
		this.end = origin.copy();
	}
	
	private BoundingBox(Coord start, Coord end){
		this.start = start.copy();
		this.end = end.copy();
		this.correct();
	}
	
	public BoundingBox(NbtCompound tag) {
		this.start = Coord.of(tag.getCompound("start"));
		this.end = Coord.of(tag.getCompound("end"));
		this.correct();
	}

	public BoundingBox getBoundingBox(){
		return this;
	}
	
	public BoundingBox combine(IBounded other) {
		BoundingBox starts = new BoundingBox(start, other.getStart());
		BoundingBox ends = new BoundingBox(end, other.getEnd());
		this.start = starts.start;
		this.end = ends.end;
		this.correct();
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
		return Shape.get(type, this);
	}

	public BoundingBox grow(Cardinal dir) {
		return this.grow(dir, 1);
	}
	
	public BoundingBox grow(Cardinal dir, int amount) {
		switch(dir) {
		case DOWN: 
		case NORTH:
		case WEST: start.add(dir, amount); break;
		case UP:
		case EAST:
		case SOUTH: end.add(dir, amount); break;
		default:
		}
		this.correct();
		return this;
	}
	
	public BoundingBox grow(Iterable<Cardinal> dirs) {
		return this.grow(dirs, 1);
		
	}
	
	public BoundingBox grow(Iterable<Cardinal> dirs, int amount) {
		for(Cardinal dir : dirs) {
			this.grow(dir, amount);
		}
		return this;
	}
	
	public BoundingBox add(Cardinal dir) {
		return this.add(dir, 1);
	}
	
	public BoundingBox add(Cardinal dir, int amount) {
		this.start.add(dir, amount);
		this.end.add(dir, amount);
		return this;
	}

	public BoundingBox add(Coord pos) {
		this.start.add(pos);
		this.end.add(pos);
		return this;
	}
	
	@Override
	public Coord getStart() {
		return start.copy();
	}

	@Override
	public Coord getEnd() {
		return end.copy();
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
	
	private void correct() {
		Coord s = new Coord(
				end.getX() < start.getX() ? end.getX() : start.getX(),
				end.getY() < start.getY() ? end.getY() : start.getY(),
				end.getZ() < start.getZ() ? end.getZ() : start.getZ()
				);
		
		Coord e = new Coord(
				end.getX() < start.getX() ? start.getX() : end.getX(),
				end.getY() < start.getY() ? start.getY() : end.getY(),
				end.getZ() < start.getZ() ? start.getZ() : end.getZ()
				);
		
		this.start = s;
		this.end = e;
	}

	@Override
	public Iterator<Coord> iterator() {
		return this.getShape(Shape.RECTSOLID).iterator();
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, @NotNull IBlockFactory block) {
		this.getShape(Shape.RECTSOLID).fill(editor, rand, block);
		
	}

	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
		this.getShape(Shape.RECTSOLID).fill(editor, rand, block, fillAir, replaceSolid);
		
	}

	@Override
	public List<Coord> get() {
		return this.getShape(Shape.RECTSOLID).get();
	}
	
	@Override
	public String toString() {
		return List.of(start, end).toString();
	}
}
