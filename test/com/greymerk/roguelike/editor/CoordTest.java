package com.greymerk.roguelike.editor;

import org.junit.jupiter.api.Test;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

class CoordTest {

	@Test
	void testHashCode() {
	}

	@Test
	void testCoordIntIntInt() {
	}

	@Test
	void testCoordCoord() {
	}

	@Test
	void testCoordNbtCompound() {
	}

	@Test
	void testCoordBlockPos() {
	}

	@Test
	void testCopy() {
	}

	@Test
	void testGetX() {
	}

	@Test
	void testGetY() {
	}

	@Test
	void testGetZ() {
	}

	@Test
	void testAddCardinalInt() {
	}

	@Test
	void testAddCoord() {
	}

	@Test
	void testSub() {
	}

	@Test
	void testAddCardinal() {
	}

	@Test
	void testMul() {
	}

	void testDot() {
		{
			Coord a = new Coord(0, 0, 0);
			Coord b = new Coord(1, 2, 3);
			assert(a.dot(b) == 0);
		}
		{
			Coord a = new Coord(-2, 5, -6);
			Coord b = new Coord(1, 2, 3);
			assert(a.dot(b) == -10);
		}
	}
	
	@Test
	void testProject() {
		{
			Coord a = new Coord(1,2,-3);
			Coord b = new Coord(3,-4,5);
			Coord answer = new Coord(-2,1,-2);
			assert(a.project(b).equals(answer));
		}
		
		{
			Coord a = new Coord(1,2,3);
			Coord b = new Coord(3,4,5);
			Coord answer = new Coord(1,2,2);
			assert(a.project(b).equals(answer));
		}
	}
	
	@Test
	void testScalar() {
		{
			Coord a = new Coord(1,2,3);
			Coord b = new Coord(3,4,5);
			double answer = 3.676955262170047;
			assert(round(a.scalar(b)) == round(answer));
		}
	}
	
	@Test
	void testMagnitude() {
		{
			Coord c = new Coord(3,4,5);
			double answer = 7.071067811865475;
			assert(round(c.magnitude()) == round(answer));
		}
	}
	
	@Test
	void testUnit() {
	}

	@Test
	void testDistance() {
	}

	@Test
	void testManhattanDistance() {
		Coord a = new Coord(3, 2, 5);
		Coord b = new Coord(4, 1, 7);
		assert(a.manhattanDistance(b) == 4);
		
		a = new Coord(-10, 0, 0);
		b = new Coord(0,0,0);
		assert(a.manhattanDistance(b) == 10);
		
		a = new Coord(-10, 0, 0);
		b = new Coord(0,0,10);
		assert(a.manhattanDistance(b) == 20);
	}

	@Test
	void testDirTo() {
	}

	@Test
	void testCorrect() {
	}

	@Test
	void testToString() {
	}

	@Test
	void testEqualsObject() {
	}

	@Test
	void testGetBlockPos() {
	}
	
	@Test
	void testGetChunkPos() {
		ChunkPos cp = new ChunkPos(0,0);
		BlockPos bp = cp.getCenterAtY(0);
		Coord pos = new Coord(bp);
		assert(cp.equals(pos.getChunkPos()));
		
		cp = new ChunkPos(3, -5);
		bp = cp.getCenterAtY(0);
		pos = new Coord(bp);
		assert(cp.equals(pos.getChunkPos()));
		
	}

	@Test
	void testGetNbt() {
	}
	
	private double round(double d) {
		return (double)Math.round(d * 100)/100;
	}

}
