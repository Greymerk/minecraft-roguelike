package greymerk.roguelike.dungeon.base;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class DungeonFactory implements IDungeonFactory {
	
	private Map<DungeonRoom, Integer> singles;
	private Map<DungeonRoom, Integer> multiple;
	private DungeonRoom base;
	
	private Iterator<IDungeonRoom> singleRooms;
	
	
	
	public DungeonFactory(){
		this(DungeonRoom.CORNER);
	}
	
	public DungeonFactory(DungeonRoom base){
		singles = new HashMap<DungeonRoom, Integer>();
		multiple = new HashMap<DungeonRoom, Integer>();
		this.base = base;
	}
	
	public DungeonFactory(JsonArray json) throws Exception {
		this();
		
		for(JsonElement e : json){
			this.add(e.getAsJsonObject());
		}
	}
	
	public DungeonFactory(DungeonFactory toCopy){
		this();
		for(DungeonRoom room : toCopy.singles.keySet()){
			this.singles.put(room, toCopy.singles.get(room));
		}
		
		for(DungeonRoom room : toCopy.multiple.keySet()){
			this.multiple.put(room, toCopy.multiple.get(room));
		}
		
		base = toCopy.base;
	}

	public DungeonFactory(DungeonFactory base, DungeonFactory other){
		this();
		this.base = other.base;
		if(other.singles.keySet().isEmpty()){
			for(DungeonRoom room : base.singles.keySet()){
				this.singles.put(room, base.singles.get(room));
			}	
		} else {
			for(DungeonRoom room : other.singles.keySet()){
				this.singles.put(room, other.singles.get(room));
			}	
		}
		
		if(other.multiple.keySet().isEmpty()){
			for(DungeonRoom room : base.multiple.keySet()){
				this.multiple.put(room, base.multiple.get(room));
			}	
		} else {
			for(DungeonRoom room : other.multiple.keySet()){
				this.multiple.put(room, other.multiple.get(room));
			}	
		}
	}
	
	public void add(JsonObject entry) throws Exception{
		String mode = (entry.get("mode").getAsString()).toLowerCase();
		String type = (entry.get("type").getAsString()).toUpperCase();			
		
		int weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;
		
		if(!DungeonRoom.contains(type)){
			throw new Exception("No such dungeon: " + type);
		}
		
		DungeonRoom toAdd = DungeonRoom.valueOf(entry.get("type").getAsString());
		
		if(mode.equals("single")){
			this.addSingle(toAdd);
		}
		
		if(mode.equals("random")){
			this.addRandom(toAdd, weight);
		}
	}
	
	public IDungeonRoom get(Random rand){
		
		if(this.singleRooms == null) this.singleRooms = new RoomIterator();
		
		if(this.singleRooms.hasNext()) return this.singleRooms.next();
		
		Set<DungeonRoom> keyset = this.multiple.keySet();
		if(keyset.isEmpty()) return DungeonRoom.getInstance(base);
		
		WeightedRandomizer<DungeonRoom> randomizer = new WeightedRandomizer<DungeonRoom>();
		for(DungeonRoom room : keyset){
			randomizer.add(new WeightedChoice<DungeonRoom>(room, multiple.get(room)));
		}
		
		DungeonRoom choice = randomizer.get(rand);
		return DungeonRoom.getInstance(choice);
	}
	
	public void addSingle(DungeonRoom type){
		this.addSingle(type, 1);
	}
	
	public void addSingle(DungeonRoom type, int num){
		if(!singles.containsKey(type)){
			singles.put(type, num);
			return;
		}
		
		int count = singles.get(type);
		count += num;
		singles.put(type, count);
	}
	
	public void addRandom(DungeonRoom type, int weight){
		multiple.put(type, weight);
	}
	
	@Override
	public boolean equals(Object o){
		DungeonFactory other = (DungeonFactory)o;
		
		if(!this.base.equals(other.base)) return false;
		
		if(!this.singles.equals(other.singles)) return false;
		
		if(!this.multiple.equals(other.multiple)) return false;
		
		return true;
	}
	
	public static DungeonFactory getRandom(Random rand, int numRooms) {
		DungeonFactory rooms = new DungeonFactory();
		rooms.base = DungeonRoom.CORNER;
		for(int i = 0; i < numRooms; ++i){
			DungeonRoom type = DungeonRoom.getRandomRoom(rand);
			if(rand.nextBoolean()){
				rooms.addRandom(type, 1);	
			} else {
				rooms.addSingle(type, 1);
			}
		}
		return rooms;
	}
	
	private class RoomIterator implements Iterator<IDungeonRoom>{
		private PriorityQueue<IDungeonRoom> rooms;
		
		public RoomIterator(){
			
			rooms = new PriorityQueue<IDungeonRoom>();
			
			Set<DungeonRoom> keyset = singles.keySet();
			for(DungeonRoom type : keyset){
				for(int i = 0; i < singles.get(type); ++i){
					rooms.add(DungeonRoom.getInstance(type));
				}
			}
		}
		
		@Override
		public boolean hasNext() {
			return !rooms.isEmpty();
		}

		@Override
		public IDungeonRoom next() {
			return rooms.poll();
		}
	}
}
