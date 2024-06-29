package com.greymerk.roguelike.debug;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.config.Config;

public class Debug {
	
	public static boolean isOn() {
		return Config.ofBoolean(Config.DEBUG);
	}
	
	public static void info(String message) {
		if(Debug.isOn()) Roguelike.LOGGER.info("DEBUG: " + message);
	}
}