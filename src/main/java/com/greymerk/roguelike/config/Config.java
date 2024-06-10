package com.greymerk.roguelike.config;

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
}
