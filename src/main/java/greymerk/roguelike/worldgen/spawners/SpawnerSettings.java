package greymerk.roguelike.worldgen.spawners;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class SpawnerSettings {

	private Map<Spawner, IWeighted<Spawnable>> spawners;
	
	public SpawnerSettings(JsonObject data){
		this.spawners = new HashMap<Spawner, IWeighted<Spawnable>>();
		
		for(Spawner type : Spawner.values()){
			if(data.has(type.name())){
				JsonArray categoryData = data.get(type.name()).getAsJsonArray();
				WeightedRandomizer<Spawnable> spawns = new WeightedRandomizer<Spawnable>();
				this.spawners.put(type, spawns);
				for(JsonElement spawnData : categoryData){
					JsonObject spawnObj = spawnData.getAsJsonObject();
					int weight = spawnObj.has("weight") ? spawnObj.get("weight").getAsInt() : 1;
					spawns.add(new WeightedChoice<Spawnable>(new Spawnable(spawnObj), weight));
				}
			}
		}
	}
	
	public SpawnerSettings(SpawnerSettings toCopy){
		this.spawners = new HashMap<Spawner, IWeighted<Spawnable>>();
		if(toCopy == null) return;
		this.spawners.putAll(toCopy.spawners);
	}
	
	public SpawnerSettings(SpawnerSettings base, SpawnerSettings override){
		this.spawners = new HashMap<Spawner, IWeighted<Spawnable>>();
		
		for(Spawner type : Spawner.values()){
			if(override != null && override.spawners.containsKey(type)){
				this.spawners.put(type, override.spawners.get(type));
			} else if(base != null && base.spawners.containsKey(type)){
				this.spawners.put(type, base.spawners.get(type));
			}
		}
	}
	
	public void generate(IWorldEditor editor, Random rand, Coord cursor, Spawner type, int level){
		
		Spawnable toSpawn;
		
		if(spawners.containsKey(type)){
			toSpawn = spawners.get(type).get(rand);
		} else {
			toSpawn = new Spawnable(type);
		}
		
		toSpawn.generate(editor, rand, cursor, level);
		
	}
	
}