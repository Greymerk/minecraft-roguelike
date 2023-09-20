package com.greymerk.roguelike.mixin;

import java.util.Comparator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.state.RoguelikeState;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;

@Mixin(ServerWorld.class)
public class TickChunkMixin{
	
	@Inject(at = @At("HEAD"), method = "tickChunk(Lnet/minecraft/world/chunk/WorldChunk;I)V")
	private void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo info) {
		MinecraftServer server = chunk.getWorld().getServer();
		RoguelikeState state = RoguelikeState.getServerState(server);
		
		if(!surroundingChunksLoaded(chunk.getWorld(), chunk.getPos())) return;
		
		IWorldEditor editor = new WorldEditor(chunk.getWorld());
		
		
		List<IRoom> rooms = state.getFromChunk(chunk.getPos());
		
		rooms.sort(new RoomSortByY());
		
		for(IRoom room : rooms) {
			room.generate(editor);
			room.applyFilters(editor);
			room.setGenerated(true);
		}
		
		state.update();
	}
	
	private boolean surroundingChunksLoaded(World world, ChunkPos cpos) {
		for(int x = cpos.x - 1; x <= cpos.x + 1; x++) {
			for(int z = cpos.z - 1; z <= cpos.z + 1; z++) {
				if(!world.isChunkLoaded(x, z)) return false;
				Chunk chunk = world.getChunk(x, z);
				ChunkStatus status = chunk.getStatus();
				if(status != ChunkStatus.FULL) return false;
			}
		}
		
		return true;
	}
	
	class RoomSortByY implements Comparator<IRoom>{
		@Override
		public int compare(IRoom room1, IRoom room2) {
			int depth1 = room1.getWorldPos().getY();
			int depth2 = room2.getWorldPos().getY();
			return depth1 - depth2;
		}
	}
	
	 
}
