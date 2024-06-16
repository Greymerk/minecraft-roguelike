package com.greymerk.roguelike.events;

import com.greymerk.roguelike.dungeon.DungeonLocation;
import com.greymerk.roguelike.dungeon.DungeonPlacement;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.gamerules.RoguelikeRules;
import com.greymerk.roguelike.state.RoguelikeState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents.Load;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class ChunkLoadGenerateDungeon implements Load{

	@Override
	public void onChunkLoad(ServerWorld sw, WorldChunk chunk) {
		if(sw.isClient) return;
		long inhabited = chunk.getInhabitedTime();
		if(inhabited != 0) return;
		
		IWorldEditor editor = new WorldEditor(sw);
		if(!editor.getGameRules().getBoolean(RoguelikeRules.GEN_ROGUELIKE_DUNGEONS)) return;
		
		ChunkPos cpos = chunk.getPos();
		if(!DungeonPlacement.validChunkPos(editor, cpos)) return;
		RegistryKey<World> key = editor.getKey();
		RoguelikeState state = editor.getState();
		state.addPlacement(DungeonLocation.of(key, cpos));
	}

}
