package com.greymerk.roguelike.config;

import java.util.Optional;

import com.greymerk.roguelike.util.MixedKey;

public enum Config {
	
	FREQUENCY("frequency"),
	MOB_DROPS("mob_drops"),
	DEBUG("debug"),
	BELOW_SEA_LEVEL("below_sea_level"),
	ROOMS_PER_LEVEL("rooms_per_level"),
	ROGUELIKE_LOOT("roguelike_loot"),
	RANDOM_SEED("random_seed");

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
	
	public static Optional<Integer> ofInteger(Config type) {
		ConfigSettings settings = ConfigSettings.getInstance();
		MixedKey<Integer> key = MixedKey.ofInteger(type.keyOf());
		if(settings.has(key)) {
			return Optional.of(settings.get(key));
		} else {
			return Optional.empty();
		}
	}
	
	public static String ofString(Config type) {
		ConfigSettings settings = ConfigSettings.getInstance();
		return settings.get(MixedKey.ofString(type.keyOf()));
	}
}
