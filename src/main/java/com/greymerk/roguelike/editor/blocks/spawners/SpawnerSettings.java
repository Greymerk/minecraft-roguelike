package com.greymerk.roguelike.editor.blocks.spawners;

import java.util.HashMap;
import java.util.Map;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class SpawnerSettings {

	private Map<Spawner, WeightedRandomizer<Spawnable>> spawners;
	
	public SpawnerSettings(){
		this.spawners = new HashMap<Spawner, WeightedRandomizer<Spawnable>>();
	}
	
	public SpawnerSettings(SpawnerSettings toCopy){
		this();
		for(Spawner type : toCopy.spawners.keySet()){
			if(this.spawners.get(type) == null){
				this.spawners.put(type, new WeightedRandomizer<Spawnable>());
			}
			this.spawners.get(type).merge(toCopy.spawners.get(type));
		}
	}
	
	public SpawnerSettings(SpawnerSettings base, SpawnerSettings other){
		this();

		for(Spawner type : base.spawners.keySet()){
			if(this.spawners.get(type) == null){
				this.spawners.put(type, new WeightedRandomizer<Spawnable>());
			}
			this.spawners.get(type).merge(base.spawners.get(type));
		}
		
		for(Spawner type : other.spawners.keySet()){
			if(this.spawners.get(type) == null){
				this.spawners.put(type, new WeightedRandomizer<Spawnable>());
			}
			this.spawners.get(type).merge(other.spawners.get(type));
		}
	}
	


	public void generate(IWorldEditor editor, Random rand, Coord cursor, Spawner type, int level){
		Spawnable toSpawn = spawners.containsKey(type) ? spawners.get(type).get(rand) : new Spawnable(type);
		toSpawn.generate(editor, rand, cursor, level);
	}
	
	@Override
	public String toString(){
		return this.spawners.keySet().toString();
	}
}
