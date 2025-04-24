package com.greymerk.roguelike.editor.boundingbox;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;

class BoundingBoxTest {

	@Test
	void testBoundingBoxCoordCoord() {
		Coord start = Coord.ZERO;
		Coord end = Coord.ZERO.withZ(5);
		BoundingBox bb = BoundingBox.of(start, end);
		assert(bb.contains(start));
		assert(bb.contains(end));
		assert(!bb.contains(Coord.ZERO.withX(1)));
	}

	@Test
	void testGrowCardinal() {
		BoundingBox bb = BoundingBox.of(Coord.ZERO);
		for(Cardinal dir : Cardinal.values()) {
			Coord pos = Coord.ZERO;
			pos.add(dir, 1);
			assert(!bb.contains(pos));
			bb.grow(dir, 1);
			assert(bb.contains(pos));	
		}
		
		bb = BoundingBox.of(Coord.ZERO);
		for(Cardinal dir : Cardinal.values()) {
			Coord pos = Coord.ZERO;
			pos.add(dir, 10);
			assert(!bb.contains(pos));
			bb.grow(dir, 10);
			assert(bb.contains(pos));	
		}
	}
	
	@Test
	void testGrowIterable() {
		BoundingBox bb = BoundingBox.of(Coord.ZERO);
		Cardinal.directions.forEach(dir -> {
			Coord pos = Coord.ZERO;
			pos.add(dir);
			assert(!bb.contains(pos));
		});
		
		bb.grow(Cardinal.directions, 1);
		Cardinal.directions.forEach(dir -> {
			Coord pos = Coord.ZERO;
			pos.add(dir);
			assert(bb.contains(pos));
		});
		
		BoundingBox bb2 = BoundingBox.of(Coord.ZERO);
		bb2.grow(Cardinal.directions, 5);
		Cardinal.directions.forEach(dir -> {
			Coord pos = Coord.ZERO;
			pos.add(dir, 5);
			assert(bb2.contains(pos));
		});
		
		Cardinal.directions.forEach(dir -> {
			Coord pos = Coord.ZERO;
			pos.add(dir, 6);
			assert(!bb2.contains(pos));
		});
		
		Coord pos = Coord.ZERO;
		pos.add(Cardinal.UP);
		assert(!bb2.contains(pos));
	}
		
	@Test
	void testMove() {
		BoundingBox bb = BoundingBox.of(Coord.ZERO);
		bb.grow(Cardinal.orthogonal(Cardinal.NORTH), 1);
		Coord pos = Coord.ZERO;
		pos.add(Cardinal.NORTH);
		assert(!bb.contains(pos));
		bb.add(Cardinal.NORTH, 1);
		assert(bb.contains(pos));
	}

	@Test
	void testCollide() {
		BoundingBox bb1 = BoundingBox.of(Coord.of(2, 0, 2), Coord.of(-2, 0, -2));
		BoundingBox bb2 = BoundingBox.of(Coord.of(0, -1, 0), Coord.of(0,1,0));
		assert(bb1.collide(bb2));
		
		BoundingBox bb3 = BoundingBox.of(Coord.of(5,0,0), Coord.of(10,0,0));
		assert(!bb1.collide(bb3));
	}

	@Test
	void testGetStart() {
		Coord start = Coord.of(-5, -6, 3);
		Coord end = Coord.of(3, 5, 6);
		BoundingBox bb = BoundingBox.of(start, end);
		assert(bb.getStart().equals(start));
	}

	@Test
	void testGetEnd() {
		Coord start = Coord.of(-5, -6, -3);
		Coord end = Coord.of(3, 5, 6);
		
		BoundingBox bb = BoundingBox.of(start, end);
		assert(bb.getEnd().equals(end));
	}

	@Test
	void testContains() {
		Coord start = Coord.of(-5,-5,-5);
		Coord end = Coord.of(5, 5, 5);
		
		BoundingBox bb = BoundingBox.of(start, end);
		assert(bb.contains(Coord.ZERO));
		assert(bb.contains(Coord.of(-5,0,0)));
		assert(bb.contains(Coord.of(5,0,0)));
		assert(bb.contains(Coord.of(1,1,1)));
		
		assert(!bb.contains(Coord.of(-10,0,0)));
		assert(!bb.contains(Coord.of(0,-10,0)));
		assert(!bb.contains(Coord.of(0,0,-10)));
		
	}
	
	@Test
	void testCombine() {
		Coord origin = Coord.ZERO;
		
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
		assert(bb.getStart().equals(Coord.of(-20, -20, -20)));
		assert(bb.getEnd().equals(Coord.of(20, 20, 20)));
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
	
	@Test
	void testCodec() {
		Coord s = Coord.of(5, 3, -2);
		Coord e = Coord.of(-9, 2, 18);
		BoundingBox bb = BoundingBox.of(s, e);
		
		final DataResult<NbtElement> enc = BoundingBox.CODEC.encodeStart(NbtOps.INSTANCE, bb);
		NbtElement nbt = enc.getOrThrow();
		final DataResult<Pair<BoundingBox, NbtElement>> dec = BoundingBox.CODEC.decode(NbtOps.INSTANCE, nbt);
		BoundingBox bb2 = dec.getOrThrow().getFirst();
		
		assert(bb.equals(bb2));		
	}
}
