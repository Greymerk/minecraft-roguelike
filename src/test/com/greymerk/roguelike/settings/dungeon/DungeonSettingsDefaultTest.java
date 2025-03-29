package com.greymerk.roguelike.settings.dungeon;

import com.greymerk.roguelike.settings.LevelSettings;

class DungeonSettingsDefaultTest {

	//@Test
	void test() {
		int firstY = 230;
		int lastY = -50;
		DungeonSettingsDefault settings = new DungeonSettingsDefault(firstY, lastY);
		for(int i = firstY; i >= lastY; i-=10) {
			LevelSettings level = settings.getLevelType(i);
			System.out.println("y:" + i + " " + level.name());
		}
	}

}
