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
import com.greymerk.roguelike.editor.shapes.Shape;

public abstract class AbstractLargeRoom extends AbstractRoom implements IRoom {

	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = new Coord(0,0,0);
		CellManager cells = new CellManager();
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.add(dir, 2).grow(Cardinal.directions);
		bb.getShape(Shape.RECTSOLID).get().forEach(pos -> {
			cells.add(Cell.of(pos, CellState.OBSTRUCTED));
		});
		
		Coord pos = origin.copy();
		cells.add(Cell.of(pos, CellState.OBSTRUCTED));
		pos.add(dir, 4);
		cells.add(Cell.of(pos, CellState.OBSTRUCTED));
		pos.add(dir);
		cells.add(Cell.of(pos, CellState.POTENTIAL));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			pos = origin.copy().add(o);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED).addWall(Cardinal.reverse(dir)));
			pos.add(o);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED).addWall(Cardinal.reverse(dir)).addWall(o));
			pos.add(dir);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED).addWall(o));
			pos.add(dir);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED));
			pos.add(dir);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED).addWall(o));
			pos.add(dir);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED).addWall(dir).addWall(o));
			pos.add(Cardinal.reverse(o));
			cells.add(Cell.of(pos, CellState.OBSTRUCTED).addWall(dir));
			
			cells.add(Cell.of(origin.copy().add(dir, 2).add(o, 3), CellState.POTENTIAL));
		}
		
		return cells;
	}
	
	@Override
	public BoundingBox getBoundingBox() {
		BoundingBox bb = BoundingBox.of(worldPos.copy().add(direction, Cell.SIZE * 2));
		bb.grow(Cardinal.directions, (Cell.SIZE / 2) + (Cell.SIZE * 2) + 1);
		bb.grow(Cardinal.UP, 6).grow(Cardinal.DOWN, 3);
		return bb;
	}
	
	@Override
	public void determineEntrances(Floor f, Coord fp) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = fp.copy().add(this.direction, 2);
			pos.add(dir, 3);
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
