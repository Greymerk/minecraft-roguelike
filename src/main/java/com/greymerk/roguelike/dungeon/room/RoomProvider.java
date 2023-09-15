package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class RoomProvider {

	List<Room> before;
	List<Room> after;
	WeightedRandomizer<Room> random;
	
	public RoomProvider() {
		this.before = new ArrayList<Room>();
		this.after = new ArrayList<Room>();
		this.random = new WeightedRandomizer<Room>();
	}
	
	public void addRoomBefore(Room room) {
		this.before.add(room);
	}
	
	public List<Room> getRoomsBefore(){
		return this.before;
	}
	
	public void addRoomAfter(Room room) {
		this.after.add(room);
	}
	
	
	public List<Room> getRoomsAfter(){
		return this.after;
	}
	
	public void addRandomChoice(Room room, int weight) {
		WeightedChoice<Room> choice = new WeightedChoice<Room>(room, weight);
		this.random.add(choice);
	}
	
	public Room get(Random rand) {
		return this.random.get(rand);
	}
}
