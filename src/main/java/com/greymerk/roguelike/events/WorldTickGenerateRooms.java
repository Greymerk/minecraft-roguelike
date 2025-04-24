package com.greymerk.roguelike.events;
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
		if(!state.hasDungeons()) return;
		
		state.getFromLoaded(editor).stream()
			.sorted((a, b) -> a.getWorldPos().getY() - b.getWorldPos().getY())
			.forEach(room -> {
				room.generate(editor);
				room.applyFilters(editor);
				room.setGenerated(true);
			});
		
		state.update();
		RoguelikeState.flagForGenerationCheck = false;
	}
}
