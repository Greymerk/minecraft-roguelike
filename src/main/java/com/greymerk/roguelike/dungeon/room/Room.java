package com.greymerk.roguelike.dungeon.room;

import java.util.Arrays;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;

import net.minecraft.nbt.NbtCompound;

public enum Room {

	CORRIDOR, ENTRANCE, STAIRWAY, CROSS, BEDROOM, HALL, CRYPT,
	RESERVOIR, CISTERN, OSSUARY, KITCHEN, CREEPER, ENDER;
	
	public static IRoom fromType(Room type) {
		switch(type) {
		case CORRIDOR: return new Corridor();
		case ENTRANCE: return new EntranceRoom();
		case STAIRWAY: return new Stairway();
		case CROSS: return new CrossRoom();
		case BEDROOM: return new BedRoom();
		case HALL: return new GreatHallRoom();
		case CRYPT: return new CryptRoom();
		case RESERVOIR: return new ReservoirRoom();
		case CISTERN: return new CisternRoom();
		case OSSUARY: return new OssuaryRoom();
		case KITCHEN: return new KitchenRoom();
		case CREEPER: return new CreeperRoom();
		case ENDER: return new EnderRoom();
		default: return new Corridor();
		}
	}
	
	public static IRoom createFromNBT(NbtCompound tag) {
		Room type = get(tag.get("type").asString());
		ILevelSettings settings = LevelSettings.get(tag.getString("settings"));
		IBounded box = new BoundingBox(tag.getCompound("box"));
		Coord pos = new Coord(tag.getCompound("pos"));
		int dirValue = tag.getInt("dir");
		Cardinal dir = Arrays.asList(Cardinal.values()).get(dirValue);
		return getInstance(type, settings, box, pos, dir);
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
	
	public static IRoom getInstance(Room type, ILevelSettings settings) {
		IRoom room = fromType(type);
		room.setLevelSettings(settings);
		return room;
	}
	
	public static IRoom getInstance(Room type, ILevelSettings settings, Coord floorPos, Coord worldPos) {
		IRoom room = fromType(type);
		room.setLevelSettings(settings);
		room.setFloorPos(floorPos);
		room.setWorldPos(worldPos);
		return room;
	}
	
	public static IRoom getInstance(Room type, ILevelSettings settings, Coord floorPos, Coord worldPos, Cardinal dir) {
		IRoom room = getInstance(type, settings, floorPos, worldPos);
		room.setDirection(dir);
		return room;
	}
	
	public static IRoom getInstance(Room type, ILevelSettings settings, IBounded box, Coord pos, Cardinal dir) {
		IRoom room = fromType(type);
		room.setLevelSettings(settings);
		room.setWorldPos(pos);
		room.setDirection(dir);
		return room;
	}
}
