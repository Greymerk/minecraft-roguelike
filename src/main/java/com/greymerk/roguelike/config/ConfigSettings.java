package com.greymerk.roguelike.config;

import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.util.MixedKey;
import com.greymerk.roguelike.util.MixedMap;


public class ConfigSettings {
	
	private static ConfigSettings instance;
	private MixedMap configs;
	private IConfigStore file;
	
	public static void init(IConfigStore store) {
		instance = new ConfigSettings(store);
	}
	
	public static ConfigSettings getInstance() {
		if(instance == null) {
			instance = new ConfigSettings(new ConfigFile());
		}
		return instance;
	}
	
	private ConfigSettings(IConfigStore store) {
		this.configs = new MixedMap();
		this.file = store;
		this.init();
	}
	
	private void init() {
		this.configs.putMixed(MixedKey.ofBoolean(Config.MOB_DROPS.keyOf()), true);
		this.configs.putMixed(MixedKey.ofDouble(Config.FREQUENCY.keyOf()), 1.0);
		this.configs.putMixed(MixedKey.ofBoolean(Config.DEBUG.keyOf()), false);
		this.configs.putMixed(MixedKey.ofBoolean(Config.BELOW_SEA_LEVEL.keyOf()), true);
		this.configs.putMixed(MixedKey.ofInteger(Config.ROOMS_PER_LEVEL.keyOf()), 20);
		this.configs.putMixed(MixedKey.ofBoolean(Config.ROGUELIKE_LOOT.keyOf()), true);
		this.configs.putMixed(MixedKey.ofBoolean(Config.DETERMINISTIC.keyOf()), true);
		this.configs.putMixed(MixedKey.ofInteger(Config.DISTANCE_TO_VILLAGE.keyOf()), 6);
		if(file.exists()) this.read();
		this.store();
	}

	public JsonObject asJson() {
		JsonObject json = new JsonObject();
		json.addProperty(Config.MOB_DROPS.keyOf(), this.get(MixedKey.ofBoolean(Config.MOB_DROPS.keyOf())));
		json.addProperty(Config.FREQUENCY.keyOf(), this.get(MixedKey.ofDouble(Config.FREQUENCY.keyOf())));
		json.addProperty(Config.DEBUG.keyOf(), this.get(MixedKey.ofBoolean(Config.DEBUG.keyOf())));
		json.addProperty(Config.BELOW_SEA_LEVEL.keyOf(), this.get(MixedKey.ofBoolean(Config.BELOW_SEA_LEVEL.keyOf())));
		json.addProperty(Config.ROOMS_PER_LEVEL.keyOf(), this.get(MixedKey.ofInteger(Config.ROOMS_PER_LEVEL.keyOf())));
		json.addProperty(Config.ROGUELIKE_LOOT.keyOf(), this.get(MixedKey.ofBoolean(Config.ROGUELIKE_LOOT.keyOf())));
		json.addProperty(Config.DETERMINISTIC.keyOf(), this.get(MixedKey.ofBoolean(Config.DETERMINISTIC.keyOf())));
		json.addProperty(Config.DISTANCE_TO_VILLAGE.keyOf(), this.get(MixedKey.ofInteger(Config.DISTANCE_TO_VILLAGE.keyOf())));
		return json;
	}
	
	public void parse(JsonObject json) {
		if(json.has(Config.MOB_DROPS.keyOf())) configs.put(MixedKey.ofBoolean(Config.MOB_DROPS.keyOf()), json.get(Config.MOB_DROPS.keyOf()).getAsBoolean());
		if(json.has(Config.FREQUENCY.keyOf())) configs.put(MixedKey.ofDouble(Config.FREQUENCY.keyOf()), json.get(Config.FREQUENCY.keyOf()).getAsDouble());
		if(json.has(Config.DEBUG.keyOf())) configs.put(MixedKey.ofBoolean(Config.DEBUG.keyOf()), json.get(Config.DEBUG.keyOf()).getAsBoolean());
		if(json.has(Config.BELOW_SEA_LEVEL.keyOf())) configs.put(MixedKey.ofBoolean(Config.BELOW_SEA_LEVEL.keyOf()), json.get(Config.BELOW_SEA_LEVEL.keyOf()).getAsBoolean());
		if(json.has(Config.ROOMS_PER_LEVEL.keyOf())) configs.put(MixedKey.ofInteger(Config.ROOMS_PER_LEVEL.keyOf()), json.get(Config.ROOMS_PER_LEVEL.keyOf()).getAsInt());
		if(json.has(Config.ROGUELIKE_LOOT.keyOf())) configs.put(MixedKey.ofBoolean(Config.ROGUELIKE_LOOT.keyOf()), json.get(Config.ROGUELIKE_LOOT.keyOf()).getAsBoolean());
		if(json.has(Config.DETERMINISTIC.keyOf())) configs.put(MixedKey.ofBoolean(Config.DETERMINISTIC.keyOf()), json.get(Config.DETERMINISTIC.keyOf()).getAsBoolean());
		if(json.has(Config.DISTANCE_TO_VILLAGE.keyOf())) configs.put(MixedKey.ofInteger(Config.DISTANCE_TO_VILLAGE.keyOf()), json.get(Config.DISTANCE_TO_VILLAGE.keyOf()).getAsInt());
	}
	
	public <T> T get(MixedKey<T> key) {
		if(!this.has(key)) return null;
		return configs.getMixed(key);
	}
	
	public <T> void put(MixedKey<T> key, T value) {
		if(!this.has(key)) return;
		configs.putMixed(key, value);
		this.store();
	}

	public <T> boolean has(MixedKey<T> key) {
		return configs.containsKey(key);
	}

	public void read() {
		Optional<String> contents = file.getFileContents();
		if(contents.isEmpty()) return;
		try {
			String str = contents.get();
			JsonElement jsonElement = JsonParser.parseString(str);
			JsonObject json = jsonElement.getAsJsonObject();
			this.parse(json);	
		} catch (IllegalStateException e) {
			Roguelike.LOGGER.error(e.toString());
			Roguelike.LOGGER.error("Invalid Json Config - Replacing with defaults");
			this.store();
			return;
		} catch (Exception e){
			Roguelike.LOGGER.error(e.toString());
			Roguelike.LOGGER.error("Something's wrong with the config file - using defaults for now");
			return;
		}
	}
	
	private void store() {
		JsonObject json = this.asJson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		file.write(gson.toJson(json));
	}
}
