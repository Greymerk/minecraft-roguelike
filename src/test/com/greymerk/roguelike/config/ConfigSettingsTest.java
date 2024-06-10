package com.greymerk.roguelike.config;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.util.MixedKey;

class ConfigSettingsTest {

	@Test
	void test() {
		ConfigSettings config = ConfigSettings.getInstance();
		Boolean b = config.get(MixedKey.ofBoolean(Config.MOB_DROPS.keyOf()));
		assert(b == true);
		
		assert(config.has(MixedKey.ofBoolean(Config.MOB_DROPS.keyOf())));
		assert(!config.has(MixedKey.ofDouble(Config.MOB_DROPS.keyOf())));
		
		
		Double d = config.get(MixedKey.ofDouble(Config.FREQUENCY.keyOf()));
		assert(d == 1.0);
		config.put(MixedKey.ofDouble(Config.FREQUENCY.keyOf()), 2.0);
		Double d2 = config.get(MixedKey.ofDouble(Config.FREQUENCY.keyOf()));
		assert(d2 == 2.0);
	}

}
