package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.util.math.random.Random;

public class LargeRoom extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = new Coord(this.getWorldPos());
		Random rand = editor.getRandom(origin);
		
		Coord start = new Coord(origin);
		int size = 15;
		start.add(Cardinal.NORTH, size);
		start.add(Cardinal.WEST, size);
		start.add(Cardinal.DOWN);
		Coord end = new Coord(origin);
		end.add(Cardinal.SOUTH, size);
		end.add(Cardinal.EAST, size);
		end.add(Cardinal.UP, 5);
		RectHollow box = new RectHollow(start, end);
		box.fill(editor, rand, this.getTheme().getPrimary().getWall());
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(origin);
			pos.add(dir, 12);
			this.door(editor, rand, theme, pos, dir);
		}
	}

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
	public String getName() {
		return Room.LARGE.name();
	}

}
