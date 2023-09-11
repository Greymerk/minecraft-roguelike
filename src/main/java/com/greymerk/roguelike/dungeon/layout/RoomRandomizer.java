package com.greymerk.roguelike.dungeon.layout;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class RoomRandomizer {

	private List<WeightedRandomizer<Room>> rooms;
	
	public RoomRandomizer() {
		rooms = new ArrayList<WeightedRandomizer<Room>>();
		for(int i = 0; i < Dungeon.getLevelFromY(-50) + 1; ++i) {
			WeightedRandomizer<Room> floorRooms = new WeightedRandomizer<Room>();
			floorRooms.add(new WeightedChoice<Room>(Room.CORRIDOR, 20));
			rooms.add(floorRooms);
		}
		
		for(int i = 0; i < rooms.size(); ++i) {
			WeightedRandomizer<Room> thisLevel = rooms.get(i);
			if(i == 1) thisLevel.add(new WeightedChoice<Room>(Room.CROSS, 10));
			if(i == 1) thisLevel.add(new WeightedChoice<Room>(Room.BEDROOM, 5));
			if(i == 2) thisLevel.add(new WeightedChoice<Room>(Room.CROSS, 5));
			if(i == 2) thisLevel.add(new WeightedChoice<Room>(Room.CRYPT, 1));
			if(i == 3) thisLevel.add(new WeightedChoice<Room>(Room.CRYPT, 5));
			if(i == 3) thisLevel.add(new WeightedChoice<Room>(Room.OSSUARY, 1));
			if(i == 4) thisLevel.add(new WeightedChoice<Room>(Room.CRYPT, 8));
			if(i == 4) thisLevel.add(new WeightedChoice<Room>(Room.OSSUARY, 2));
			if(i == 5) thisLevel.add(new WeightedChoice<Room>(Room.CRYPT, 6));
			if(i == 5) thisLevel.add(new WeightedChoice<Room>(Room.OSSUARY, 3));
			if(i == 6) thisLevel.add(new WeightedChoice<Room>(Room.RESERVOIR, 2));
			if(i == 6) thisLevel.add(new WeightedChoice<Room>(Room.CISTERN, 7));
		}
	}
	
	public Room get(Random rand, int level) {
		if(level < 0 || level >= rooms.size()) return Room.CORRIDOR;
		return this.rooms.get(level).get(rand);
	}
	
}
