package com.greymerk.roguelike.dungeon.room;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;

class CorridorTest {

	@Test
	void getCellsTest() {
		Coord origin = Coord.ZERO;
		Cardinal dir = Cardinal.EAST;
		Corridor room = new Corridor();
		room.setDirection(dir);
		room.setFloorPos(origin.copy());
		room.setWorldPos(origin.copy());
		CellManager cells = room.getCells(dir);

		assert(cells.get(origin).getState() == CellState.OBSTRUCTED);
		assert(cells.get(origin.copy().add(dir)).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.copy().add(Cardinal.left(dir))).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.copy().add(Cardinal.right(dir))).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.copy().add(Cardinal.reverse(dir))).getState() == CellState.EMPTY);
	}

	@Test
	void testEntrancePair() {

	}
}
