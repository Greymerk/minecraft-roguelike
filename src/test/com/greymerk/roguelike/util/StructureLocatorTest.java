package com.greymerk.roguelike.util;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.editor.Coord;

class StructureLocatorTest {

	@Test
	void testHasStructure() {
		
		long seed = 118167;
		
		assert(StructureLocator.hasStructure(seed, StructureLocator.VILLAGE, new Coord(112, 0, 48).getChunkPos()));
		
		assert(!StructureLocator.hasStructure(seed, StructureLocator.VILLAGE, new Coord(150, 0, 48).getChunkPos()));
		
		assert(StructureLocator.hasStructure(seed, StructureLocator.TRIAL_CHAMBER, new Coord(336, 0, 336).getChunkPos()));
		
		assert(!StructureLocator.hasStructure(seed, StructureLocator.TRIAL_CHAMBER, new Coord(300, 0, 336).getChunkPos()));
	}
	
	@Test
	void testScan() {
		long seed = 118167;
		
		Coord origin = new Coord(336, 0 , 336).freeze();
		
		Set<Coord> locations = StructureLocator.scan(seed, origin, StructureLocator.TRIAL_CHAMBER, 100);
		
		locations.forEach(pos -> {
			System.out.println(pos);
		});
		
		assert(locations.contains(Coord.of(origin.getChunkPos().getCenterAtY(0))));
	}
}
