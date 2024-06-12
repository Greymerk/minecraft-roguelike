package com.greymerk.roguelike.editor.boundingbox;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;

import net.minecraft.nbt.NbtCompound;

class BoundingBoxTest {

	@Test
	void testBoundingBoxCoordCoord() {
		Coord start = new Coord(0,0,0);
		Coord end = new Coord(0,0,5);
		BoundingBox bb = BoundingBox.of(start, end);
		assert(bb.contains(start));
		assert(bb.contains(end));
		assert(!bb.contains(new Coord(1,0,0)));
	}

	@Test
	void testBoundingBoxNbtCompound() {
		Coord start = new Coord(0,0,0);
		Coord end = new Coord(0,0,5);
		BoundingBox bb = BoundingBox.of(start, end);
		NbtCompound tag = bb.getNbt();
		BoundingBox bb2 = new BoundingBox(tag);
		assert(bb.equals(bb2));
	}
	
	@Test
	void testGrowCardinal() {
		BoundingBox bb = BoundingBox.of(new Coord(0,0,0));
		for(Cardinal dir : Cardinal.values()) {
			Coord pos = new Coord(0,0,0);
			pos.add(dir, 1);
			assert(!bb.contains(pos));
			bb.grow(dir, 1);
			assert(bb.contains(pos));	
		}
		
		bb = BoundingBox.of(new Coord(0,0,0));
		for(Cardinal dir : Cardinal.values()) {
			Coord pos = new Coord(0,0,0);
			pos.add(dir, 10);
			assert(!bb.contains(pos));
			bb.grow(dir, 10);
			assert(bb.contains(pos));	
		}
	}
	
	@Test
	void testGrowIterable() {
		BoundingBox bb = BoundingBox.of(new Coord(0,0,0));
		Cardinal.directions.forEach(dir -> {
			Coord pos = new Coord(0,0,0);
			pos.add(dir);
			assert(!bb.contains(pos));
		});
		
		bb.grow(Cardinal.directions, 1);
		Cardinal.directions.forEach(dir -> {
			Coord pos = new Coord(0,0,0);
			pos.add(dir);
			assert(bb.contains(pos));
		});
		
		BoundingBox bb2 = BoundingBox.of(new Coord(0,0,0));
		bb2.grow(Cardinal.directions, 5);
		Cardinal.directions.forEach(dir -> {
			Coord pos = new Coord(0,0,0);
			pos.add(dir, 5);
			assert(bb2.contains(pos));
		});
		
		Cardinal.directions.forEach(dir -> {
			Coord pos = new Coord(0,0,0);
			pos.add(dir, 6);
			assert(!bb2.contains(pos));
		});
		
		Coord pos = new Coord(0,0,0);
		pos.add(Cardinal.UP);
		assert(!bb2.contains(pos));
	}
	
	@Test
	void testMove() {
		BoundingBox bb = BoundingBox.of(new Coord(0,0,0));
		bb.grow(Cardinal.orthogonal(Cardinal.NORTH), 1);
		Coord pos = new Coord(0,0,0);
		pos.add(Cardinal.NORTH);
		assert(!bb.contains(pos));
		bb.add(Cardinal.NORTH, 1);
		assert(bb.contains(pos));
	}

	@Test
	void testCollide() {
		BoundingBox bb1 = BoundingBox.of(new Coord(2, 0, 2), new Coord (-2, 0, -2));
		BoundingBox bb2 = BoundingBox.of(new Coord(0, -1, 0), new Coord(0,1,0));
		assert(bb1.collide(bb2));
		
		BoundingBox bb3 = BoundingBox.of(new Coord(5,0,0), new Coord(10,0,0));
		assert(!bb1.collide(bb3));
	}

	@Test
	void testGetStart() {
		Coord start = new Coord(-5, -6, 3);
		Coord end = new Coord(3, 5, 6);
		BoundingBox bb = BoundingBox.of(start, end);
		assert(bb.getStart().equals(start));
	}

	@Test
	void testGetEnd() {
		Coord start = new Coord(-5, -6, -3);
		Coord end = new Coord(3, 5, 6);
		
		BoundingBox bb = BoundingBox.of(start, end);
		assert(bb.getEnd().equals(end));
	}

	@Test
	void testGetNbt() {
		Coord start = new Coord(-5, 6, 3);
		Coord end = new Coord(3, -5, 6);
		
		BoundingBox bb = BoundingBox.of(start, end);
		NbtCompound tag = bb.getNbt();
		
		assert(tag.contains("start"));
		assert(tag.contains("end"));
		
		NbtCompound s = tag.getCompound("start");
		NbtCompound e = tag.getCompound("end");
		
		assert(Coord.of(s).equals(bb.getStart()));
		assert(Coord.of(e).equals(bb.getEnd()));
	}

	@Test
	void testContains() {
		Coord start = new Coord(-5,-5,-5);
		Coord end = new Coord(5, 5, 5);
		
		BoundingBox bb = BoundingBox.of(start, end);
		assert(bb.contains(new Coord(0,0,0)));
		assert(bb.contains(new Coord(-5,0,0)));
		assert(bb.contains(new Coord(5,0,0)));
		assert(bb.contains(new Coord(1,1,1)));
		
		assert(!bb.contains(new Coord(-10,0,0)));
		assert(!bb.contains(new Coord(0,-10,0)));
		assert(!bb.contains(new Coord(0,0,-10)));
		
	}
	
	@Test
	void testCombine() {
		Coord origin = new Coord(0,0,0);
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		BoundingBox bb2 = BoundingBox.of(origin.copy().add(Cardinal.SOUTH));
		
		bb.combine(bb2);
		assert(bb.contains(origin.copy()));
		assert(bb.contains(origin.copy().add(Cardinal.SOUTH)));
		
		bb = BoundingBox.of(origin.copy());
		bb.grow(Arrays.asList(Cardinal.values()), 10);
		bb.add(Cardinal.NORTH, 10).add(Cardinal.WEST, 10).add(Cardinal.DOWN, 10);
		bb2 = BoundingBox.of(origin.copy());
		bb2.grow(Arrays.asList(Cardinal.values()), 10);
		bb2.add(Cardinal.SOUTH, 10).add(Cardinal.EAST, 10).add(Cardinal.UP, 10);
		bb.combine(bb2);
		assert(bb.contains(origin.copy().add(Cardinal.NORTH, 10).add(Cardinal.WEST, 10).add(Cardinal.UP, 10)));
		assert(bb.contains(origin.copy().add(Cardinal.SOUTH, 10).add(Cardinal.EAST, 10).add(Cardinal.DOWN, 10)));
		assert(bb.getStart().equals(new Coord(-20, -20, -20)));
		assert(bb.getEnd().equals(new Coord(20, 20, 20)));
	}
	
	@Test
	void testHashSet() {
		Set<BoundingBox> boxes = new HashSet<BoundingBox>();
		boxes.add(BoundingBox.of(Coord.ZERO).grow(Cardinal.directions, 2));
		boxes.add(BoundingBox.of(Coord.ZERO).grow(Cardinal.directions, 3));
		assert(boxes.size() == 2);
		boxes.add(BoundingBox.of(Coord.ZERO).grow(Cardinal.directions, 2));
		assert(boxes.size() == 2);
		boxes.add(BoundingBox.of(Coord.ZERO).grow(Cardinal.directions, 4));
		assert(boxes.size() == 3);
	}
}
