package com.greymerk.roguelike.settings.dungeon;

import com.greymerk.roguelike.settings.IDungeonSettings;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;

public class DungeonSettingsRandom implements IDungeonSettings{
	
	@Override
	public ILevelSettings getLevel(int y) {
		return LevelSettings.fromType(LevelSettings.RANDOM);
	}

	@Override
	public LevelSettings getSettings(int y) {
		// TODO Auto-generated method stub
		return null;
	}
}
