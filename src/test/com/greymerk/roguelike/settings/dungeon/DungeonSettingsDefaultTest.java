package com.greymerk.roguelike.settings.dungeon;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.settings.IDungeonSettings;

class DungeonSettingsDefaultTest {

	@Test
	void test() {
		IDungeonSettings settings = new DungeonSettingsDefault();
		for(int i = 80; i >= -50; i-=10) {
			System.out.println(settings.getSettings(i));
		}
	}
	
	@Test
	void testAboveSeaLevel() {
		IDungeonSettings settings = new DungeonSettingsDefault(100, -50);
		for(int i = 100; i >= -50; i-=10) {
			System.out.println(settings.getSettings(i));
		}
	}

}
