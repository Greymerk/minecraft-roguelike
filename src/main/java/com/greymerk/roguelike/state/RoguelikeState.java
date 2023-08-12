package com.greymerk.roguelike.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class RoguelikeState extends PersistentState {
	
	List<Dungeon> dungeons;
	
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
	
	public List<IRoom> getFromChunk(ChunkPos cpos){
		
		List<IRoom> rooms = new ArrayList<IRoom>();
		Coord start = new Coord(cpos.getStartX(), -64, cpos.getStartZ());
		Coord end = new Coord(cpos.getEndX(), 256, cpos.getEndZ());
		IBounded box = new BoundingBox(start, end);
		
		for(Dungeon d : this.dungeons) {
			for(IRoom r : d) {
				if((!r.isGenerated()) && box.contains(r.getWorldPos())) {
					rooms.add(r);
				}
			}
		}
		
		return rooms;
	}
	
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
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
    	List<Dungeon> dungeons = new ArrayList<Dungeon>();
    	for(int i = 0; i < dungeonList.size(); i++) {
    		NbtCompound data = dungeonList.getCompound(i);
    		dungeons.add(new Dungeon(data));
    	}
    	
    	return dungeons;
    }
    
    public static RoguelikeState getServerState(MinecraftServer server) {
        // First we get the persistentStateManager for the OVERWORLD
        PersistentStateManager persistentStateManager = server
                .getWorld(World.OVERWORLD).getPersistentStateManager();
 
        // Calling this reads the file from the disk if it exists, or creates a new one and saves it to the disk
        // You need to use a unique string as the key. You should already have a MODID variable defined by you somewhere in your code. Use that.
        RoguelikeState serverState = persistentStateManager.getOrCreate(
                RoguelikeState::createFromNbt,
                RoguelikeState::new,
                Roguelike.MODID); 
 
        return serverState;
    }
}
