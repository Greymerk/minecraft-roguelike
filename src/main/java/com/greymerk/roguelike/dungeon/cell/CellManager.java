package com.greymerk.roguelike.dungeon.cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.greymerk.roguelike.editor.Coord;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CellManager implements Iterable<Cell>{

	List<Cell> cells;
	
	public static final Codec<List<Cell>> LIST_CELL_CODEC = Codec.list(Cell.CODEC);
	public static final Codec<CellManager> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			LIST_CELL_CODEC.fieldOf("cells").forGetter(cm -> cm.getCells())
		).apply(instance, cl -> new CellManager(cl))
	);
			
	
	public CellManager() {
		this.cells = new ArrayList<Cell>();
	}
	
	public CellManager(List<Cell> cells) {
		this.cells = new ArrayList<Cell>();
		cells.addAll(cells);
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

	public boolean roomFits(Cell potential, CellManager rcm) {
		List<Cell> roomCells = rcm.getCells(CellState.OBSTRUCTED);
		for(Cell c : roomCells) {
			Coord fp = c.getFloorPos().add(potential.getFloorPos());
			if(fp.getY() != 0) continue;
			Cell target = this.get(fp);
			if(target.getState() == CellState.OBSTRUCTED) return false;
		}
		
		return true;
	}
	
	public Set<Integer> getLevelOffsets(){
		Set<Integer> offsets = new HashSet<Integer>();
		this.cells.forEach(c -> {
			offsets.add(c.getLevelOffset());
		});
		return offsets; 
	}
	
	public List<Cell> getByOffset(int offset){
		List<Cell> cells = new ArrayList<Cell>();
		this.cells.forEach(c -> {
			if(c.getLevelOffset() == offset) {
				cells.add(c);
			}
		});
		return cells;
	}
	
	@Override
	public String toString() {
		return this.cells.toString();
	}
}


