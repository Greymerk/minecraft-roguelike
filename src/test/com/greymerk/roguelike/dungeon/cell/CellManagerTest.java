package com.greymerk.roguelike.dungeon.cell;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;

class CellManagerTest {

	@Test
	void testAdd() {
		Coord pos = new Coord(0,0,0);
		CellManager cells = new CellManager();
		assert(cells.get(pos).getState() == CellState.EMPTY);

		cells.add(new Cell(pos, CellState.POTENTIAL));
		assert(cells.get(pos).getState() == CellState.POTENTIAL);

		cells.add(new Cell(pos, CellState.OBSTRUCTED));
		assert(cells.get(pos).getState() == CellState.OBSTRUCTED);
		
		cells.add(new Cell(pos, CellState.POTENTIAL));
		assert(cells.get(pos).getState() == CellState.OBSTRUCTED);

		assert(!(cells.get(pos).getWalls().contains(Cardinal.NORTH)));
		
		Cell toAdd = new Cell(pos, CellState.OBSTRUCTED);
		toAdd.addWall(Cardinal.NORTH);
		cells.add(toAdd);
		
		assert((cells.get(pos).getWalls().contains(Cardinal.NORTH)));
		assert(!(cells.get(pos).getWalls().contains(Cardinal.SOUTH)));
	}
	
	@Test
	void testGet() {
		Coord pos = new Coord(0,0,0);
		CellManager cells = new CellManager();
		assert(cells.get(pos).getState() == CellState.EMPTY);
		
		cells.add(new Cell(pos, CellState.OBSTRUCTED));
		assert(cells.get(pos).getState() == CellState.OBSTRUCTED);
	}
	
	@Test
	void testGetCells() {
		CellManager cells = new CellManager();
		
		assert(cells.getCells().size() == 0);
		
		cells.add(new Cell(new Coord(0,0,0), CellState.POTENTIAL));
		cells.add(new Cell(new Coord(0,0,1), CellState.POTENTIAL));
		cells.add(new Cell(new Coord(0,1,0), CellState.POTENTIAL));
		assert(cells.getCells().size() == 3);
		assert(cells.getCells(CellState.POTENTIAL).size() == 3);
		
		cells.add(new Cell(new Coord(0,0,0), CellState.OBSTRUCTED));
		cells.add(new Cell(new Coord(1,0,0), CellState.OBSTRUCTED));
		cells.add(new Cell(new Coord(0,2,0), CellState.OBSTRUCTED));
		assert(cells.getCells().size() == 5);
		assert(cells.getCells(CellState.POTENTIAL).size() == 2);
		assert(cells.getCells(CellState.OBSTRUCTED).size() == 3);
	}
}
