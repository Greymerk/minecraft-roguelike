package com.greymerk.roguelike.dungeon.cell;

import java.util.Objects;

import com.greymerk.roguelike.editor.Coord;

public class Cell {

	public static final int SIZE = 6;
	private CellState state;
	private Coord floorPos;
	
	public Cell(Coord floorPos, CellState state) {
		this.floorPos = floorPos;
		this.state = state;
	}
	
	public CellState getState() {
		return this.state;
	}
	
	public void setState(CellState state) {
		this.state = state;
	}
	
	public Coord getFloorPos() {
		return new Coord(this.floorPos);
	}
	
	public Coord getWorldPos(Coord origin) {
		Coord wp = new Coord(this.floorPos);
		wp = wp.mul(new Coord(Cell.SIZE, 1, Cell.SIZE));
		wp.add(origin);
		return wp;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(floorPos, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Cell other = (Cell) obj;
		return Objects.equals(floorPos, other.floorPos)
				&& state == other.state;
	}
}
