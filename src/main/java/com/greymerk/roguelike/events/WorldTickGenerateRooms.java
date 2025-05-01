package com.greymerk.roguelike.events;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.state.RoguelikeState;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartWorldTick;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class WorldTickGenerateRooms implements StartWorldTick{
	
	@Override
	public void onStartTick(ServerWorld world) {
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
		state.getLoadedRooms(editor).stream()
			.sorted((a, b) -> a.getWorldPos().getY() - b.getWorldPos().getY())
			.forEach(room -> {
				editor.clearStats();
				room.generate(editor);
				room.applyFilters(editor);
				room.setGenerated(true);
				room.mergeStats(editor.getStatistics());
			});
		
		state.update();
		RoguelikeState.flagForGenerationCheck = false;
	}
}
