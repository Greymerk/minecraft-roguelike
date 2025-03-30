package com.greymerk.roguelike.dungeon.cell;

public enum CellState {
	POTENTIAL, OBSTRUCTED, EMPTY;
	
	public static CellState of(String name) {
		return CellState.valueOf(name);
	}

}

