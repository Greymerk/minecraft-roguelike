package com.greymerk.roguelike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greymerk.roguelike.commands.RoguelikeCommand;
import com.greymerk.roguelike.events.ChunkLoadRoomFlagEvent;
import com.greymerk.roguelike.events.EntityLoadEvent;
import com.greymerk.roguelike.events.WorldTickGenerate;
import com.greymerk.roguelike.gamerules.RoguelikeRules;
import com.greymerk.roguelike.state.ServerLoadListener;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.Load;

public class Roguelike implements ModInitializer {

	public static final String MODID = "roguelike";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	{
		RoguelikeRules.init();	
	}
	
	@Override
	public void onInitialize() {
		Load listener = new ServerLoadListener();
		ServerWorldEvents.LOAD.register(listener);
		
		ServerEntityEvents.ENTITY_LOAD.register(new EntityLoadEvent());
		ServerTickEvents.START_SERVER_TICK.register(new WorldTickGenerate());
		ServerChunkEvents.CHUNK_LOAD.register(new ChunkLoadRoomFlagEvent());
		//ServerChunkEvents.CHUNK_LOAD.register(new ChunkLoadGenerateDungeon());
		
		//ServerTickEvents.START_WORLD_TICK.register(new WorldTickTest());
		
		CommandRegistrationCallback.EVENT.register(RoguelikeCommand.getListener());
	}
}
