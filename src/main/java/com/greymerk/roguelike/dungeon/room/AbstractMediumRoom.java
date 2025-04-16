package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

public abstract class AbstractMediumRoom extends AbstractRoom implements IRoom{

	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();
		
		cells.add(Cell.of(origin, CellState.OBSTRUCTED, this));
		cells.add(Cell.of(origin.copy().add(dir), CellState.OBSTRUCTED, this));
		cells.add(Cell.of(origin.copy().add(dir, 2), CellState.OBSTRUCTED, this));
		cells.add(Cell.of(origin.copy().add(dir, 3), CellState.POTENTIAL, this));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			cells.add(Cell.of(origin.copy().add(dir).add(o, 2), CellState.POTENTIAL, this));
			cells.add(Cell.of(origin.copy().add(o), CellState.OBSTRUCTED, this).addWall(Cardinal.reverse(dir)).addWall(o));
			cells.add(Cell.of(origin.copy().add(o).add(dir), CellState.OBSTRUCTED, this));
			cells.add(Cell.of(origin.copy().add(o).add(dir, 2), CellState.OBSTRUCTED, this).addWall(dir).addWall(o));
		}
		
		return cells;
	}

	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin.copy().add(dir, Cell.SIZE))
			.grow(Cardinal.directions, 10)
			.grow(Cardinal.UP, 6)
			.grow(Cardinal.DOWN, 3);
	}
}
