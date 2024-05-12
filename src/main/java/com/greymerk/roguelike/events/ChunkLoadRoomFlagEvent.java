package com.greymerk.roguelike.events;

import com.greymerk.roguelike.state.RoguelikeState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents.Load;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;

// Give the state class a heads up to check for rooms that need to generate
public class ChunkLoadRoomFlagEvent implements Load {

	@Override
	public void onChunkLoad(ServerWorld world, WorldChunk chunk) {
		RoguelikeState.flagForGenerationCheck = true;
	}
}
