package com.greymerk.roguelike.editor.blocks.stair;

import com.greymerk.roguelike.editor.Cardinal;

public class Stair{

	public static MetaStair of(StairType type){
		return get(type, Cardinal.EAST, false);
	}
	
	public static MetaStair get(StairType type, Cardinal dir, boolean upsideDown){
		MetaStair stair = new MetaStair(type);
		stair.setOrientation(dir, upsideDown);
		return stair;
	}
	
}
