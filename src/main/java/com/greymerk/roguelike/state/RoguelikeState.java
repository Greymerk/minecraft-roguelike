package com.greymerk.roguelike.state;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.StreamSupport;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.debug.Debug;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.Statistics;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class RoguelikeState extends SavedData {
	
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
		this.setDirty();
	}
	
	public void removeDungeon(Dungeon toRemove) {
		this.dungeons.remove(toRemove);
		this.setDirty();
	}
	
	public boolean hasDungeons() {
		return !this.dungeons.isEmpty();
	}
	
	public void update(IWorldEditor editor) {
		if(Debug.isOn()) {
			this.dungeons.stream()
			.filter(d -> d.isGenerated())
			.forEach(d -> {
				Statistics stats = d.getStatistics();
				Debug.toFile(editor, 
					"RoguelikeDungeons_" + d.getPos().toString().replaceAll(" ", "_") + "_Loot.json", 
					Statistics.CODEC.encodeStart(JsonOps.INSTANCE, stats).getOrThrow());
				Debug.info("Dungeon @" + d.getPos().toString() + " completed generating rooms.");
			});	
		}
		this.dungeons.removeIf(d -> d.isGenerated());
		this.setDirty();
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
    
    public static RoguelikeState getServerState(ResourceKey<Level> worldKey, MinecraftServer server) {
        return server.getLevel(worldKey)
        		.getDataStorage()
        		.computeIfAbsent(getPersistentStateType());
    }
    
    public static SavedDataType<RoguelikeState> getPersistentStateType() {
    	
    	return new SavedDataType<>(
                Identifier.fromNamespaceAndPath(Roguelike.MODID, "state"),
                RoguelikeState::new,
                CODEC,
                DataFixTypes.LEVEL);
    		
    }
}
