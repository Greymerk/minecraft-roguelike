package com.greymerk.roguelike.config;

import com.greymerk.roguelike.util.MixedKey;

public enum Config {
	
	FREQUENCY("frequency"),
	MOB_DROPS("mob_drops");

	private final String key;
	
	Config(final String key){
		this.key = key;
	}
	
	public String keyOf() {
		return this.key;
	}
	
	@Override
	public String toString() {
		return key;
	}
	
	public static Boolean ofBoolean(Config type) {
		ConfigSettings settings = ConfigSettings.getInstance();
		return settings.get(MixedKey.ofBoolean(type.keyOf()));
	}
	
	public static Double ofDouble(Config type) {
		ConfigSettings settings = ConfigSettings.getInstance();
		return settings.get(MixedKey.ofDouble(type.keyOf()));
	}
	
	public static Integer ofInteger(Config type) {
		ConfigSettings settings = ConfigSettings.getInstance();
		return settings.get(MixedKey.ofInteger(type.keyOf()));
	}
	
	public static String ofString(Config type) {
		ConfigSettings settings = ConfigSettings.getInstance();
		return settings.get(MixedKey.ofString(type.keyOf()));
	}
}
