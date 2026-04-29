package com.greymerk.roguelike.events;

import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.DungeonPlacement;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.IWorldInfo;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.gamerules.RoguelikeRules;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents.Generate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkDungeonGenerate implements Generate {

	@Override
	public void onChunkGenerate(ServerLevel world, LevelChunk chunk) {
		IWorldEditor editor = WorldEditor.of(world);
		IWorldInfo worldInfo = editor.getInfo();
		if(!worldInfo.getGameRules().get(RoguelikeRules.GEN_ROGUELIKE_DUNGEONS)) return;
		
		ChunkPos cpos = chunk.getPos();
		if(!DungeonPlacement.validChunkPos(editor, cpos)) return;
		
		Coord pos = Coord.of(cpos.getMiddleBlockX(), worldInfo.getTopYInclusive(), cpos.getMiddleBlockZ()).freeze();
		editor.getInfo().getState().addDungeon(new Dungeon(pos));
	}
}
