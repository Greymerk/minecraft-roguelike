package com.greymerk.roguelike.settings;

import java.util.HashMap;
import java.util.Map;

import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.settings.level.LevelSettingsDefault;

public class DungeonSettingsBase implements IDungeonSettings{

	protected Map<Integer, ILevelSettings> levelSettings;
	
	public DungeonSettingsBase() {
		this.levelSettings = new HashMap<Integer, ILevelSettings>();
	}
	
	@Override
	public ILevelSettings getLevel(int y) {
		Integer level = Dungeon.getLevelFromY(y);
		if(!this.levelSettings.containsKey(level)) {
			return new LevelSettingsDefault();
		}
		return this.levelSettings.get(level);
	}
}
