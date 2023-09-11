package com.greymerk.roguelike.dungeon.cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;

public class CellManager implements Iterable<Cell>{

	List<Cell> cells;
	
	public CellManager() {
		this.cells = new ArrayList<Cell>();
	}
	
	public void add(List<Cell> cellsToAdd, Coord offset) {
		for(Cell c : cellsToAdd) {
			if(c.getFloorPos().getY() != 0) continue;
			this.add(new Cell(c.getFloorPos().add(offset), c.getState()));
		}
	}
	
	public void add(List<Cell> cellsToAdd) {
		this.add(cellsToAdd, new Coord(0,0,0));
	}
	
	public void add(Cell toAdd) {
		Cell current = get(toAdd.getFloorPos());

		if(toAdd.getState() == CellState.EMPTY) return;
		
		if(current.getState() == CellState.EMPTY) {
			this.cells.add(toAdd);
			return;
		}
		
		if(current.getState() == CellState.OBSTRUCTED) return;
		
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
		
		return new Cell(new Coord(floorPos), CellState.EMPTY);
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
	
	public Cell getNearestPotential(Coord floorPos) {
		List<Cell> potentials = this.getCells(CellState.POTENTIAL);
		if(potentials.size() == 0) return new Cell(floorPos, CellState.EMPTY);
		
		Cell nearest = potentials.get(0);
		double dist = floorPos.distance(nearest.getFloorPos());
		for(Cell cell : potentials) {
			double d = floorPos.distance(cell.getFloorPos());
			if(d < dist) {
				dist = d;
				nearest = cell;
			}
		}
		
		return nearest;
	}
	
	public void addCellWalls() {
		for(Cell c : this.cells) {
			if(c.getState() == CellState.POTENTIAL) continue;
			
			Coord fp = c.getFloorPos();
			for(Cardinal dir : Cardinal.directions) {
				Coord pos = new Coord(fp);
				pos.add(dir);
				Cell other = this.get(pos);
				if(other.getState() == CellState.EMPTY
					|| other.getState() == CellState.POTENTIAL) {
					c.addWall(dir);
				}
				if(other.getState() == CellState.OBSTRUCTED
					&& other.getWalls().contains(Cardinal.reverse(dir))) {
					c.addWall(dir);
				}
			}
		}
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


