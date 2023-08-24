package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectSolid;

public class Stairway extends AbstractRoom implements IRoom {
	
	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(getWorldPos());
		Coord origin = new Coord(this.worldPos);
		buildCell(editor, rand, origin, direction);
		this.buildSteps(editor, rand, origin);
		Coord bottomCell = new Coord(origin).add(direction, 12).add(Cardinal.DOWN, 10);
		buildCell(editor, rand, bottomCell, Cardinal.reverse(direction));
	}

	private void buildSteps(IWorldEditor editor, Random rand, Coord origin) {
		for(int i = 0; i < 10; ++i) {
			this.oneStep(editor, rand, origin, i);
		}
	}
	
	private void oneStep(IWorldEditor editor, Random rand, Coord origin, int step) {
		IStair stair = theme.getPrimary().getStair();
		
		Coord start = new Coord(origin);
		start.add(direction, 2 + step);
		start.add(Cardinal.UP, 3 - step);
		Coord end = new Coord(start);
		end.add(Cardinal.DOWN, 3);
		start.add(Cardinal.left(direction));
		end.add(Cardinal.right(direction));
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		end = new Coord(start);
		end.add(Cardinal.right(direction), 2);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
		for(Cardinal dir : Cardinal.orthogonal(direction)) {
			Coord pos = new Coord(origin);
			pos.add(direction, 2 + step);
			pos.add(Cardinal.UP, 2 - step);
			pos.add(dir);
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, pos);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN, 1 + step);
		start.add(direction, -1 + step);
		end = new Coord(start);
		end.add(direction, 3);
		start.add(Cardinal.left(direction));
		end.add(Cardinal.right(direction));
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		end = new Coord(start);
		end.add(Cardinal.right(direction), 2);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
		start.add(direction);
		end.add(direction);
		stair.setOrientation(direction, false);
		new RectSolid(start, end).get().forEach(p -> {
			stair.set(editor, p);
		});;
	}
	
	private void buildCell(IWorldEditor editor, Random rand, Coord pos, Cardinal entrance) {
		IStair stair = theme.getPrimary().getStair();
		
		Coord start = new Coord(pos);
		start.add(new Coord(-2, 0, -2));
		Coord end = new Coord(pos);
		end.add(new Coord(2, 3, 2));
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		start = new Coord(-2, -1, -2).add(pos);
		end = new Coord(2, -1, 2).add(pos);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			start = new Coord(pos);
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			
			if(dir != entrance) {
				this.door(editor, rand, theme, pos, dir);
			} else {
				continue;
			}
			
			start = new Coord(end);
			end.add(Cardinal.right(dir), 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord p = new Coord(pos);
				p.add(dir, 2);
				p.add(Cardinal.UP, 2);
				p.add(o);
				stair.setOrientation(Cardinal.reverse(o), true);
				stair.set(editor, rand, p, true, true);
			}
		}
	}

	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		Coord origin = new Coord(0,0,0);
		Coord pos = new Coord(origin);
		cells.add(new Cell(new Coord(pos), CellState.OBSTRUCTED));
		pos.add(direction);
		cells.add(new Cell(new Coord(pos), CellState.OBSTRUCTED));
		pos.add(direction);
		cells.add(new Cell(new Coord(pos), CellState.OBSTRUCTED));
		
		for(Cardinal dir : Cardinal.directions) {
			if(dir == direction) continue;
			Coord p = new Coord(origin);
			cells.add(new Cell(new Coord(p), CellState.POTENTIAL));
		}
		
		pos = new Coord(origin);
		pos.add(Cardinal.DOWN);
		cells.add(new Cell(new Coord(pos), CellState.OBSTRUCTED));
		pos.add(direction);
		cells.add(new Cell(new Coord(pos), CellState.OBSTRUCTED));
		pos.add(direction);
		cells.add(new Cell(new Coord(pos), CellState.OBSTRUCTED));
		
		for(Cardinal dir : Cardinal.directions) {
			if(dir == Cardinal.reverse(direction)) continue;
			Coord p = new Coord(pos);
			p.add(dir);
			cells.add(new Cell(new Coord(p), CellState.POTENTIAL));
		}
		
		return cells;
	}

	

	@Override
	public String getName() {
		return Room.STAIRWAY.name();
	}

}
