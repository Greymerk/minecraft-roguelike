package com.greymerk.roguelike.state;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.StreamSupport;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.debug.Debug;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
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
	
	public void update(IWorldEditor editor) {
		if(Debug.isOn()) {
			this.dungeons.stream()
			.filter(d -> d.isGenerated())
			.forEach(d -> {
				Debug.info("Dungeon @" + d.getPos().toString() + " completed generating rooms.");
			});	
		}
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
    
    public static List<Dungeon> load(NbtList dungeonList){
    	List<Dungeon> dungeons = new CopyOnWriteArrayList<Dungeon>();
    	for(int i = 0; i < dungeonList.size(); i++) {
    		NbtCompound data = dungeonList.getCompound(i);
    		Dungeon toAdd = Dungeon.CODEC.decode(NbtOps.INSTANCE, data).result().get().getFirst();
    		dungeons.add(toAdd);
    	}
    	
    	return dungeons;
    }
	
    public static RoguelikeState createFromNbt(NbtCompound tag) {
        RoguelikeState roguelikeState = new RoguelikeState();
        NbtList dungeonList = tag.getList("dungeons", NbtElement.COMPOUND_TYPE);
        roguelikeState.dungeons = RoguelikeState.load(dungeonList);
        
        return roguelikeState;
    }
	
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup var2) {
    	return (NbtCompound) CODEC.encodeStart(NbtOps.INSTANCE, this).result().get();
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
