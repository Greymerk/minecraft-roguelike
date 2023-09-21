package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

public abstract class AbstractMediumRoom extends AbstractRoom implements IRoom{

	@Override
	public CellManager getCells() {
		CellManager cells = new CellManager();
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
	public BoundingBox getBoundingBox() {
		BoundingBox bb = new BoundingBox(worldPos.copy());
		bb.grow(Cardinal.directions, 10);
		bb.grow(Cardinal.UP, 6).grow(Cardinal.DOWN, 3);
		return bb;
	}
	
	@Override
	public void determineEntrances(Floor f, Coord fp) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(fp);
			pos.add(dir, 2);
			Cell c = f.getCell(pos);
			if(!c.isRoom()) continue;
			List<Cardinal> walls = c.getWalls();
			if(!walls.contains(Cardinal.reverse(dir))) {
				this.addEntrance(dir, Entrance.DOOR);
			} else {
				this.addEntrance(dir, Entrance.WALL);
			}
		}
	}
}
