package com.greymerk.roguelike.settings.dungeon;

import com.greymerk.roguelike.settings.DungeonSettingsBase;
import com.greymerk.roguelike.settings.IDungeonSettings;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;

public class DungeonSettingsRandom extends DungeonSettingsBase implements IDungeonSettings{
	
	public DungeonSettingsRandom() {
	}
	
	@Override
	public ILevelSettings getLevel(int y) {
		return LevelSettings.fromType(LevelSettings.RANDOM);
	}
}
