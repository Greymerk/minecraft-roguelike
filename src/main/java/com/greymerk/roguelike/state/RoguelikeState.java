package com.greymerk.roguelike.state;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.StreamSupport;

import com.google.gson.JsonElement;
import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.debug.Debug;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.Statistics;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

public class RoguelikeState extends PersistentState {
	
	public static final Codec<List<Dungeon>> DUNGEON_LIST_CODEC = Codec.list(Dungeon.CODEC);
	
	public static final Codec<RoguelikeState> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				DUNGEON_LIST_CODEC.fieldOf("dungeons").forGetter(state -> state.dungeons)
			).apply(instance, (dungeonList) -> new RoguelikeState(dungeonList))
	);
	
	public static boolean flagForGenerationCheck = true;
	private List<Dungeon> dungeons;
	
	private RoguelikeState(List<Dungeon> dungeonList) {
		//because different threads may be concurrently writing
		//CopyOnWriteArrayList avoids concurrent modification error
		this.dungeons = new CopyOnWriteArrayList<Dungeon>();
		this.dungeons.addAll(dungeonList);
	}
	
	private RoguelikeState() {
		this.dungeons = new CopyOnWriteArrayList<Dungeon>();
	}

	public void addDungeon(Dungeon toAdd) {
		this.dungeons.add(toAdd);
		this.markDirty();
	}
	
	public void removeDungeon(Dungeon toRemove) {
		this.dungeons.remove(toRemove);
		this.markDirty();
	}
	
	public boolean hasDungeons() {
		return !this.dungeons.isEmpty();
	}
	
	public void update() {
		this.dungeons.stream()
			.filter(d -> d.isGenerated())
			.forEach(d -> {
				Statistics stats = d.getStatistics();
				DataResult<JsonElement> result = Statistics.LOG_CODEC.encodeStart(JsonOps.INSTANCE, stats);
				JsonElement je = result.getOrThrow();
				String json = je.toString();
				Debug.info(json);
			});
		this.dungeons.removeIf(d -> d.isGenerated());
		this.markDirty();
	}
	
	public List<Dungeon> getLoadedDungeons(IWorldEditor editor){
		return this.dungeons.stream()
				.filter(d -> editor.isChunkLoaded(d.getPos()))
				.toList();
	}
		
	public List<IRoom> getLoadedRooms(IWorldEditor editor){
		return this.dungeons.stream()
				.flatMap(d -> StreamSupport.stream(d.spliterator(), false))
				.filter(r -> !r.isGenerated())
				.filter(r -> editor.surroundingChunksLoaded(r.getWorldPos()))
				.toList();
	}
    
    public static RoguelikeState getServerState(RegistryKey<World> worldKey, MinecraftServer server) {
        return server.getWorld(worldKey)
        		.getPersistentStateManager()
        		.getOrCreate(getPersistentStateType());
    }
    
    public static PersistentStateType<RoguelikeState> getPersistentStateType() {
    	
    	return new PersistentStateType<RoguelikeState>(
    			Roguelike.MODID,
    			RoguelikeState::new,
    			CODEC,
    			null);
    		
    }
}
