package com.greymerk.roguelike.settings.dungeon;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.greymerk.roguelike.settings.DungeonSettingsBase;
import com.greymerk.roguelike.settings.IDungeonSettings;
import com.greymerk.roguelike.settings.LevelSettings;

public class DungeonSettingsRandom extends DungeonSettingsBase implements IDungeonSettings{
	public DungeonSettingsRandom() {
		super();
		
		IntStream.rangeClosed(0, 11).boxed().collect(Collectors.toList()).forEach(i -> {
			this.set(i, LevelSettings.RANDOM);
		});	
	}
	
	private void set(Integer key, LevelSettings value) {
		this.levelSettings.put(key, LevelSettings.fromType(value));
	}
}
