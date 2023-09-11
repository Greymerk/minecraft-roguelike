package com.greymerk.roguelike.dungeon.cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;

public class Cell {

	public static final int SIZE = 6;
	private CellState state;
	private Coord floorPos;
	private List<Cardinal> walls;
	
	public Cell(Coord floorPos, CellState state) {
		this.floorPos = floorPos;
		this.state = state;
		this.walls = new ArrayList<Cardinal>();
	}
	
	public CellState getState() {
		return this.state;
	}
	
	public void setState(CellState state) {
		this.state = state;
	}
	
	public void replace(Cell other) {
		this.floorPos = other.floorPos;
		this.state = other.state;
		this.walls = other.walls;
	}
	
	public boolean isRoom() {
		if(this.state == CellState.CORRIDOR) return true;
		if(this.state == CellState.OBSTRUCTED) return true;
		return false;
	}
	
	public Coord getFloorPos() {
		return new Coord(this.floorPos);
	}
	
	public int getLevelOffset() {
		// the floors are counted from zero, but down is negative in position
		// for now i'm just reversing the position value to get the level offset
		return this.floorPos.getY() * -1;
	}
	
	public Coord getWorldPos(Coord origin) {
		Coord wp = new Coord(this.floorPos);
		wp = wp.mul(new Coord(Cell.SIZE, 1, Cell.SIZE));
		wp.add(origin);
		return wp;
	}
	
	public void addWall(Cardinal dir) {
		if(this.walls.contains(dir)) return;
		this.walls.add(dir);
	}
	
	public List<Cardinal> getWalls(){
		return this.walls;
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

	public JsonElement asJson() {
		JsonObject jsonCell = new JsonObject();
		jsonCell.add("floorPos", this.floorPos.asJson());
		jsonCell.addProperty("state", this.state.name());
		JsonArray jsonWalls = new JsonArray();
		this.walls.forEach(dir -> jsonWalls.add(dir.name()));
		jsonCell.add("walls", jsonWalls);
		return jsonCell;
	}
}
