package com.greymerk.roguelike.editor;

import org.junit.jupiter.api.Test;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

class CoordTest {

	@Test
	void testCoordBlockPos() {
		Coord a = Coord.of(1, 2, 3);
		BlockPos bp = a.getBlockPos();
		Coord b = Coord.of(bp);
		assert(a.equals(b));
	}

	@Test
	void testCopy() {
		Coord a = Coord.of(1, 2, 3);
		assert(a.equals(a.copy()));
	}

	@Test
	void testGetX() {
		Coord a = Coord.of(1, 2, 3);
		assert(a.getX() == 1);
	}

	@Test
	void testGetY() {
		Coord a = Coord.of(1, 2, 3);
		assert(a.getY() == 2);
	}

	@Test
	void testGetZ() {
		Coord a = Coord.of(1, 2, 3);
		assert(a.getZ() == 3);
	}

	@Test
	void testAddCardinalInt() {
		Coord a = Coord.of(0,0,0);
		a.add(Cardinal.EAST, 2);
		assert(a.equals(Coord.of(2,0,0)));
		a.add(Cardinal.SOUTH, 2);
		assert(a.equals(Coord.of(2,0,2)));
		a.add(Cardinal.UP, 2);
		assert(a.equals(Coord.of(2,2,2)));
		a.add(Cardinal.WEST, 2);
		assert(a.equals(Coord.of(0,2,2)));
		a.add(Cardinal.NORTH, 2);
		assert(a.equals(Coord.of(0,2,0)));
		a.add(Cardinal.DOWN, 2);
		assert(a.equals(Coord.ZERO));
	}

	@Test
	void testAddCoord() {
		Coord a = Coord.of(1, 2, 3);
		Coord b = Coord.of(4, 5, -6);
		Coord c = a.add(b);
		assert(c.equals(Coord.of(5, 7, -3)));
	}

	@Test
	void testSub() {
		Coord a = Coord.of(1, 2, 3);
		Coord b = Coord.of(4, 5, -6);
		Coord c = a.sub(b);
		assert(c.equals(Coord.of(-3, -3, 9)));
	}

	@Test
	void testAddCardinal() {
		Coord a = Coord.of(0,0,0);
		a.add(Cardinal.EAST);
		assert(a.equals(Coord.of(1,0,0)));
		a.add(Cardinal.SOUTH);
		assert(a.equals(Coord.of(1,0,1)));
		a.add(Cardinal.UP);
		assert(a.equals(Coord.of(1,1,1)));
		a.add(Cardinal.WEST);
		assert(a.equals(Coord.of(0,1,1)));
		a.add(Cardinal.NORTH);
		assert(a.equals(Coord.of(0,1,0)));
		a.add(Cardinal.DOWN);
		assert(a.equals(Coord.ZERO));
	}

	@Test
	void testMul() {
		Coord a = Coord.of(2, 4, 6);
		Coord b = Coord.of(3, 2, -2);
		Coord c = a.mul(b);
		assert(c.equals(Coord.of(6, 8, -12)));
	}

	void testDot() {
		{
			Coord a = Coord.of(0, 0, 0);
			Coord b = Coord.of(1, 2, 3);
			assert(a.dot(b) == 0);
		}
		{
			Coord a = Coord.of(-2, 5, -6);
			Coord b = Coord.of(1, 2, 3);
			assert(a.dot(b) == -10);
		}
	}
	
	@Test
	void testProject() {
		{
			Coord a = Coord.of(1,2,-3);
			Coord b = Coord.of(3,-4,5);
			Coord answer = Coord.of(-2,1,-2);
			assert(a.project(b).equals(answer));
		}
		
		{
			Coord a = Coord.of(1,2,3);
			Coord b = Coord.of(3,4,5);
			Coord answer = Coord.of(1,2,2);
			assert(a.project(b).equals(answer));
		}
	}
	
	@Test
	void testScalar() {
		{
			Coord a = Coord.of(1,2,3);
			Coord b = Coord.of(3,4,5);
			double answer = 3.676955262170047;
			assert(round(a.scalar(b)) == round(answer));
		}
	}
	
	@Test
	void testMagnitude() {
		{
			Coord c = Coord.of(3,4,5);
			double answer = 7.071067811865475;
			assert(round(c.magnitude()) == round(answer));
		}
	}
	
	@Test
	void testUnit() {
		Coord a = Coord.of(1, -2, -3);
		assert(a.unit().equals(Coord.of(1, -1, -1)));
	}

	@Test
	void testDistance() {
		Coord a = Coord.of(10, -50, 80);
		Coord b = Coord.of(-40, 20, -30);
		assert((int)Math.floor(a.distance(b)) == 120);
	}

	@Test
	void testManhattanDistance() {
		Coord a = Coord.of(3, 2, 5);
		Coord b = Coord.of(4, 1, 7);
		assert(a.manhattanDistance(b) == 4);
		
		a = Coord.of(-10, 0, 0);
		b = Coord.ZERO;
		assert(a.manhattanDistance(b) == 10);
		
		a = Coord.of(-10, 0, 0);
		b = Coord.of(0,0,10);
		assert(a.manhattanDistance(b) == 20);
	}

	@Test
	void testDirTo() {
		Coord a = Coord.of(20, -50, 10);
		Coord b = Coord.of(0, 20, 300);
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
	
	@Test
	void testFreeze() {
		Coord c = Coord.ZERO.freeze();
		
		Coord c2 = c.add(Cardinal.EAST);
		assert(c.equals(Coord.ZERO));
		assert(c2.equals(Coord.ZERO.add(Cardinal.EAST)));
		
		c2 = c.add(Cardinal.EAST, 2);
		assert(c.equals(Coord.ZERO));
		assert(c2.equals(Coord.ZERO.add(Cardinal.EAST, 2)));
		
		c2 = c.add(Coord.of(0,0,1));
		assert(c.equals(Coord.ZERO));
		assert(c2.equals(Coord.of(0,0,1)));
				
		c2 = c.sub(Coord.of(0,0,1));
		assert(c.equals(Coord.ZERO));
		assert(c2.equals(Coord.ZERO.sub(Coord.of(0,0,1))));
	}
	
	@Test
	void testCodec() {
		final Coord c = Coord.of(12, -4, 0);
		
		final DataResult<NbtElement> enc = Coord.CODEC.encodeStart(NbtOps.INSTANCE, c);
		
		NbtElement e = enc.getOrThrow();
		
		final DataResult<Pair<Coord, NbtElement>> dec = Coord.CODEC.decode(NbtOps.INSTANCE, e);
		
		Coord d = dec.getOrThrow().getFirst();
		
		assert(c.equals(d));
	}
}
