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
		Coord worldPos = new Coord(1,2,3);
		Floor f = new Floor(worldPos);
		
		assert(f.getOrigin().equals(worldPos));
	}
	
	@Test
	void testAddRoom() {
		Coord worldPos = new Coord(0,0,0);
		Coord origin = new Coord(0,0,0);
		Cardinal dir = Cardinal.NORTH;
		Floor f = new Floor(worldPos);
		assert(f.getOrigin().equals(worldPos));
		
		EntranceRoom entrance = new EntranceRoom();
		entrance.setFloorPos(origin);
		entrance.setWorldPos(worldPos);
		entrance.setDirection(Cardinal.NORTH);
		f.addRoom(entrance);
		
		CellManager cells = f.getCells();
		
		cells.forEach(c -> {
			System.out.println(c);
		});
		
		assert(cells.get(origin).getState() == CellState.OBSTRUCTED);
		assert(cells.get(origin.copy().add(dir)).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.copy().add(Cardinal.left(dir))).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.copy().add(Cardinal.right(dir))).getState() == CellState.POTENTIAL);
		assert(cells.get(origin.copy().add(Cardinal.reverse(dir))).getState() == CellState.POTENTIAL);
	}

}
