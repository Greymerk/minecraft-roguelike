package com.greymerk.roguelike.dungeon;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.room.EntranceRoom;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;

class FloorTest {

	@Test
	void testFloor() {
		Coord worldPos = Coord.of(1,2,3);
		Floor f = Floor.of(worldPos);
		
		assert(f.getOrigin().equals(worldPos));
	}
	
	@Test
	void testAddRoom() {
		Coord worldPos = Coord.ZERO;
		Coord origin = Coord.ZERO;
		Cardinal dir = Cardinal.NORTH;
		Floor f = Floor.of(worldPos);
		assert(f.getOrigin().equals(worldPos));
		
		EntranceRoom entrance = new EntranceRoom();
		entrance.setFloorPos(origin);
		entrance.setWorldPos(worldPos);
		entrance.setDirection(Cardinal.NORTH);
		f.addRoom(entrance);
		entrance.getCells(entrance.getDirection()).forEach(cell -> {
			f.addCell(cell);
		});
		
		CellManager cells = f.getCells();
		
		assert(cells.get(origin).getState() == CellState.OBSTRUCTED);
		assert(cells.get(origin.add(dir)).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.add(Cardinal.left(dir))).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.add(Cardinal.right(dir))).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.add(Cardinal.reverse(dir))).getState() == CellState.POTENTIAL);
	}

}
