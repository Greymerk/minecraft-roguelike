package com.greymerk.roguelike.settings.dungeon;

import java.util.List;

import com.greymerk.roguelike.settings.IDungeonSettings;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.util.Accordion;

public class DungeonSettingsDefault implements IDungeonSettings {

	int firstLevelY;
	int lastLevelY;
	List<LevelSettings> settings;
	
	public DungeonSettingsDefault() {
		this(50, -50);
	}
	
	public DungeonSettingsDefault(int firstLevelY, int lastLevelY) {
		this.firstLevelY = firstLevelY;
		this.lastLevelY = lastLevelY;
		
		this.settings = new Accordion<LevelSettings>()
				.add(LevelSettings.OAK)
				.add(LevelSettings.SPRUCE)
				.add(LevelSettings.DARK_OAK)
				.addAtLeast(LevelSettings.STONE, 1)
				.add(LevelSettings.CRUMBLED_STONE)
				.addAtLeast(LevelSettings.MOSSY, 1)
				.addAtLeast(LevelSettings.CRUMBLED_MOSSY, 2)
				.add(LevelSettings.TILED_SLATE)
				.list(((firstLevelY - lastLevelY) / 10) + 1);
	}
	
	public LevelSettings getLevelType(int y) {
		int i = (firstLevelY - y) / 10;
		if(i <= 0) return this.settings.getFirst();
		if(i >= this.settings.size()) return this.settings.getLast();
		return this.settings.get(i);
	}
	
	@Override
	public ILevelSettings getLevel(int y) {
		return LevelSettings.fromType(getLevelType(y));
	}

	@Override
	public LevelSettings getSettings(int y) {
		return this.getLevelType(y);
	}
}
