package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.wall.WallShelf;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.util.math.random.Random;

public class Corridor extends AbstractRoom implements IRoom{


	@Override
	public void generate(IWorldEditor editor) {
		
		Random rand = editor.getRandom(worldPos);
		IBlockFactory blocks = theme.getPrimary().getWall();
		IStair stairs = theme.getPrimary().getStair();
		
		Coord start = worldPos.copy();
		start.add(new Coord(-3, -1, -3));
		Coord end = worldPos.copy();
		end.add(new Coord(3, 4, 3));
		
		RectHollow.fill(editor, rand, start, end, blocks, false, true);
		
		for(Cardinal dir : Cardinal.directions) {
			start = worldPos.copy();
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			end = start.copy();
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			start = end.copy();
			end.add(Cardinal.right(dir), 3);
			RectSolid.fill(editor, rand, start, end, blocks);
			
			/*
			Coord pos = worldPos.copy();
			pos.add(dir, 2);
			pos.add(Cardinal.left(dir), 2);
			pos.add(Cardinal.DOWN, 2);
			editor.fillDown(rand, pos, theme.getPrimary().getPillar());
			*/
			
			for(Cardinal orth : Cardinal.orthogonal(dir)) {
				Coord pos = worldPos.copy();
				pos.add(dir, 2);
				pos.add(orth);
				pos.add(Cardinal.UP, 2);
				stairs.setOrientation(Cardinal.reverse(orth), true).set(editor, pos);
			}
			
			Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, worldPos.copy());
			
		}
		
		start = new Coord(worldPos);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			if(this.entrances.contains(dir)) {
				this.door(editor, rand, theme, worldPos, dir);
			} else {
				Coord pos = new Coord(worldPos);
				new WallShelf().generate(editor, rand, theme, pos, dir);
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

	@Override
	public void determineEntrances(Floor f, Coord floorPos) {}



}
