package com.greymerk.roguelike.dungeon;

import java.util.Objects;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class DungeonLocation{

	private RegistryKey<World> worldKey;
	private ChunkPos cpos;
	
	public static DungeonLocation of(RegistryKey<World> worldKey, ChunkPos cpos) {
		return new DungeonLocation(worldKey, cpos);
	}
	
	public static DungeonLocation of(NbtCompound data) {
		
		String namespace = data.getCompound("worldkey").getString("namespace");
		String path = data.getCompound("worldkey").getString("path");
		Identifier id = Identifier.of(namespace, path);
		RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, id);
		
		ChunkPos cpos = new ChunkPos(
			data.getCompound("chunkpos").getInt("x"),
			data.getCompound("chunkpos").getInt("z"));
		
		return new DungeonLocation(key, cpos);
	}
	
	private DungeonLocation(RegistryKey<World> worldKey, ChunkPos cpos) {
		this.worldKey = worldKey;
		this.cpos = cpos;
	}
	
	public RegistryKey<World> getKey(){
		return this.worldKey;
	}
	
	public ChunkPos getChunkPos() {
		return this.cpos;
	}
	
	@Override
	public int hashCode() {
		Identifier id = worldKey.getValue();
		return Objects.hash(cpos, id.hashCode());
	}

	public NbtCompound getNbt() {
		NbtCompound data = new NbtCompound();
		Identifier id = this.worldKey.getValue();
		
		NbtCompound wk = new NbtCompound();
		wk.put("namespace", NbtString.of(id.getNamespace()));
		wk.put("path", NbtString.of(id.getPath()));
		data.put("worldkey", wk);
		
		NbtCompound cp = new NbtCompound();
		cp.put("x", NbtInt.of(cpos.x));
		cp.put("z", NbtInt.of(cpos.z));
		data.put("chunkpos", cp);
		
		return data;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DungeonLocation other = (DungeonLocation) obj;
		return Objects.equals(cpos, other.cpos) && Objects.equals(worldKey, other.worldKey);
	}
	
	@Override
	public String toString() {
		return this.worldKey.getValue() + " : " + this.cpos;
	}

}
