package com.greymerk.roguelike.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class RoguelikeState extends PersistentState {
	
	public static boolean flagForGenerationCheck = true;
	private List<Dungeon> dungeons;
	
	private RoguelikeState() {
		//because different threads may be concurrently writing
		//CopyOnWriteArrayList avoids concurrent modification error
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
	
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup var2) {
    	NbtList dungeonData = new NbtList();
    	
    	for(Dungeon d : this.dungeons) {
    		NbtCompound data = d.getNbt();
    		dungeonData.add(data);
    	}
    	
    	nbt.put("dungeons", dungeonData);
        return nbt;
    }    
 
    public static RoguelikeState createFromNbt(NbtCompound tag) {
        RoguelikeState roguelikeState = new RoguelikeState();
        NbtList dungeonList = tag.getList("dungeons", NbtElement.COMPOUND_TYPE);
        roguelikeState.dungeons = RoguelikeState.load(dungeonList);
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
    
    public static RoguelikeState getServerState(RegistryKey<World> worldKey, MinecraftServer server) {
        // First we get the persistentStateManager for the OVERWORLD
        PersistentStateManager persistentStateManager = server
                .getWorld(worldKey).getPersistentStateManager();
 
        // Calling this reads the file from the disk if it exists, or creates a new one and saves it to the disk
        // You need to use a unique string as the key. You should already have a MODID variable defined by you somewhere in your code. Use that.
        RoguelikeState serverState = persistentStateManager.getOrCreate(
        		getPersistentStateType(),
        		Roguelike.MODID);
        return serverState;
    }
    
    public static PersistentState.Type<RoguelikeState> getPersistentStateType() {
    	return new PersistentState.Type<RoguelikeState>(
    			() -> new RoguelikeState(),
    			(nbt, registryLookup) -> createFromNbt(nbt),
    			null
    			);
    	
    }
}
