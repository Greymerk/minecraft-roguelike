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
		System.out.println(c2.getWalls());
		c.replace(c2);
		System.out.println(c.getWalls());
		assert((c.getWalls().contains(Cardinal.NORTH)));
		
	}
}
