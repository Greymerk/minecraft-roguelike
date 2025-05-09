package com.greymerk.roguelike.settings;

public interface IDungeonSettings {

	public LevelSettings getSettings(int y);
	
	public ILevelSettings getLevel(int y);
	
}
