package greymerk.roguelike.worldgen.spawners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonObject;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;


public class SpawnerSettings {

	private Map<Spawner, Spawnable> spawners;
	
	public SpawnerSettings(){
		this.spawners = new HashMap<Spawner, Spawnable>();
	}
	
	public SpawnerSettings(JsonObject data){
		
	}
	
	public void add(JsonObject entry){
		if(!entry.has("type")) return;
		
		String typeName = entry.get("type").getAsString().toUpperCase();
		if(!Arrays.asList(Spawner.values()).contains(typeName)) return;
		
		Spawner type = Spawner.valueOf(typeName);
		
		JsonObject spawns = entry.get("spawns").getAsJsonObject();
		
		this.spawners.put(type, new Spawnable(spawns));
	}
	
	public SpawnerSettings(SpawnerSettings toCopy){
		this.spawners = new HashMap<Spawner, Spawnable>();
		if(toCopy == null) return;
		this.spawners.putAll(toCopy.spawners);
	}
	
	public SpawnerSettings(SpawnerSettings base, SpawnerSettings override){
		this.spawners = new HashMap<Spawner, Spawnable>();
		
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
			toSpawn = spawners.get(type);
		} else {
			toSpawn = new Spawnable(type);
		}
		
		toSpawn.generate(editor, rand, cursor, level);
		
	}
	
}
