package com.greymerk.roguelike.events;

import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.DungeonPlacement;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.IWorldInfo;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.gamerules.RoguelikeRules;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents.Generate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public class ChunkDungeonGenerate implements Generate {

	@Override
	public void onChunkGenerate(ServerWorld world, WorldChunk chunk) {
		IWorldEditor editor = WorldEditor.of(world);
		IWorldInfo worldInfo = editor.getInfo();
		if(!worldInfo.getGameRules().getValue(RoguelikeRules.GEN_ROGUELIKE_DUNGEONS)) return;
		
		ChunkPos cpos = chunk.getPos();
		if(!DungeonPlacement.validChunkPos(editor, cpos)) return;
		
		Coord pos = Coord.of(cpos.getCenterX(), worldInfo.getTopYInclusive(), cpos.getCenterZ()).freeze();
		editor.getInfo().getState().addDungeon(new Dungeon(pos));
	}
}
