package com.greymerk.roguelike.editor;

import org.junit.jupiter.api.Test;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

class CoordTest {

	@Test
	void testCoordNbtCompound() {
		Coord a = new Coord(1, 2, 3);
		NbtElement nbt = a.getNbt();
		Coord b = Coord.of((NbtCompound)nbt);
		assert(a.equals(b));
	}

	@Test
	void testCoordBlockPos() {
		Coord a = new Coord(1, 2, 3);
		BlockPos bp = a.getBlockPos();
		Coord b = Coord.of(bp);
		assert(a.equals(b));
	}

	@Test
	void testCopy() {
		Coord a = new Coord(1, 2, 3);
		assert(a.equals(a.copy()));
	}

	@Test
	void testGetX() {
		Coord a = new Coord(1, 2, 3);
		assert(a.getX() == 1);
	}

	@Test
	void testGetY() {
		Coord a = new Coord(1, 2, 3);
		assert(a.getY() == 2);
	}

	@Test
	void testGetZ() {
		Coord a = new Coord(1, 2, 3);
		assert(a.getZ() == 3);
	}

	@Test
	void testAddCardinalInt() {
		Coord a = new Coord(0,0,0);
		a.add(Cardinal.EAST, 2);
		assert(a.equals(new Coord(2,0,0)));
		a.add(Cardinal.SOUTH, 2);
		assert(a.equals(new Coord(2,0,2)));
		a.add(Cardinal.UP, 2);
		assert(a.equals(new Coord(2,2,2)));
		a.add(Cardinal.WEST, 2);
		assert(a.equals(new Coord(0,2,2)));
		a.add(Cardinal.NORTH, 2);
		assert(a.equals(new Coord(0,2,0)));
		a.add(Cardinal.DOWN, 2);
		assert(a.equals(new Coord(0,0,0)));
	}

	@Test
	void testAddCoord() {
		Coord a = new Coord(1, 2, 3);
		Coord b = new Coord(4, 5, -6);
		Coord c = a.add(b);
		assert(c.equals(new Coord(5, 7, -3)));
	}

	@Test
	void testSub() {
		Coord a = new Coord(1, 2, 3);
		Coord b = new Coord(4, 5, -6);
		Coord c = a.sub(b);
		assert(c.equals(new Coord(-3, -3, 9)));
	}

	@Test
	void testAddCardinal() {
		Coord a = new Coord(0,0,0);
		a.add(Cardinal.EAST);
		assert(a.equals(new Coord(1,0,0)));
		a.add(Cardinal.SOUTH);
		assert(a.equals(new Coord(1,0,1)));
		a.add(Cardinal.UP);
		assert(a.equals(new Coord(1,1,1)));
		a.add(Cardinal.WEST);
		assert(a.equals(new Coord(0,1,1)));
		a.add(Cardinal.NORTH);
		assert(a.equals(new Coord(0,1,0)));
		a.add(Cardinal.DOWN);
		assert(a.equals(new Coord(0,0,0)));
	}

	@Test
	void testMul() {
		Coord a = new Coord(2, 4, 6);
		Coord b = new Coord(3, 2, -2);
		Coord c = a.mul(b);
		assert(c.equals(new Coord(6, 8, -12)));
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
		Coord a = new Coord(1, -2, -3);
		assert(a.unit().equals(new Coord(1, -1, -1)));
	}

	@Test
	void testDistance() {
		Coord a = new Coord(10, -50, 80);
		Coord b = new Coord(-40, 20, -30);
		assert((int)Math.floor(a.distance(b)) == 120);
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
		Coord a = new Coord(20, -50, 10);
		Coord b = new Coord(0, 20, 300);
		assert(a.dirTo(b) == Cardinal.SOUTH);
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
		Coord pos = Coord.of(bp);
		assert(cp.equals(pos.getChunkPos()));
		
		cp = new ChunkPos(3, -5);
		bp = cp.getCenterAtY(0);
		pos = Coord.of(bp);
		assert(cp.equals(pos.getChunkPos()));
		
	}

	@Test
	void testGetNbt() {
	}
	
	private double round(double d) {
		return (double)Math.round(d * 100)/100;
	}

}
