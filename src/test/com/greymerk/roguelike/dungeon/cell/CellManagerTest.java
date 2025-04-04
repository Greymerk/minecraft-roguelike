package com.greymerk.roguelike.dungeon.cell;

import java.util.List;

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
	void testGetByType() {
		CellManager cells = new CellManager();
		assert(cells.getCells(CellState.OBSTRUCTED).isEmpty());
		cells.add(Cell.of(Coord.ZERO, CellState.OBSTRUCTED));
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.EAST), CellState.OBSTRUCTED));
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.NORTH), CellState.OBSTRUCTED));
		assert(cells.getCells(CellState.POTENTIAL).isEmpty());
		assert(cells.getCells(CellState.OBSTRUCTED).size() == 3);
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.WEST), CellState.POTENTIAL));
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.SOUTH), CellState.POTENTIAL));
		assert(cells.getCells(CellState.OBSTRUCTED).size() == 3);
		assert(cells.getCells(CellState.POTENTIAL).size() == 2);
	}
	
	@Test
	void testGetBranches() {
		CellManager cells = new CellManager();
		Cell a = Cell.of(Coord.ZERO, CellState.OBSTRUCTED);
		Cell b = Cell.of(Coord.ZERO.add(Cardinal.EAST), CellState.OBSTRUCTED);
		cells.add(a);
		cells.add(b);
		assert(cells.getBranches().size() == 1);
		
		Cell c = Cell.of(Coord.ZERO.add(Cardinal.NORTH, 2), CellState.OBSTRUCTED);
		cells.add(c);
		assert(cells.getBranches().size() == 2);
	}
	
	@Test
	void testGetNearestPotentials() {
		CellManager cells = new CellManager();
		Cell a = Cell.of(Coord.ZERO, CellState.OBSTRUCTED);
		Cell b = Cell.of(Coord.ZERO.add(Cardinal.EAST, 5), CellState.OBSTRUCTED);
		cells.add(a);
		cells.add(b);
		
		cells.getCells(CellState.OBSTRUCTED).forEach(c -> {
			Cardinal.directions.forEach(dir -> {
				cells.add(Cell.of(c.getFloorPos().copy().add(dir), CellState.POTENTIAL));
			});
		});
		
		List<Cell> nearest = cells.getNearestPotentials();
		assert(nearest.contains(Cell.of(Coord.of(1, 0, 0), CellState.POTENTIAL)));
		assert(nearest.contains(Cell.of(Coord.of(4, 0, 0), CellState.POTENTIAL)));
	}
	
	@Test
	void testNearestPotentialsOverlap() {
		CellManager cells = new CellManager();
		cells.add(Cell.of(Coord.ZERO, CellState.OBSTRUCTED));
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.EAST).add(Cardinal.SOUTH), CellState.OBSTRUCTED));
		
		cells.getCells(CellState.OBSTRUCTED).forEach(c -> {
			Cardinal.directions.forEach(dir -> {
				cells.add(Cell.of(c.getFloorPos().copy().add(dir), CellState.POTENTIAL));
			});
		});
		
		List<Cell> nearest = cells.getNearestPotentials();
		assert(nearest.size() == 1);
		assert(nearest.getFirst().equals(Cell.of(Coord.of(1, 0, 0), CellState.POTENTIAL)));
	}
	
	@Test
	void testFullyConnected() {
		CellManager cells = new CellManager();
		cells.add(Cell.of(Coord.ZERO, CellState.OBSTRUCTED));
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.EAST), CellState.OBSTRUCTED));
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.EAST, 2), CellState.OBSTRUCTED));
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.EAST, 3), CellState.OBSTRUCTED));
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.EAST, 3).add(Cardinal.SOUTH), CellState.OBSTRUCTED));
		assert(cells.isConnected());
		cells.add(Cell.of(Coord.ZERO.add(Cardinal.EAST, 10), CellState.OBSTRUCTED));
		assert(!cells.isConnected());
	}
	
	@Test
	void testCombineBranches() {
		CellManager cm = new CellManager();
		cm.add(Cell.of(Coord.ZERO, CellState.OBSTRUCTED));
		cm.add(Cell.of(Coord.ZERO.add(Cardinal.EAST, 2), CellState.OBSTRUCTED));
		assert(cm.getBranches().size() == 2);
		assert(!cm.isConnected());
		cm.add(Cell.of(Coord.ZERO.add(Cardinal.EAST), CellState.OBSTRUCTED));
		assert(cm.getBranches().size() == 1);
		assert(cm.isConnected());
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
