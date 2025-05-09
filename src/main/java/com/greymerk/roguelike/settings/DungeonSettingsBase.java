package com.greymerk.roguelike.settings;

public class DungeonSettingsBase implements IDungeonSettings{
	
	public DungeonSettingsBase() {
	}
	
	@Override
	public ILevelSettings getLevel(int y) {
		return LevelSettings.fromType(LevelSettings.OAK);
	}

	@Override
	public LevelSettings getSettings(int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
