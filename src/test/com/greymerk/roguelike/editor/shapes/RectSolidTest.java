package com.greymerk.roguelike.editor.shapes;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

class RectSolidTest {

	@Test
	void testGet() {
		BoundingBox bb = BoundingBox.of(Coord.ZERO);
		List<Coord> list1 = new RectSolid(bb).get();
		assert(list1.size() == 1);
		assert(list1.getFirst().equals(Coord.ZERO));
		
		BoundingBox bb2 = BoundingBox.of(Coord.ZERO).grow(Cardinal.directions);
		List<Coord> list2 = new RectSolid(bb2).get();
		
		assert(list2.size() == 9);
		assert(list2.contains(Coord.ZERO));
		assert(list2.contains(Coord.ZERO.add(Cardinal.EAST)));
		
		assert(!list2.contains(Coord.ZERO.add(Cardinal.UP)));
	}

}
