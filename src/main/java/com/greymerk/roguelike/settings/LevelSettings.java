package com.greymerk.roguelike.settings;

import com.greymerk.roguelike.settings.level.LevelSettingsBlackstone;
import com.greymerk.roguelike.settings.level.LevelSettingsCopper;
import com.greymerk.roguelike.settings.level.LevelSettingsCrumbledMossy;
import com.greymerk.roguelike.settings.level.LevelSettingsCrumbledStone;
import com.greymerk.roguelike.settings.level.LevelSettingsDarkOak;
import com.greymerk.roguelike.settings.level.LevelSettingsDeepslateBrick;
import com.greymerk.roguelike.settings.level.LevelSettingsDefault;
import com.greymerk.roguelike.settings.level.LevelSettingsMossy;
import com.greymerk.roguelike.settings.level.LevelSettingsNether;
import com.greymerk.roguelike.settings.level.LevelSettingsOak;
import com.greymerk.roguelike.settings.level.LevelSettingsRandom;
import com.greymerk.roguelike.settings.level.LevelSettingsSpruce;
import com.greymerk.roguelike.settings.level.LevelSettingsStone;
import com.greymerk.roguelike.settings.level.LevelSettingsTiledDeepslate;

public enum LevelSettings {
	
	DEFAULT, OAK, SPRUCE, DARK_OAK, COPPER, 
	STONE, CRUMBLED_STONE, MOSSY, CRUMBLED_MOSSY,
	SLATE, TILED_SLATE, NETHER, BLACKSTONE,
	RANDOM;
	
	public static ILevelSettings fromType(LevelSettings type) {
		
		switch(type) {
		case DEFAULT: return new LevelSettingsDefault();
		case BLACKSTONE: return new LevelSettingsBlackstone();
		case COPPER: return new LevelSettingsCopper();
		case CRUMBLED_MOSSY: return new LevelSettingsCrumbledMossy();
		case CRUMBLED_STONE: return new LevelSettingsCrumbledStone();
		case DARK_OAK: return new LevelSettingsDarkOak();
		case MOSSY: return new LevelSettingsMossy();
		case NETHER: return new LevelSettingsNether();
		case OAK: return new LevelSettingsOak();
		case SLATE: return new LevelSettingsDeepslateBrick();
		case SPRUCE: return new LevelSettingsSpruce();
		case STONE: return new LevelSettingsStone();
		case TILED_SLATE: return new LevelSettingsTiledDeepslate();
		case RANDOM: return new LevelSettingsRandom();
		default: return new LevelSettingsDefault();
		}
	}
	
	public static ILevelSettings get(String name) {
		LevelSettings type = LevelSettings.valueOf(name.toUpperCase());
		return LevelSettings.fromType(type);
	}
}
