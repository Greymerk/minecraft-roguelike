package com.greymerk.roguelike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greymerk.roguelike.events.EntityLoadEvent;
import com.greymerk.roguelike.events.WorldTickGenerateRooms;
import com.greymerk.roguelike.gamerules.RoguelikeRules;
import com.greymerk.roguelike.state.ServerLoadListener;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.Load;

public class Roguelike implements ModInitializer {

	public static final String MODID = "roguelike";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final boolean DEBUG = false;
	{
		RoguelikeRules.init();	
	}
	
	@Override
	public void onInitialize() {
		Load listener = new ServerLoadListener();
		ServerWorldEvents.LOAD.register(listener);
		
		ServerEntityEvents.ENTITY_LOAD.register(new EntityLoadEvent());
		ServerTickEvents.START_WORLD_TICK.register(new WorldTickGenerateRooms());
	}
}
