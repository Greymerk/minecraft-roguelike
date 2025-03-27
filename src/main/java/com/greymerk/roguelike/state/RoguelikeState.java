package com.greymerk.roguelike.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
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
	
	public void update() {
		this.markDirty();
	}
	
	public List<IRoom> getFromLoaded(IWorldEditor editor){
		List<IRoom> loadedRooms = new ArrayList<IRoom>();
		
		for(Dungeon d : this.dungeons) {
			for(IRoom r : d) {
				if(r.isGenerated()) continue;
				Coord pos = r.getWorldPos();
				if(editor.surroundingChunksLoaded(pos)) {
					loadedRooms.add(r);
				}
			}
		}
		
		return loadedRooms;
	}
    
    public static RoguelikeState getServerState(RegistryKey<World> worldKey, MinecraftServer server) {
        // First we get the persistentStateManager for the OVERWORLD
        PersistentStateManager persistentStateManager = server
                .getWorld(worldKey).getPersistentStateManager();
 
        // Calling this reads the file from the disk if it exists, or creates a new one and saves it to the disk
        // You need to use a unique string as the key. You should already have a MODID variable defined by you somewhere in your code. Use that.
        RoguelikeState serverState = persistentStateManager.getOrCreate(getPersistentStateType());
        
        return serverState;
    }
    
    public static PersistentStateType<RoguelikeState> getPersistentStateType() {
    	
    	return new PersistentStateType<RoguelikeState>(
    			Roguelike.MODID,
    			RoguelikeState::new,
    			CODEC,
    			null);
    		
    }
}
