package com.greymerk.roguelike.dungeon.cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
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
		
		return new Cell(floorPos.copy(), CellState.EMPTY, null);
	}
	
	public List<Cell> getCells(){
		return this.cells;
	}
	
	public List<Cell> getCells(CellState state){
		return this.cells.stream().filter(cell -> cell.getState() == state).collect(Collectors.toList());
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
	
	public boolean isConnected() {
		return this.getBranches().size() == 1;
	}
	
	public List<Cell> getNearestPotentials(){
		List<Cell> potentials = this.getCells(CellState.POTENTIAL);
		List<List<Cell>> branches = this.getBranches();
		
		List<Cell> nearestSoFar = List.of();
		
		for(Cell c : potentials) {
			if(this.connectsTwoBranches(branches, c)) return List.of(c);
			
			for(Cell c2 : potentials) {
				if(c == c2) continue;
				
				Optional<List<Cell>> branch1 = this.findBranchAttachedToPotential(branches, c);
				Optional<List<Cell>> branch2 = this.findBranchAttachedToPotential(branches, c2);
				
				if(branch1.isEmpty() || branch2.isEmpty()) continue;
				
				if(branch1.get().equals(branch2.get())) continue;
				
				if(nearestSoFar.isEmpty()) {
					nearestSoFar = List.of(c, c2);
					continue;
				}
				
				double dist1 = nearestSoFar.getFirst().getFloorPos().distance(nearestSoFar.getLast().getFloorPos());
				double dist2 = c.getFloorPos().distance(c2.getFloorPos());
				
				if(dist2 < dist1) {
					nearestSoFar = List.of(c, c2);
				}
			}
		}
		
		return nearestSoFar;
	}
	
	public Optional<ExitType> getExitType(Cell cell, Cardinal dir) {
		Cell neighbour = this.get(cell.getFloorPos().add(dir));
		if(ExitType.isValidDoor(cell, neighbour, dir)) return Optional.of(ExitType.DOOR);
		if(ExitType.isValidAlcove(cell, neighbour, dir)) return Optional.of(ExitType.ALCOVE);
		if(ExitType.isValidWall(cell, neighbour, dir)) return Optional.of(ExitType.WALL);
		return Optional.empty();
	}
	
	private boolean connectsTwoBranches(List<List<Cell>> branches, Cell c) {
		int count = 0;
		for(List<Cell> branch : branches) {
			if(isCellAttachedToBranch(branch, c)) count++;
		}
		return count > 1;
	}

	public Optional<List<Cell>> findBranchAttachedToPotential(List<List<Cell>> branches, Cell potential){
		for(List<Cell> branch : branches) {
			if(isCellAttachedToBranch(branch, potential)) return Optional.of(branch);
		}
		return Optional.empty();
	}
	
	public boolean isCellAttachedToBranch(List<Cell> branch, Cell cell) {
		for(Cell c : branch) {
			if(c.connectedTo(cell)) return true;
		}
		return false;
	}
	
	public List<List<Cell>> getBranches(){
		List<Cell> obstructeds = this.getCells(CellState.OBSTRUCTED);
		
		List<List<Cell>> branches = new ArrayList<List<Cell>>();
		if(obstructeds.isEmpty()) return branches;
		
		for(Cell cell : obstructeds) {
			if(this.branchesContainCell(branches, cell)) continue;
			List<Cell> branch = new ArrayList<Cell>();
			branch.add(cell);
			branches.add(this.buildBranch(branch, obstructeds));
		}
		
		return branches;
	}
	
	public List<Cell> buildBranch(List<Cell> branch, List<Cell> cells){
		for(Cell c : cells) {
			if(!branch.contains(c) && this.isCellAttachedToBranch(branch, c)) {
				branch.add(c);
				return buildBranch(branch, cells);
			}
		}
		return branch;
	}
	
	public boolean branchesContainCell(List<List<Cell>> branches, Cell c) {
		for(List<Cell> branch : branches) {
			if(branch.contains(c)) return true;
		}
		
		return false;
	}
	
	public Optional<List<Cell>> findBranchContainingCell(List<List<Cell>> branches, Cell cell){
		for(List<Cell> branch : branches) {
			for(Cell c : branch) {
				if(c.connectedTo(cell)) {
					return Optional.of(branch);
				}
			}
		}
		return Optional.empty();
	}
	
	public List<Integer> getLevelOffsets(){
		Set<Integer> offsets = new TreeSet<Integer>();
		this.cells.forEach(c -> {
			offsets.add(c.getLevelOffset());
		});
		return offsets.stream().sorted().toList(); 
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


