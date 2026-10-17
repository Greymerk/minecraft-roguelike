package com.greymerk.roguelike.events;
import java.util.List;

import com.greymerk.roguelike.debug.Debug;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.state.RoguelikeState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartLevelTick;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class WorldTickGenerateRooms implements StartLevelTick{
	
	private static final int ROOMS_PER_TICK = 50;
	
	@Override
	public void onStartTick(ServerLevel world) {
		if(!RoguelikeState.flagForGenerationCheck) return;
		
		IWorldEditor editor = WorldEditor.of(world);
		MinecraftServer server = world.getServer();
		RoguelikeState state = RoguelikeState.getServerState(editor.getRegistryKey(), server);
		if(!state.hasDungeons()) return;
		
		// check for dungeons that have been initiated but require layout
		state.getLoadedDungeons(editor).stream()
			.filter(d -> !d.hasLayout())
			.forEach(d -> {
				Coord pos = d.getPos();
				state.removeDungeon(d);
				Dungeon.generate(editor, pos);
			});;
		
		// check for rooms that can be generated
		List<IRoom> rooms = state.getLoadedRooms(editor); 
		if(rooms.isEmpty()) RoguelikeState.flagForGenerationCheck = false;		
		rooms.stream()
			.sorted((a, b) -> a.getWorldPos().getY() - b.getWorldPos().getY())
			.limit(ROOMS_PER_TICK)
			.forEach(room -> {
				editor.clearStats();
				room.generate(editor);
				room.applyFilters(editor);
				room.setGenerated(true);
				room.mergeStats(editor.getStatistics());
			});
		
		state.update(editor);
	}
}
