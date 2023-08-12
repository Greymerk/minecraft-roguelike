package com.greymerk.roguelike.dungeon.room;

import java.util.Arrays;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;

import net.minecraft.nbt.NbtCompound;

public enum Room {

	CORRIDOR, ENTRANCE, STAIRWAY;
	
	public static IRoom createFromNBT(NbtCompound tag) {
		Room type = get(tag.get("type").asString());
		ITheme theme = Theme.get(tag.getString("theme"));
		IBounded box = new BoundingBox(tag.getCompound("box"));
		Coord pos = new Coord(tag.getCompound("pos"));
		Cardinal dir = Arrays.asList(Cardinal.directions).get(tag.getInt("dir"));
		
		switch(type) {
		case CORRIDOR: return new Corridor(theme, box, pos);
		case ENTRANCE: return new EntranceRoom(theme, box, pos);
		case STAIRWAY: return new Stairway(theme, box, pos, dir);
		default: return new Corridor(theme, box, pos);
		}
	}
	
	public static Room get(String name) {
		for(Room type : Room.values()) {
			if(type.toString().equals(name)) return type;
		}
		return CORRIDOR;
	}
	
	public static boolean contains(String name) {
		for(Room type : Room.values()) {
			if(type.toString().equals(name)) return true;
		}
		return false;
	}
}
