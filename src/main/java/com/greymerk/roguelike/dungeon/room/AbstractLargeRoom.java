package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.shapes.RectSolid;

public abstract class AbstractLargeRoom extends AbstractRoom implements IRoom {

	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		
		Coord start = new Coord(-2,0,-2);
		Coord end = new Coord(2, 0, 2);
		RectSolid square = new RectSolid(start, end);
		for(Coord pos : square) {
			Cell c = new Cell(pos, CellState.OBSTRUCTED);
			if(Math.abs(pos.getX()) == 2 && pos.getZ() == 0) {cells.add(c); continue;}
			if(Math.abs(pos.getZ()) == 2 && pos.getX() == 0) {cells.add(c); continue;}
			
			if(pos.getX() == -2) c.addWall(Cardinal.WEST);
			if(pos.getX() == 2)  c.addWall(Cardinal.EAST);
			if(pos.getZ() == -2) c.addWall(Cardinal.NORTH);
			if(pos.getZ() == 2)	 c.addWall(Cardinal.SOUTH);
			cells.add(c);
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(0,0,0);
			pos.add(dir, 3);
			Cell c = new Cell(pos, CellState.POTENTIAL);
			cells.add(c);
		}
		return cells;
	}
	
	@Override
	public void determineEntrances(Floor f, Coord fp) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(fp);
			pos.add(dir, 3);
			Cell c = f.getCell(pos);
			if(c.getState() != CellState.CORRIDOR) continue;
			List<Cardinal> walls = c.getWalls();
			if(!walls.contains(Cardinal.reverse(dir))) {
				this.addEntrance(dir);
			}
		}
	}
}
