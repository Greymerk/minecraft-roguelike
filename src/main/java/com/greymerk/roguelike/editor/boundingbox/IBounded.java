package com.greymerk.roguelike.editor.boundingbox;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.nbt.NbtCompound;

public interface IBounded {
	
	public BoundingBox getBoundingBox();
	
	public boolean collide(IBounded other);

	public boolean contains(Coord pos);
	
	public IShape getShape(Shape type);
	
	public Coord getStart();
	
	public Coord getEnd();

	public NbtCompound getNbt();
	
}
