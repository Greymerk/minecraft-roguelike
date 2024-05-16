package com.greymerk.roguelike.editor.shapes;

import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

public enum Shape {

	RECTSOLID, RECTHOLLOW, RECTPYRAMID, RECTWIREFRAME, SPHERE;
	
	public static IShape get(Shape type, BoundingBox bb){
		switch(type){
		case RECTSOLID: return new RectSolid(bb);
		case RECTHOLLOW: return new RectHollow(bb);
		case RECTPYRAMID: return new RectPyramid(bb);
		case RECTWIREFRAME: return new RectWireframe(bb);
		case SPHERE: return new Sphere(bb);
		default: return new RectSolid(bb);
		}
	}
}
