package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.util.math.random.Random;

public class RoomProvider {

	List<Room> addOnce;
	List<Room> addAfter;
	Map<Room, Double> addSometimes;
	WeightedRandomizer<Room> random;
	
	public RoomProvider() {
		this.addOnce = new ArrayList<Room>();
		this.addAfter = new ArrayList<Room>();
		this.addSometimes = new HashMap<Room, Double>();
		this.random = new WeightedRandomizer<Room>();
	}
	
	public void addRoomOnce(Room room) {
		this.addOnce.add(room);
	}
	
	public void addRoomAfter(Room room) {
		this.addAfter.add(room);
	}
	
	public void addRoomSometimes(Room room, Double probability) {
		this.addSometimes.put(room, probability);
	}
	
	public void addRandomChoice(Room room, int weight) {
		WeightedChoice<Room> choice = new WeightedChoice<Room>(room, weight);
		this.random.add(choice);
	}
	
	public List<Room> getRooms(Random rand, int count){
		List<Room> rooms = new ArrayList<Room>();
		this.addOnce.forEach(room -> rooms.add(room));
		if(rooms.size() >= count) return rooms;
		for(int i = 0; i < count - rooms.size(); ++i) {
			rooms.add(random.get(rand));
		}
		this.addSometimes.forEach((room, probability) -> {
			if(rand.nextDouble() < probability) rooms.add(room);
		});
		RandHelper.shuffle(rooms, rand);
		this.addAfter.forEach(room -> rooms.add(room));
		return rooms;
	}
	
	public Room get(Random rand) {
		return this.random.get(rand);
	}
}
