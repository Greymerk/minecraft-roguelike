package com.greymerk.roguelike.settings.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greymerk.roguelike.settings.IDungeonSettings;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;

public class DungeonSettingsDefault implements IDungeonSettings {

	int firstLevelY;
	int lastLevelY;
	Map<Double, LevelSettings> levelSettings;
	
	public DungeonSettingsDefault() {
		this(50, -50);
	}
	
	public DungeonSettingsDefault(int firstLevelY, int lastLevelY) {
		this.firstLevelY = firstLevelY;
		this.lastLevelY = lastLevelY;
		
		this.levelSettings = new HashMap<Double, LevelSettings>();
		
		this.set(0d, LevelSettings.OAK);
		this.set(0.03d, LevelSettings.SPRUCE);
		this.set(0.15d, LevelSettings.DARK_OAK);
		this.set(0.25d, LevelSettings.STONE);
		this.set(0.45d, LevelSettings.CRUMBLED_STONE);
		this.set(0.6d, LevelSettings.MOSSY);
		this.set(0.8d, LevelSettings.CRUMBLED_MOSSY);
		this.set(1.0d, LevelSettings.TILED_SLATE);
	}
	
	private void set(Double key, LevelSettings value) {
		this.levelSettings.put(key, value);
	}
	
	public LevelSettings getLevelType(int y) {
		if(y > this.firstLevelY) return LevelSettings.OAK;
		if(y < this.lastLevelY) return LevelSettings.TILED_SLATE;
		
		double total = (double)(firstLevelY - lastLevelY);
		double position = (double)(firstLevelY - y) / total;
		
		double key = 0d;
		LevelSettings level = LevelSettings.OAK;
		
		List<Double> keySet = new ArrayList<Double>();
		keySet.addAll(this.levelSettings.keySet());
		Collections.sort(keySet);
		
		for(double k : keySet) {
			if(k > key && k <= position) {
				key = k;
				level = this.levelSettings.get(k);
			}
		}
		
		return level;
	}
	
	@Override
	public ILevelSettings getLevel(int y) {
		return LevelSettings.fromType(getLevelType(y));
	}
}
