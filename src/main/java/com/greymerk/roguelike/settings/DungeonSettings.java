package com.greymerk.roguelike.settings;

import com.greymerk.roguelike.settings.dungeon.DungeonSettingsDefault;

public enum DungeonSettings {

	DEFAULT;
	
	public static IDungeonSettings fromType(DungeonSettings type) {
		switch(type) {
		case DEFAULT: return new DungeonSettingsDefault();
		default: return new DungeonSettingsDefault();
		}
	}
}
