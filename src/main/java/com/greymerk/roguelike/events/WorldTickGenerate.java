package com.greymerk.roguelike.events;

import java.util.List;

import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.state.RoguelikeState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartTick;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionTypes;

public class WorldTickGenerate implements StartTick{

	@Override
	public void onStartTick(MinecraftServer server) {
		server.getWorlds().forEach(sw -> {
			tick(sw);
		});
	}
	
	public void tick(ServerWorld sw) {
		if(sw.getDimensionKey() != DimensionTypes.OVERWORLD) return;
		if(!RoguelikeState.flagForGenerationCheck) return;
		
		IWorldEditor editor = new WorldEditor(sw);
		//System.out.println("tick " + editor.getKey());
		RoguelikeState state = editor.getState();
		List<IRoom> rooms = state.getFromLoaded(editor);
		if(rooms.isEmpty()) return;
		
		for(IRoom room : rooms) {
			room.generate(editor);
			room.applyFilters(editor);
			room.setGenerated(true);
		}	
		
		state.update();	
		RoguelikeState.flagForGenerationCheck = false;
	}
}
