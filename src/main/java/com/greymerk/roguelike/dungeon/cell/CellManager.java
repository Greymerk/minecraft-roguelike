package com.greymerk.roguelike.dungeon.cell;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.editor.Coord;

public class CellManager {

	List<Cell> cells;
	
	public CellManager() {
		this.cells = new ArrayList<Cell>();
	}
	
	public void addCells(List<Cell> cellsToAdd, Coord offset) {
		for(Cell c : cellsToAdd) {
			if(c.getFloorPos().getY() != 0) continue;
			this.addCell(new Cell(c.getFloorPos().add(offset), c.getState()));
		}
	}
	
	public void addCells(List<Cell> cellsToAdd) {
		this.addCells(cellsToAdd, new Coord(0,0,0));
	}
	
	public void addCell(Cell toAdd) {
		Cell current = get(toAdd.getFloorPos());

		if(toAdd.getState() == CellState.EMPTY) return;
		
		if(current.getState() == CellState.EMPTY) {
			this.cells.add(toAdd);
			return;
		}
		
		if(current.getState() == CellState.OBSTRUCTED) return;
		
		if(current.getState() == CellState.POTENTIAL) {
			current.setState(toAdd.getState());
			return;
		}
		
		if(toAdd.getState() == CellState.OBSTRUCTED) {
			current.setState(CellState.OBSTRUCTED);
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
}
