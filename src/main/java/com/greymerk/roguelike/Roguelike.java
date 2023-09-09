package com.greymerk.roguelike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greymerk.roguelike.state.ServerLoadListener;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.Load;

public class Roguelike implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "roguelike";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	public static final boolean DEBUG = false;
	
	@Override
	public void onInitialize() {

		Load listener = new ServerLoadListener();
		ServerWorldEvents.LOAD.register(listener);
		
		
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		//LOGGER.info("Hello Roguelike world!");
		
	}
}
