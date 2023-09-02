package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;

public abstract class AbstractMediumRoom extends AbstractRoom implements IRoom{

	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		cells.add(new Cell(new Coord(0,0,0), CellState.OBSTRUCTED));
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(0,0,0);
			pos.add(dir);
			cells.add(new Cell(new Coord(pos), CellState.OBSTRUCTED));
			pos.add(Cardinal.left(dir));
			Cell c = new Cell(new Coord(pos), CellState.OBSTRUCTED);
			c.addWall(dir);
			c.addWall(Cardinal.left(dir));
			cells.add(c);
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(0,0,0);
			pos.add(dir, 2);
			cells.add(new Cell(pos, CellState.POTENTIAL));
		}

		return cells;
	}

	@Override
	public void determineEntrances(Floor f, Coord fp) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(fp);
			pos.add(dir, 2);
			Cell c = f.getCell(pos);
			if(!(c.getState() == CellState.CORRIDOR
				|| c.getState() == CellState.OBSTRUCTED)) continue;
			List<Cardinal> walls = c.getWalls();
			if(!walls.contains(Cardinal.reverse(dir))) {
				this.addEntrance(dir);
			}
		}
	}
	
	@Override
	public int getSize() {
		return 9;
	}


}
