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
	public CellManager getCells(Cardinal dir) {
		Coord origin = new Coord(0,0,0);
		CellManager cells = new CellManager();
		
		cells.add(Cell.of(origin, CellState.OBSTRUCTED));
		cells.add(Cell.of(origin.copy().add(dir), CellState.OBSTRUCTED));
		cells.add(Cell.of(origin.copy().add(dir, 2), CellState.OBSTRUCTED));
		cells.add(Cell.of(origin.copy().add(dir, 3), CellState.POTENTIAL));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			cells.add(Cell.of(origin.copy().add(dir).add(o, 2), CellState.POTENTIAL));
			cells.add(Cell.of(origin.copy().add(o), CellState.OBSTRUCTED).addWall(Cardinal.reverse(dir)).addWall(o));
			cells.add(Cell.of(origin.copy().add(o).add(dir), CellState.OBSTRUCTED));
			cells.add(Cell.of(origin.copy().add(o).add(dir, 2), CellState.OBSTRUCTED).addWall(dir).addWall(o));
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
			Coord pos = fp.copy().add(this.direction);
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
