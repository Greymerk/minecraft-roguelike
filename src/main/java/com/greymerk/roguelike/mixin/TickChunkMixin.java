package com.greymerk.roguelike.mixin;

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
import net.minecraft.world.chunk.WorldChunk;

@Mixin(ServerWorld.class)
public class TickChunkMixin{
	
	@Inject(at = @At("HEAD"), method = "tickChunk(Lnet/minecraft/world/chunk/WorldChunk;I)V")
	private void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo info) {
		//System.out.println("chunktick");
		MinecraftServer server = chunk.getWorld().getServer();
		RoguelikeState state = RoguelikeState.getServerState(server);
		
		if(!surroundingChunksLoaded(chunk.getWorld(), chunk.getPos())) return;
		
		IWorldEditor editor = new WorldEditor(chunk.getWorld());
		List<IRoom> rooms = state.getFromChunk(chunk.getPos());
		
		for(IRoom room : rooms) {
			room.generate(editor);
			room.setGenerated(true);
		}
		
		state.update();
		//System.out.println("chunktick end");
	}
	
	private boolean surroundingChunksLoaded(World world, ChunkPos cpos) {
		for(int x = cpos.x - 1; x <= cpos.x + 1; x++) {
			for(int z = cpos.z - 1; z <= cpos.z + 1; z++) {
				if(!world.isChunkLoaded(x, z)) return false;
			}
		}
		
		return true;
	}
}