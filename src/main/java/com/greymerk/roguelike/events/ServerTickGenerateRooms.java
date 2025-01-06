package com.greymerk.roguelike.events;

import java.util.List;
import java.util.Set;

import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.state.RoguelikeState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartTick;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class ServerTickGenerateRooms implements StartTick{

	@Override
	public void onStartTick(MinecraftServer server) {
		if(!RoguelikeState.flagForGenerationCheck) return;
		
		Set<RegistryKey<World>> worlds = server.getWorldRegistryKeys();
		worlds.forEach(key -> {
			generate(server, key);
		});
	}
	
	private void generate(MinecraftServer server, RegistryKey<World> key) {
		ServerWorld world = server.getWorld(key);
		IWorldEditor editor = new WorldEditor(world, key);
		
		RoguelikeState state = RoguelikeState.getServerState(key, server);
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
