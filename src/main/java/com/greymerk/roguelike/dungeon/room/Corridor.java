package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;

public class Corridor extends AbstractRoom implements IRoom{


	@Override
	public void generate(IWorldEditor editor) {
		
		Random rand = editor.getRandom(worldPos);
		IBlockFactory blocks = theme.getPrimary().getWall();
		IStair stairs = theme.getPrimary().getStair();
		
		Coord start = new Coord(worldPos);
		start.add(new Coord(-3, -1, -3));
		Coord end = new Coord(worldPos);
		end.add(new Coord(3, 4, 3));
		
		RectHollow.fill(editor, rand, start, end, blocks, false, true);
		
		for(Cardinal dir : Cardinal.directions) {
			start = new Coord(worldPos);
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			Coord pos = new Coord(start);
			pos.add(Cardinal.DOWN);
			editor.fillDown(rand, pos, theme.getPrimary().getPillar());
			start = new Coord(end);
			end.add(Cardinal.right(dir), 3);
			RectSolid.fill(editor, rand, start, end, blocks);
			
			for(Cardinal orth : Cardinal.orthogonal(dir)) {
				pos = new Coord(worldPos);
				pos.add(dir, 2);
				pos.add(orth);
				pos.add(Cardinal.UP, 2);
				stairs.setOrientation(Cardinal.reverse(orth), true).set(editor, pos);
			}
			
		}
		
		start = new Coord(worldPos);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.WEST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.EAST, 3);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			if(this.entrances.contains(dir)) {
				this.door(editor, rand, theme, worldPos, dir);
			} else {
				Coord pos = new Coord(worldPos);
				pos.add(Cardinal.UP);
				pos.add(dir, 3);
				editor.set(pos, BlockType.get(BlockType.GLOWSTONE));	
			}
		}
	}

	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		cells.add(new Cell(new Coord(0,0,0), CellState.CORRIDOR));
		
		for(Cardinal dir : Cardinal.directions) {
			cells.add(new Cell(new Coord(0,0,0).add(dir), CellState.POTENTIAL));
		}
		
		return cells;
	}

	@Override
	public String getName() {
		return Room.CORRIDOR.name();
	}



}
