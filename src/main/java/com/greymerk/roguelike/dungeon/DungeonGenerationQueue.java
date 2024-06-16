package com.greymerk.roguelike.dungeon;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class DungeonGenerationQueue implements Iterable<DungeonLocation>{

	private static DungeonGenerationQueue instance;
	
	private Set<DungeonLocation> locations;
	
	public static DungeonGenerationQueue getInstance() {
		if(instance == null) {
			instance = new DungeonGenerationQueue();
		}
		
		return instance;
	}
	
	private DungeonGenerationQueue() {
		this.locations = new HashSet<DungeonLocation>();
	}
	
	public void add(RegistryKey<World> key, ChunkPos cpos) {
		this.locations.add(DungeonLocation.of(key, cpos));
	}
	
	public boolean has(RegistryKey<World> key, ChunkPos cpos) {
		DungeonLocation dl = DungeonLocation.of(key, cpos);
		return this.locations.contains(dl);
	}
	
	public void remove(DungeonLocation dl) {
		this.locations.remove(dl);
	}

	@Override
	public Iterator<DungeonLocation> iterator() {
		return this.locations.iterator();
	}
}
