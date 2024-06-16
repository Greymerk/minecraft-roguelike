package com.greymerk.roguelike.events;

import java.util.List;

import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.state.RoguelikeState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartWorldTick;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionTypes;

public class WorldTickGenerate implements StartWorldTick{

	@Override
	public void onStartTick(ServerWorld sw) {
		if(sw.getDimensionKey() != DimensionTypes.OVERWORLD) return;
		if(!RoguelikeState.flagForGenerationCheck) return;
		
		IWorldEditor editor = new WorldEditor(sw);
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
