package greymerk.roguelike.catacomb.dungeon;


import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DungeonFactory implements IDungeonFactory {

	private List<Dungeon> singles;
	private WeightedRandomizer<Dungeon> multiple;

	public DungeonFactory(){
		singles = new ArrayList<Dungeon>();
		multiple = new WeightedRandomizer<Dungeon>();
	}
	
	public DungeonFactory(JsonArray json) {
		this();
		
		for(JsonElement e : json){
			JsonObject entry = e.getAsJsonObject();
			String mode = entry.get("mode").getAsString();
			if(mode.equals("single")){
				this.addSingle(Dungeon.valueOf(entry.get("type").getAsString()));
			}
			
			if(mode.equals("random")){
				this.addRandom(Dungeon.valueOf(entry.get("type").getAsString()), entry.get("weight").getAsInt());
			}
		}
	}
	
	public DungeonFactory(DungeonFactory toCopy){
		singles = new ArrayList<Dungeon>();
		singles.addAll(toCopy.singles);
		
		multiple = new WeightedRandomizer<Dungeon>(toCopy.multiple);
	}

	public void addSingle(Dungeon type){
		singles.add(type);
	}
	
	public void addByRatio(Dungeon toAdd, int rate){
		if(rate <= 0) return;
		int max = RogueConfig.getInt(RogueConfig.LEVELMAXROOMS);
		int numRooms = max / rate;
		
		if (numRooms == 0){
			addSingle(toAdd);
			return;
		}
		
		for(int i = 0; i < numRooms; ++i){
			addSingle(toAdd);
		}
	}
	
	public void addRandom(Dungeon type, int weight){
		multiple.add(new WeightedChoice<Dungeon>(type, weight));
	}
	
	@Override
	public IDungeon get(Random rand) {
		if(!singles.isEmpty()) return Dungeon.getInstance(singles.remove(0));
		if(!multiple.isEmpty()) return Dungeon.getInstance(multiple.get(rand));
		return Dungeon.getInstance(Dungeon.CORNER);
	}
}
