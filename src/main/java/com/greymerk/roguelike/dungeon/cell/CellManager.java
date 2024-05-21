package com.greymerk.roguelike.dungeon.cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.greymerk.roguelike.editor.Coord;

public class CellManager implements Iterable<Cell>{

	List<Cell> cells;
	
	public CellManager() {
		this.cells = new ArrayList<Cell>();
	}
	
	public void add(Cell toAdd) {
		Cell current = get(toAdd.getFloorPos());

		if(toAdd.getState() == CellState.EMPTY) return;
		
		if(current.getState() == CellState.EMPTY) {
			this.cells.add(toAdd);
			return;
		}
		
		if(current.getState() == CellState.OBSTRUCTED) {
			if(toAdd.getState() == CellState.OBSTRUCTED) {
				current.replace(toAdd);
			}
			return;
		}
		
		if(current.getState() == CellState.POTENTIAL) {
			current.replace(toAdd);
			return;
		}
		
		if(toAdd.getState() == CellState.OBSTRUCTED) {
			current.replace(toAdd);
			return;
		}
	}
	
	public Cell get(Coord floorPos) {
		for(Cell c : this.cells) {
			if(c.getFloorPos().equals(floorPos)) return c;
		}
		
		return new Cell(floorPos.copy(), CellState.EMPTY);
	}
	
	public List<Cell> getCells(){
		return this.cells;
	}
	
	public List<Cell> getCells(CellState state){
		List<Cell> cl = new ArrayList<Cell>();
		for(Cell c : this.cells) {
			if(c.getState() == state) cl.add(c);
		}
		return cl;
	}
	
	public Iterator<Cell> iterator(){
		return this.cells.iterator();
	}
	
	public JsonArray asJson() {
		JsonArray jsonCells = new JsonArray();
		this.cells.forEach(c -> jsonCells.add(c.asJson()));
		return jsonCells;
	}
}


