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
		this.set(3, LevelSettings.DARK_OAK); //30
		this.set(4, LevelSettings.STONE); //20
		this.set(5, LevelSettings.CRUMBLED_STONE); //10
		this.set(6, LevelSettings.MOSSY); //0
		this.set(7, LevelSettings.CRUMBLED_MOSSY); //-10
		this.set(8, LevelSettings.SLATE); //-20
		this.set(9, LevelSettings.TILED_SLATE); //-30
		this.set(10, LevelSettings.NETHER); //-40
		this.set(11, LevelSettings.BLACKSTONE); //-50
	}
	
	private void set(Integer key, LevelSettings value) {
		this.levelSettings.put(key, LevelSettings.fromType(value));
	}

}
