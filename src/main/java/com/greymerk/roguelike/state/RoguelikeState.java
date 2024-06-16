package com.greymerk.roguelike.state;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.DungeonLocation;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class RoguelikeState extends PersistentState {
	
	public static boolean flagForGenerationCheck = true;
	private List<Dungeon> dungeons;
	private Set<DungeonLocation> potentials;
	
	private RoguelikeState() {
		//because different threads may be concurrently writing
		//CopyOnWriteArrayList avoids concurrent modification error
		this.dungeons = new CopyOnWriteArrayList<Dungeon>();
		this.potentials = new HashSet<DungeonLocation>();
	}

	public void addDungeon(Dungeon toAdd) {
		this.dungeons.add(toAdd);
		this.markDirty();
	}
	
	public void addPlacement(DungeonLocation toAdd) {
		this.potentials.add(toAdd);
		this.markDirty();
	}
	
	public void removePlacement(DungeonLocation dl) {
		this.potentials.remove(dl);
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
	
	public List<DungeonLocation> getLoadedPotentials(IWorldEditor editor){
		List<DungeonLocation> placements = new ArrayList<DungeonLocation>();
		if(this.potentials.isEmpty()) return List.of();
		this.potentials.forEach(dl -> {
			if(!editor.getKey().equals(dl.getKey())) return;
			Coord pos = Coord.of(dl.getChunkPos());
			if(editor.surroundingChunksLoaded(pos)) {
				placements.add(dl);	
			}
		});
		return placements;
	}
	
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
    	NbtList dungeonData = new NbtList();
    	
    	for(Dungeon d : this.dungeons) {
    		NbtCompound data = d.getNbt();
    		dungeonData.add(data);
    	}
    	
    	nbt.put("dungeons", dungeonData);
    	
    	NbtList placements = new NbtList();
    	this.potentials.forEach(dl -> {
    		NbtCompound data = dl.getNbt();
    		placements.add(data);
    	});
    	
    	nbt.put("potentials", placements);
        return nbt;
    }    
 
    public static RoguelikeState createFromNbt(NbtCompound tag) {
        RoguelikeState roguelikeState = new RoguelikeState();
        NbtList dungeonList = tag.getList("dungeons", NbtElement.COMPOUND_TYPE);
        roguelikeState.dungeons = RoguelikeState.load(dungeonList);
        
        NbtList potentials = tag.getList("potentials", NbtElement.COMPOUND_TYPE);
        roguelikeState.potentials = RoguelikeState.loadPlacements(potentials);
        
        return roguelikeState;
    }
    
    public static List<Dungeon> load(NbtList dungeonList){
    	List<Dungeon> dungeons = new CopyOnWriteArrayList<Dungeon>();
    	for(int i = 0; i < dungeonList.size(); i++) {
    		NbtCompound data = dungeonList.getCompound(i);
    		Dungeon toAdd = new Dungeon(data);
    		dungeons.add(toAdd);
    	}
    	
    	return dungeons;
    }
    
    public static Set<DungeonLocation> loadPlacements(NbtList placements){
    	Set<DungeonLocation> locations = new HashSet<DungeonLocation>();
    	for(int i = 0; i < placements.size(); i++) {
    		NbtCompound data = placements.getCompound(i);
    		DungeonLocation toAdd = DungeonLocation.of(data);
    		locations.add(toAdd);
    	}
    	return locations;
    }
    
    public static RoguelikeState getServerState(RegistryKey<World> key, MinecraftServer server) {
        // First we get the persistentStateManager for the OVERWORLD
        PersistentStateManager persistentStateManager = server
                .getWorld(key).getPersistentStateManager();
 
        // Calling this reads the file from the disk if it exists, or creates a new one and saves it to the disk
        // You need to use a unique string as the key. You should already have a MODID variable defined by you somewhere in your code. Use that.
        RoguelikeState serverState = persistentStateManager.getOrCreate(
                RoguelikeState::createFromNbt,
                RoguelikeState::new,
                Roguelike.MODID); 
        return serverState;
    }
}
