package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

public abstract class AbstractLargeRoom extends AbstractRoom implements IRoom {

	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();
		
		BoundingBox.of(origin).add(dir, 2)
			.grow(Cardinal.directions)
			.forEach(pos -> {
				cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
			});
		
		Coord pos = origin.copy();
		cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
		pos.add(dir, 4);
		cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
		pos.add(dir);
		cells.add(Cell.of(pos, CellState.POTENTIAL, this));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			pos = origin.copy().add(o);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this).addWall(Cardinal.reverse(dir)));
			pos.add(o);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this).addWall(Cardinal.reverse(dir)).addWall(o));
			pos.add(dir);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this).addWall(o));
			pos.add(dir);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
			pos.add(dir);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this).addWall(o));
			pos.add(dir);
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this).addWall(dir).addWall(o));
			pos.add(Cardinal.reverse(o));
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this).addWall(dir));
			
			cells.add(Cell.of(origin.copy().add(dir, 2).add(o, 3), CellState.POTENTIAL, this));
		}
		
		return cells;
	}
	
	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin.copy().add(dir, Cell.SIZE * 2))
			.grow(Cardinal.directions, 15)
			.grow(Cardinal.UP, 6)
			.grow(Cardinal.DOWN, 3);
	}

}
