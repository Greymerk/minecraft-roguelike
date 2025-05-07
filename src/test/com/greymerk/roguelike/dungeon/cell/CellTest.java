package com.greymerk.roguelike.dungeon.cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

class CellTest {

	@Test
	void testGetState() {
		Coord pos = Coord.ZERO;
		Cell c = new Cell(pos, CellState.OBSTRUCTED, null);
		assert(c.getState() == CellState.OBSTRUCTED);
		
		Cell c2 = new Cell(pos, CellState.EMPTY, null);
		assert(c2.getState() == CellState.EMPTY);
		
		Cell c3 = new Cell(pos, CellState.POTENTIAL, null);
		assert(c3.getState() == CellState.POTENTIAL);
	}
	
	@Test
	void testReplace() {
		Coord pos = Coord.ZERO;
		Cell c = new Cell(pos, CellState.EMPTY, null);
		assert(!(c.getWalls().contains(Cardinal.NORTH)));
		
		Cell c2 = new Cell(pos, CellState.OBSTRUCTED, null);
		c2.addWall(Cardinal.NORTH);
		c.replace(c2);
		assert((c.getWalls().contains(Cardinal.NORTH)));
	}

	@Test
	void testGetLevelOffset() {
		Coord origin = Coord.ZERO;
		Cell c1 = Cell.of(origin.copy(), CellState.OBSTRUCTED, null);
		assert(c1.getLevelOffset() == 0);

		Cell c2 = Cell.of(origin.copy().add(Cardinal.DOWN), CellState.OBSTRUCTED, null);
		assert(c2.getLevelOffset() == 1);
		
		Cell c3 = Cell.of(origin.copy().add(Cardinal.UP), CellState.OBSTRUCTED, null);
		assert(c3.getLevelOffset() == -1);

		Cell c4 = Cell.of(origin.copy().add(Cardinal.DOWN, 3), CellState.OBSTRUCTED, null);
		assert(c4.getLevelOffset() == 3);
	}
	
	@Test
	void testGetWorldPos() {
		Coord worldPos = Coord.of(5, 10, -10);
		Cell c = Cell.of(Coord.of(3, 0, -2), CellState.OBSTRUCTED, null);
		assert(c.getWorldPos(worldPos).equals(Coord.of(23, 10, -22)));
	}
	
	@Test
	void testConnectedTo() {
		Cell a = Cell.of(Coord.ZERO, CellState.OBSTRUCTED, null);
		Cell b = Cell.of(Coord.ZERO.add(Cardinal.EAST), CellState.OBSTRUCTED, null);
		assert(a.connectedTo(b));
		
		Cell c = Cell.of(Coord.ZERO.add(Cardinal.EAST, 2), CellState.OBSTRUCTED, null);
		assert(!a.connectedTo(c));
	}
	
	@Test
	void testConnectToWithWalls() {
		Cell a = Cell.of(Coord.ZERO, CellState.OBSTRUCTED, null, List.of(Cardinal.EAST));
		Cell b = Cell.of(Coord.ZERO.add(Cardinal.EAST), CellState.OBSTRUCTED, null, List.of(Cardinal.WEST));
		
		assert(!a.connectedTo(b));
		
		
	}
	
	@Test
	void testCodec() {
		Cell c = Cell.of(Coord.of(1, 2, 3), CellState.OBSTRUCTED, null, List.of(Cardinal.NORTH, Cardinal.EAST));
		
		DataResult<JsonElement> enc = Cell.CODEC.encodeStart(JsonOps.INSTANCE, c);
		
		Cell c2 = Cell.CODEC.decode(JsonOps.INSTANCE, enc.getOrThrow()).getOrThrow().getFirst();
		
		assert(c.equals(c2));
	}
	
	@Test
	void testSort() {
		
		var cells = new ArrayList<Cell>(List.of(Cell.of(Coord.ZERO.add(Cardinal.EAST, 5), CellState.OBSTRUCTED, null),
				Cell.of(Coord.ZERO.add(Cardinal.EAST, 4).add(Cardinal.NORTH), CellState.OBSTRUCTED, null),
				Cell.of(Coord.ZERO.add(Cardinal.EAST, 4).add(Cardinal.SOUTH), CellState.OBSTRUCTED, null)));
		System.out.println(cells);
		
		Coord origin = Coord.ZERO;
		Collections.sort(cells, (a, b) -> {
			Coord ac = a.getWorldPos(origin);
			Coord bc = b.getWorldPos(origin);
			int diff = ac.manhattanDistance(origin) - bc.manhattanDistance(origin);
			if(diff != 0) return diff;
			int aMax = Math.max(ac.getX(), ac.getZ());
			int bMax = Math.max(bc.getX(), bc.getZ());
			return aMax - bMax;
		});
		
		System.out.println(cells);
		
		Collections.reverse(cells);
		System.out.println(cells);
	}
	
}
