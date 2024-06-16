package com.greymerk.roguelike.events;

import java.util.List;

import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.state.RoguelikeState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartWorldTick;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class WorldTickGenerateRooms implements StartWorldTick{

	@Override
	public void onStartTick(ServerWorld world) {
		if(!RoguelikeState.flagForGenerationCheck) return;
		
		IWorldEditor editor = new WorldEditor((World)world);
		MinecraftServer server = world.getServer();
		RoguelikeState state = RoguelikeState.getServerState(editor.getRegistryKey(), server);
		
		List<IRoom> rooms = state.getFromLoaded(editor);
		
		for(IRoom room : rooms) {
			room.generate(editor);
			room.applyFilters(editor);
			room.setGenerated(true);
		}	
		
		state.update();
		RoguelikeState.flagForGenerationCheck = false;
	}
}
