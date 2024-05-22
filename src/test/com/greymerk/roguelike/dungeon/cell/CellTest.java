package com.greymerk.roguelike.dungeon.cell;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;

class CellTest {

	@Test
	void testGetState() {
		Coord pos = new Coord(0,0,0);
		Cell c = new Cell(pos, CellState.OBSTRUCTED);
		assert(c.getState() == CellState.OBSTRUCTED);
		
		Cell c2 = new Cell(pos, CellState.EMPTY);
		assert(c2.getState() == CellState.EMPTY);
		
		Cell c3 = new Cell(pos, CellState.POTENTIAL);
		assert(c3.getState() == CellState.POTENTIAL);
	}
	
	@Test
	void testReplace() {
		Coord pos = new Coord(0,0,0);
		Cell c = new Cell(pos, CellState.EMPTY);
		assert(!(c.getWalls().contains(Cardinal.NORTH)));
		
		Cell c2 = new Cell(pos, CellState.OBSTRUCTED);
		c2.addWall(Cardinal.NORTH);
		c.replace(c2);
		assert((c.getWalls().contains(Cardinal.NORTH)));
	}

	@Test
	void testGetLevelOffset() {
		Coord origin = new Coord(0,0,0);
		Cell c1 = Cell.of(origin.copy(), CellState.OBSTRUCTED);
		assert(c1.getLevelOffset() == 0);

		Cell c2 = Cell.of(origin.copy().add(Cardinal.DOWN), CellState.OBSTRUCTED);
		assert(c2.getLevelOffset() == 1);
		
		Cell c3 = Cell.of(origin.copy().add(Cardinal.UP), CellState.OBSTRUCTED);
		assert(c3.getLevelOffset() == -1);

		Cell c4 = Cell.of(origin.copy().add(Cardinal.DOWN, 3), CellState.OBSTRUCTED);
		assert(c4.getLevelOffset() == 3);
	}
	
	@Test
	void testGetWorldPos() {
		Coord worldPos = new Coord(5, 10, -10);
		Cell c = Cell.of(new Coord(3, 0, -2), CellState.OBSTRUCTED);
		assert(c.getWorldPos(worldPos).equals(new Coord(23, 10, -22)));
	}
}
