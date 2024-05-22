package com.greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

public class Floor {

	private Coord origin;
	private List<IRoom> rooms;
	private CellManager cells;
	
	public Floor(Coord origin) {
		this.rooms = new ArrayList<IRoom>();
		this.cells = new CellManager();
		this.origin = origin;
	}
	
	public void addRoom(IRoom room) {
		this.rooms.add(room);
	}
	
	public void generate(IWorldEditor editor) {
		for(IRoom room : this.rooms) {
			room.generate(editor);
		}
	}
	
	public List<IRoom> getRooms(){
		return this.rooms;
	}
	
	public CellManager getCells(){
		return this.cells;
	}
	
	public List<Cell> getCells(CellState type){
		return this.cells.getCells(type);
	}

	public Cell getCell(Coord floorPos) {
		return this.cells.get(floorPos);
	}
	
	public void addCell(Cell toAdd) {
		this.cells.add(toAdd);
	}
			
	public Coord getOrigin() {
		return this.origin.copy();
	}

	public JsonElement asJson() {
		JsonObject jsonFloor = new JsonObject();
		JsonArray jsonCells = this.cells.asJson();
		jsonFloor.add("cells", jsonCells);
		return jsonFloor;
	}
}
