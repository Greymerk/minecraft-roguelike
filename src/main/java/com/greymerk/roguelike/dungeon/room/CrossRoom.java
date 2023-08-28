package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.block.Blocks;

public class CrossRoom extends AbstractRoom implements IRoom {


	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos();
		Random rand = editor.getRandom(origin);
		IBlockFactory wall = theme.getPrimary().getWall();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		IStair stair = theme.getPrimary().getStair();
		Coord start = new Coord(-9, -1, -9).add(origin);
		Coord end = new Coord(9, 4, 9).add(origin);
		RectHollow.fill(editor, rand, start, end, wall, false, true);
		end = new Coord(9, -1, 9).add(origin);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			
			start = new Coord(origin);
			start.add(dir, 8);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(Cardinal.left(dir), 3);
			end.add(Cardinal.right(dir), 3);
			end.add(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, start, end, wall);
			start.add(Cardinal.UP);
			start.add(Cardinal.reverse(dir));
			end.add(Cardinal.reverse(dir));
			
			for(Cardinal orth : Cardinal.orthogonal(dir)) {
				Coord pos = new Coord(origin);
				pos.add(dir, 8);
				pos.add(orth, 2);
				start = new Coord(pos);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				RectSolid.fill(editor, rand, start, end, pillar);
				pos.add(Cardinal.UP, 2);
				for(Cardinal d : Cardinal.directions) {
					Coord p = new Coord(pos);
					p.add(d);
					stair.setOrientation(d, true);
					stair.set(editor, rand, p, true, false);
				}
				pos.add(Cardinal.UP);
				pos.add(Cardinal.reverse(dir));
				for(Cardinal d : Cardinal.directions) {
					Coord p = new Coord(pos);
					p.add(d);
					stair.setOrientation(d, true);
					stair.set(editor, rand, p, true, false);
				}
			}
			
			Coord pos = new Coord(origin);
			pos.add(dir, 6);
			if(this.entrances.contains(dir)) {
				this.door(editor, rand, theme, pos, dir);
			}
			pos.add(Cardinal.left(dir), 6);
			cornerCell(editor, rand, pos);
		}
	}

	private void cornerCell(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		IStair stair = theme.getPrimary().getStair();
		
		
		for(Cardinal dir : Cardinal.directions) {
			Coord start = new Coord(origin);
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			Coord end = new Coord(start);
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, pillar);
			
			start = new Coord(origin);
			start.add(dir, 2);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(Cardinal.left(dir));
			end.add(Cardinal.right(dir));
			RectSolid.fill(editor, rand, start, end, wall);
			
			for(Cardinal orth : Cardinal.orthogonal(dir)) {
				Coord pos = new Coord(origin);
				pos.add(dir, 2);
				pos.add(Cardinal.UP, 2);
				pos.add(orth);
				stair.setOrientation(Cardinal.reverse(orth), true);
				stair.set(editor, pos);
			}
		}
		
		Coord pos = new Coord(origin);
		Spawner.generate(editor, rand, pos);
		
		pos.add(Cardinal.UP, 3);
		MetaBlock lamp = new MetaBlock(Blocks.LANTERN);
		editor.set(pos, lamp);
		
	}
	
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
	public int getSize() {
		return 9;
	}

	@Override
	public String getName() {
		return Room.CROSS.name();
	}

}
