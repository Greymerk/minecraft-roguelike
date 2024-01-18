package com.greymerk.roguelike.settings.dungeon;

import com.greymerk.roguelike.settings.DungeonSettingsBase;
import com.greymerk.roguelike.settings.IDungeonSettings;
import com.greymerk.roguelike.settings.LevelSettings;

public class DungeonSettingsDefault extends DungeonSettingsBase implements IDungeonSettings {

	public DungeonSettingsDefault() {
		super();
		
		this.set(0, LevelSettings.OAK); //60+
		this.set(1, LevelSettings.OAK); //50
		this.set(2, LevelSettings.SPRUCE); //40
		this.set(3, LevelSettings.SPRUCE); //30
		this.set(4, LevelSettings.DARK_OAK); //20
		this.set(5, LevelSettings.STONE); //10
		this.set(6, LevelSettings.STONE); //0
		this.set(7, LevelSettings.CRUMBLED_STONE); //-10
		this.set(8, LevelSettings.MOSSY); //-20
		this.set(9, LevelSettings.CRUMBLED_MOSSY); //-30
		this.set(10, LevelSettings.CRUMBLED_MOSSY); //-40
		this.set(11, LevelSettings.TILED_SLATE); //-50
	}
	
	private void set(Integer key, LevelSettings value) {
		this.levelSettings.put(key, LevelSettings.fromType(value));
	}

}
