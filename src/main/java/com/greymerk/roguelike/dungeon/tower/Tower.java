package com.greymerk.roguelike.dungeon.tower;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.theme.Theme;

public enum Tower {

	ROGUE;
	
	public static ITower get(Tower type){
		
		switch(type){
		case ROGUE: return new RogueTower();
		default: return new RogueTower();
		}
	}
	
	public static Theme getDefaultTheme(Tower type){
		return Theme.TOWER;
	}
	
	public static Coord getBaseCoord(IWorldEditor editor, Coord pos){
		
		Coord cursor = new Coord(pos.getX(), 128, pos.getZ());

		while(cursor.getY() > 60){
			if(editor.isGround(cursor)) break;
			cursor.add(Cardinal.DOWN);
		}

		cursor.add(Cardinal.UP);
		
		
		
		int yOffset = cursor.getY() - pos.getY();

		if(yOffset < 14){
			yOffset = 14;
		}
		
		return new Coord(pos.getX(), pos.getY() + yOffset, pos.getZ());
	}

	public static Tower get(String name) throws Exception{
		if(!contains(name.toUpperCase())){
			throw new Exception("No such tower type: " + name);
		}
		
		return valueOf(name.toUpperCase());
	}
	
	public static boolean contains(String name){
		for(Tower value : Tower.values()){
			if(value.toString().equals(name)) return true;
		}
		return false;
	}

	public static Tower getRandom(Random rand) {
		return Tower.values()[rand.nextInt(Tower.values().length)];
	}
}
