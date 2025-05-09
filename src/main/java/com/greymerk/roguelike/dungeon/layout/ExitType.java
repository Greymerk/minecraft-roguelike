package com.greymerk.roguelike.dungeon.layout;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;

public enum ExitType {
	DOOR, WALL, ALCOVE;

	public static boolean isValidDoor(Cell cell, Cell neighbour, Cardinal dir) {
		if(cell.getState() != CellState.OBSTRUCTED) return false;
		if(neighbour.getState() != CellState.OBSTRUCTED) return false;
		if(cell.hasWall(dir)) return false;
		if(neighbour.hasWall(Cardinal.reverse(dir))) return false;
		if(cell.sameRoom(neighbour)) return false;		
		return true;
	}

	public static boolean isValidAlcove(Cell cell, Cell neighbour, Cardinal dir) {
		if(cell.getState() != CellState.OBSTRUCTED) return false;
		if(neighbour.getState() != CellState.POTENTIAL) return false;
		if(cell.hasWall(dir)) return false;
		if(neighbour.hasWall(Cardinal.reverse(dir))) return false;
		if(!cell.sameRoom(neighbour)) return false;
		return true;
	}

	public static boolean isValidWall(Cell cell, Cell neighbour, Cardinal dir) {
		if(cell.getState() != CellState.OBSTRUCTED) return false;
		if(cell.hasWall(dir)) return false;
		if(neighbour.getState() == CellState.EMPTY) return true;
		
		if(!neighbour.hasWall(Cardinal.reverse(dir))) return false;
		boolean sameRoom = cell.sameRoom(neighbour);
		if(neighbour.getState() == CellState.OBSTRUCTED && sameRoom) return false;
		
		return true;
	}
}
