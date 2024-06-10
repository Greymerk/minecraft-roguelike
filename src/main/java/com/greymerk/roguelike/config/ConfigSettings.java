package com.greymerk.roguelike.config;

import com.google.gson.JsonObject;
import com.greymerk.roguelike.util.MixedKey;
import com.greymerk.roguelike.util.MixedMap;


public class ConfigSettings {
	
	private static ConfigSettings instance;
	private MixedMap configs;
	private ConfigFile file;
	
	public static ConfigSettings getInstance() {
		if(instance == null) {
			instance = new ConfigSettings();
		}
		return instance;
	}
	
	private ConfigSettings() {
		this.configs = new MixedMap();
		this.file = new ConfigFile(this);
		this.init();
	}
	
	private void init() {
		this.configs.putMixed(MixedKey.ofBoolean(Config.MOB_DROPS.keyOf()), true);
		this.configs.putMixed(MixedKey.ofDouble(Config.FREQUENCY.keyOf()), 1.0);
		this.file.read();
	}
	
	public <T> T get(MixedKey<T> key) {
		if(!this.has(key)) return null;
		return configs.getMixed(key);
	}
	
	public <T> void put(MixedKey<T> key, T value) {
		if(!this.has(key)) return;
		configs.putMixed(key, value);
		this.file.writeConfigFile();
	}

	public <T> boolean has(MixedKey<T> key) {
		return configs.containsKey(key);
	}

	public JsonObject asJson() {
		JsonObject json = new JsonObject();
		json.addProperty(Config.MOB_DROPS.keyOf(), this.get(MixedKey.ofBoolean(Config.MOB_DROPS.keyOf())));
		json.addProperty(Config.FREQUENCY.keyOf(), this.get(MixedKey.ofDouble(Config.FREQUENCY.keyOf())));
		return json;
	}
	
	public void parse(JsonObject json) {
		if(json.has(Config.MOB_DROPS.keyOf())) configs.put(MixedKey.ofBoolean(Config.MOB_DROPS.keyOf()), json.get(Config.MOB_DROPS.keyOf()).getAsBoolean());
		if(json.has(Config.FREQUENCY.keyOf())) configs.put(MixedKey.ofDouble(Config.FREQUENCY.keyOf()), json.get(Config.FREQUENCY.keyOf()).getAsDouble());
	}
}
