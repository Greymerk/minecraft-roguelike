package com.greymerk.roguelike.util;

import org.junit.jupiter.api.Test;

class MixedMapTest {

	@Test
	void test() {
		MixedKey<Integer> intKey = new MixedKey<Integer>(Integer.class, "NumberKey");
		MixedMap map = new MixedMap();
		map.putMixed(intKey, 1);
		assert(map.getMixed(intKey) == 1);
		
		MixedKey<String> strKey = new MixedKey<String>(String.class, "StringKey");
		map.putMixed(strKey, "test");
		assert(map.getMixed(strKey) == "test");
	}

}
