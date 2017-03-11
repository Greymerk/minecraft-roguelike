package greymerk.roguelike.dungeon.base;


import java.util.HashMap;
import java.util.Map;
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
			JsonObject entry = e.getAsJsonObject();
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
		for(DungeonRoom room : base.singles.keySet()){
			this.singles.put(room, base.singles.get(room));
		}
		
		for(DungeonRoom room : base.multiple.keySet()){
			this.multiple.put(room, base.multiple.get(room));
		}
		
		for(DungeonRoom room : other.singles.keySet()){
			this.singles.put(room, other.singles.get(room));
		}
		
		for(DungeonRoom room : other.multiple.keySet()){
			this.multiple.put(room, other.multiple.get(room));
		}
		
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
	public IDungeonRoom get(Random rand) {
		if(!singles.isEmpty()) return this.getSingle(rand);
		if(!multiple.isEmpty()) return this.getRandom(rand);
		return DungeonRoom.getInstance(base);
	}
	
	private IDungeonRoom getRandom(Random rand){
		Set<DungeonRoom> keyset = this.multiple.keySet();
		if(keyset.isEmpty()) return null;
		
		WeightedRandomizer<DungeonRoom> randomizer = new WeightedRandomizer<DungeonRoom>();
		for(DungeonRoom room : keyset){
			randomizer.add(new WeightedChoice<DungeonRoom>(room, multiple.get(room)));
		}
		
		DungeonRoom choice = randomizer.get(rand);
		return DungeonRoom.getInstance(choice);
	}
	
	private IDungeonRoom getSingle(Random rand){
		Set<DungeonRoom> keyset = this.singles.keySet();
		if(keyset.isEmpty()) return null;
		
		DungeonRoom[] rooms = keyset.toArray(new DungeonRoom[0]);
		DungeonRoom type = rooms[rand.nextInt(rooms.length)];
		
		if(this.singles.get(type) <= 1){
			this.singles.remove(type);
		} else {
			this.singles.put(type, this.singles.get(type) - 1);	
		}
		
		return DungeonRoom.getInstance(type);
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
}
