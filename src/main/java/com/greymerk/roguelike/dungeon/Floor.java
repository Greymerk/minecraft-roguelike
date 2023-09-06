package com.greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.Line;
import com.greymerk.roguelike.editor.shapes.MultiShape;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.util.math.RandHelper;

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
	
	public boolean addRandomBranch(Random rand, int min, int max) {
		Coord toAdd = new Coord(0,0,0);
		
		Cardinal randomDir = Cardinal.directions.get(rand.nextInt(4));
		toAdd.add(randomDir, rand.nextBetween(min, max));
		toAdd.add(Cardinal.orthogonal(randomDir).get(rand.nextInt(2)), rand.nextBetween(min, max));
		
		Cell current = cells.get(toAdd);
		if(current.getState() == CellState.OBSTRUCTED) return false;
		if(current.getState() == CellState.CORRIDOR) return false;
		
		Cell nearest = cells.getNearestPotential(toAdd);
		
		MultiShape lines = new MultiShape();
		
		Coord corner = rand.nextBoolean() 
				? new Coord(nearest.getFloorPos().getX(), 0, toAdd.getZ())
				: new Coord(toAdd.getX(), 0, nearest.getFloorPos().getZ());
		
		Coord start = new Coord(toAdd);
		Coord end = new Coord(corner);
		lines.addShape(new Line(start, end));
		
		start = new Coord(nearest.getFloorPos());
		end = new Coord(corner);
		lines.addShape(new Line(start, end));
		
		for(Coord fp : lines) {
			CellState state = cells.get(fp).getState();
			if(state == CellState.OBSTRUCTED) return false;
		}
		
		for(Coord fp : lines) {
			this.addCorridorCell(fp);
		}
		
		return true;
	}
	
	public void addCorridorCell(Coord fp) {
		if(cells.get(fp).getState() == CellState.OBSTRUCTED) return;
		
		this.cells.addCell(new Cell((fp), CellState.CORRIDOR));
		for(Cardinal dir : Cardinal.directions) {
			this.cells.addCell(new Cell(new Coord(fp).add(dir), CellState.POTENTIAL));
		}
	}
	
	public void convertCorridors() {
		List<Cell> corridors = this.cells.getCells(CellState.CORRIDOR);
		for(Cell c : corridors) {
			Coord fp = c.getFloorPos();
			Coord wp = c.getWorldPos(this.origin);
			IRoom toAdd = Room.getInstance(Room.CORRIDOR, this.theme, fp, wp);
			List<Cardinal> walls = c.getWalls();
			for(Cardinal dir : Cardinal.directions) {
				if(!walls.contains(dir)) {
					toAdd.addEntrance(dir);
				}
			}
			this.addRoom(toAdd);
		}
	}
	
	public Cell getCell(Coord floorPos) {
		return this.cells.get(floorPos);
	}
	
	public void addStair(Random rand) {
		
		List<Cell> potentials = cells.getCells(CellState.POTENTIAL);
		if(potentials.isEmpty()) {
			System.out.println("no potentials: " + this.origin.toString());
			return;
		}
		
		RandHelper.shuffle(potentials, rand);
		
		Cell c = findValidStair(potentials, rand);
		Cardinal stairDir = this.findStairDir(c);
		
		IRoom stairs = Room.getInstance(Room.STAIRWAY, this.theme, c.getFloorPos(), c.getWorldPos(origin), stairDir);
		this.addRoom(stairs);
		for(Cell rc : stairs.getCells()) {
			if(rc.getFloorPos().getY() != 0) continue;
			Coord fp = rc.getFloorPos();
			fp.add(c.getFloorPos());
			CellState state = rc.getState();
			Cell toAdd = new Cell(fp, state);
			this.addCell(toAdd);
			
			for(Cardinal dir : Cardinal.directions) {
				if(rc.getWalls().contains(dir)) continue;
				if(dir == stairDir) continue;
				Coord pos = rc.getFloorPos().copy();
				pos.add(dir);
				Cell other = this.getCell(pos);
				if(!other.isRoom()) continue;
				if(!other.getWalls().contains(Cardinal.reverse(dir))) {
					stairs.addEntrance(dir);
				}
			}
		}
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

	public CellState getCellState(Coord floorPos) {
		return cells.get(floorPos).getState();
	}
	
	public ITheme getTheme() {
		return this.theme;
	}
	
	public List<Cell> getOffsetCells(){
		List<Cell> cells = new ArrayList<Cell>();
		for(IRoom room : this.rooms) {
			for(Cell c : room.getCells()) {
				Coord cpos = c.getFloorPos();
				if(cpos.getY() == -1) {
					Coord fp = new Coord(room.getFloorPos());
					fp.add(new Coord(cpos.getX(), 0, cpos.getZ()));
					cells.add(new Cell(fp, c.getState()));
				}
			}
		}
		return cells;
	}
	
	public void addCell(Cell toAdd) {
		this.cells.addCell(toAdd);
	}
	
	public void addCells(List<Cell> toAdd) {
		this.cells.addCells(toAdd);
	}
	
	public void addCellWalls() {
		this.cells.addCellWalls();
	}
	
	public Coord getOrigin() {
		return new Coord(this.origin);
	}
}
