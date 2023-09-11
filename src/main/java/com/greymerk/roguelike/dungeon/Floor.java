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
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class Floor {

	private Coord origin;
	private List<IRoom> rooms;
	private CellManager cells;
	private ITheme theme;
	
	public Floor(ITheme theme, Coord origin) {
		this.rooms = new ArrayList<IRoom>();
		this.cells = new CellManager();
		this.theme = theme;
		this.origin = origin;
	}

	public Cardinal findStairDir(Cell cell) {
		for(Cardinal dir : Cardinal.directions) {
			Coord p = cell.getFloorPos();
			p.add(dir);
			if(this.cells.get(p).isRoom()) {
				return Cardinal.reverse(dir);
			}
		}
		return Cardinal.EAST;
	}
	
	public Cell findValidStair(List<Cell> potentials, Random rand) {
		for(Cell c : potentials) {
			int count = 0;
			for(Cardinal dir : Cardinal.randDirs(rand)) {
				Coord p = c.getFloorPos();
				p.add(dir);
				if(this.cells.get(p).isRoom()) {
					count++;
				}
			}
			if(count == 1) {
				return c;
			}
		}
		return potentials.get(0);
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
	
	public List<Cell> getCells(){
		return this.cells.getCells();
	}
	
	public List<Cell> getCells(CellState type){
		return this.cells.getCells(type);
	}

	public Cell getCell(Coord floorPos) {
		return this.cells.get(floorPos);
	}
		
	public ITheme getTheme() {
		return this.theme;
	}
	
	public void addCell(Cell toAdd) {
		this.cells.add(toAdd);
	}
			
	public Coord getOrigin() {
		return new Coord(this.origin);
	}

	public JsonElement asJson() {
		JsonObject jsonFloor = new JsonObject();
		JsonArray jsonCells = this.cells.asJson();
		jsonFloor.add("cells", jsonCells);
		return jsonFloor;
	}
}
