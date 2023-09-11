package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.util.math.random.Random;

public class ReservoirRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = worldPos.copy();
		Random rand = editor.getRandom(origin);
		this.clearSpace(editor, rand, origin);
		this.basin(editor, rand, origin);
		this.buildCells(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.addArches(editor, rand, origin);
		this.addLiquid(editor, rand, origin);
		this.doors(editor, rand, origin);
		
	}
	
	private void doors(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : this.entrances) {
			Coord pos = origin.copy();
			pos.add(dir, 12);
			Fragment.generate(Fragment.ARCH, editor, rand, theme, pos, dir);
		}
	}

	private void addLiquid(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory liquid = theme.getPrimary().getLiquid();
		
		Coord start = origin.copy();
		start.add(Cardinal.DOWN, 6);
		start.add(Cardinal.NORTH, 11);
		start.add(Cardinal.WEST, 11);
		Coord end = origin.copy();
		end.add(Cardinal.DOWN, 5);
		end.add(Cardinal.SOUTH, 11);
		end.add(Cardinal.EAST, 11);
		RectSolid.fill(editor, rand, start, end, liquid, true, false);
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		for(Cardinal dir : Cardinal.directions) {
			// outer rim
			Coord start = origin.copy();
			start.add(Cardinal.UP, 4);
			start.add(dir, 9);
			start.add(Cardinal.left(dir), 8);
			Coord end = origin.copy();
			end.add(Cardinal.UP, 4);
			end.add(dir, 10);
			end.add(Cardinal.right(dir), 9);
			RectSolid.fill(editor, rand, start, end, wall);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				start = origin.copy();
				start.add(Cardinal.UP, 4);
				start.add(dir, 2);
				end = start.copy();
				end.add(o, 8);
				RectSolid.fill(editor, rand, start, end, wall);
				end.add(Cardinal.DOWN);
				stair.setOrientation(Cardinal.reverse(o), true);
				stair.set(editor, end);
				end.add(o);
				wall.set(editor, rand, end);
				end.add(Cardinal.DOWN);
				stair.set(editor, end);
				
				
				start = origin.copy();
				start.add(Cardinal.UP, 4);
				start.add(dir, 4);
				end = start.copy();
				end.add(o, 8);
				RectSolid.fill(editor, rand, start, end, wall);
				end.add(Cardinal.DOWN);
				stair.setOrientation(Cardinal.reverse(o), true);
				stair.set(editor, end);
				end.add(o);
				wall.set(editor, rand, end);
				end.add(Cardinal.DOWN);
				stair.set(editor, end);
				
				Coord pos = origin.copy();
				pos.add(Cardinal.UP, 3);
				pos.add(o, 9);
				pos.add(dir, 8);
				wall.set(editor, rand, pos);
				pos.add(Cardinal.DOWN);
				stair.set(editor, pos);
			}
		}
	}

	private void clearSpace(IWorldEditor editor, Random rand, Coord origin) {
		
		// clear upper level cell space
		for(Cardinal dir : Cardinal.directions) {
			Coord start = origin.copy();
			start.add(dir, 10);
			start.add(Cardinal.left(dir), 9);
			Coord end = origin.copy();
			end.add(dir, 14);
			end.add(Cardinal.right(dir), 14);
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, Air.get());
		}
		
		// clear basin
		Coord start = origin.copy();
		start.add(Cardinal.NORTH, 10);
		start.add(Cardinal.WEST, 10);
		start.add(Cardinal.DOWN, 6);
		Coord end = origin.copy();
		end.add(Cardinal.SOUTH, 10);
		end.add(Cardinal.EAST, 10);
		end.add(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, start, end, Air.get());
		
	}
	
	private void buildCells(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			Coord start = origin.copy();
			start.add(dir, 11);
			start.add(Cardinal.DOWN);
			Coord end = start.copy();
			end.add(dir, 2);
			start.add(Cardinal.left(dir), 13);
			end.add(Cardinal.right(dir), 9);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 12);
			cell(editor, rand, pos.copy());
			pos.add(Cardinal.left(dir), 6);
			cell(editor, rand, pos.copy());
			pos.add(Cardinal.right(dir), 12);
			cell(editor, rand, pos.copy());
			pos.add(Cardinal.right(dir), 6);
			cell(editor, rand, pos.copy());
			
			Coord start = origin.copy();
			start.add(dir, 10);
			start.add(Cardinal.DOWN);
			Coord end = start.copy();
			start.add(Cardinal.left(dir), 14);
			end.add(Cardinal.right(dir), 14);
			RectSolid.fill(editor, rand, start, end, wall, true, false);
			start.add(dir, 4);
			end.add(dir, 4);
			RectSolid.fill(editor, rand, start, end, wall, true, false);
			start.add(Cardinal.UP, 4);
			end.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, start, end, wall, true, false);
			start.add(Cardinal.reverse(dir), 4);
			end.add(Cardinal.reverse(dir), 4);
			RectSolid.fill(editor, rand, start, end, wall, true, false);
		}
	}
	
	private void cell(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		IStair stair = theme.getPrimary().getStair();
		
		for(Cardinal dir : Cardinal.directions) {
			Coord start = origin.copy();
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			Coord end = start.copy();
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, pillar);
			
			start = origin.copy();
			start.add(dir, 2);
			start.add(Cardinal.left(dir));
			start.add(Cardinal.DOWN);
			end = start.copy();
			end.add(Cardinal.right(dir), 2);
			RectSolid.fill(editor, rand, start, end, wall);
			start.add(Cardinal.UP, 4);
			end.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, start, end, wall);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(dir, 2);
				pos.add(o);
				pos.add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(o), true);
				stair.set(editor, pos);
			}
		}
	}

	private void basin(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			Coord start = origin.copy();
			start.add(dir, 13);
			start.add(Cardinal.left(dir), 10);
			start.add(Cardinal.DOWN);
			Coord end = origin.copy();
			end.add(dir, 11);
			end.add(Cardinal.right(dir), 13);
			end.add(Cardinal.DOWN, 8);
			RectSolid.fill(editor, rand, start, end, wall);
		}
				
		Coord start = origin.copy();
		start.add(Cardinal.NORTH, 13);
		start.add(Cardinal.WEST, 13);
		start.add(Cardinal.DOWN, 7);
		Coord end = origin.copy();
		end.add(Cardinal.SOUTH, 13);
		end.add(Cardinal.EAST, 13);
		end.add(Cardinal.DOWN, 10);
		RectSolid.fill(editor, rand, start, end, wall);
	}

	private void addArches(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(Cardinal.DOWN, 6);
			pos.add(dir, 10);
			largeArch(editor, rand, pos.copy(), dir);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord p = pos.copy();
				p.add(o, 6);
				largeArch(editor, rand, p.copy(), dir);
			}
		}
	}
	
	private void largeArch(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		IStair stair = theme.getPrimary().getStair();
		
		Coord start = origin.copy();
		start.add(Cardinal.left(dir), 3);
		Coord end = origin.copy();
		end.add(Cardinal.right(dir), 3);
		end.add(dir);
		end.add(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, start, end, Air.get());
		
		start = origin.copy();
		start.add(dir);
		start.add(Cardinal.UP, 4);
		end = start.copy();
		start.add(Cardinal.left(dir), 3);
		end.add(Cardinal.right(dir), 3);
		RectSolid.fill(editor, rand, start, end, wall);
		
		Coord pos = origin.copy();
		pos.add(dir);
		pos.add(Cardinal.UP, 3);
		Lantern.set(editor, pos, Lantern.SOUL, true);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			start = origin.copy();
			start.add(o, 3);
			start.add(dir);
			end = start.copy();
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, pillar);
			
			pos = origin.copy();
			pos.add(o, 3);
			pos.add(Cardinal.UP, 3);
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, pos);
			pos.add(Cardinal.UP);
			wall.set(editor, rand, pos);
			pos.add(Cardinal.reverse(o));
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos);
			pos.add(dir);
			pos.add(Cardinal.DOWN);
			stair.set(editor, pos);
		}
	}
	
	@Override
	public CellManager getCells() {
		CellManager cells = super.getCells();
		
		Coord start = new Coord(-1, -1, -1);
		Coord end = new Coord(1, -1, 1);
		for(Coord pos :  new RectSolid(start, end).get()){
			cells.add(new Cell(pos, CellState.OBSTRUCTED));
		}
		
		for(Cardinal dir : Cardinal.directions) {
			start = new Coord(0,-1,0);
			start.add(Cardinal.left(dir));
			start.add(dir, 2);
			end = start.copy();
			end.add(Cardinal.right(dir), 2);
			for(Coord pos :  new RectSolid(start, end).get()){
				Cell c = new Cell(pos, CellState.OBSTRUCTED);
				c.addWall(dir);
				cells.add(c);
			}
			end.add(Cardinal.right(dir));
			Cell c = new Cell(end, CellState.OBSTRUCTED);
			c.addWall(dir);
			c.addWall(Cardinal.right(dir));
			cells.add(c);
		}
		
		return cells;
	}
	
	@Override
	public String getName() {
		return Room.RESERVOIR.name();
	}

}
