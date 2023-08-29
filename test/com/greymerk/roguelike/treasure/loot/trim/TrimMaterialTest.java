package com.greymerk.roguelike.treasure.loot.trim;

import org.junit.jupiter.api.Test;

class TrimMaterialTest {

	@Test
	void testGetNbt() {
	}

	@Test
	void testGetId() {
		TrimMaterial mat = TrimMaterial.DIAMOND;
		String id = TrimMaterial.getId(mat);
		System.out.println("id" + id);
		assert(id.equals("minecraft:diamond"));
	}

	@Test
	void testGetItem() {
	}

}
