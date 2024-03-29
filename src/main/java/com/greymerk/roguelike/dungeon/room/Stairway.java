package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.util.math.random.Random;

public class Stairway extends AbstractRoom implements IRoom {
	
	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(getWorldPos());
		Coord origin = this.worldPos.copy();
		buildCell(editor, rand, origin, direction);
		Coord middleCell = origin.copy().add(direction, 6).add(Cardinal.DOWN, 6);
		buildCell(editor, rand, middleCell, Cardinal.reverse(direction));
		Coord bottomCell = origin.copy().add(direction, 12).add(Cardinal.DOWN, 10);
		buildCell(editor, rand, bottomCell, Cardinal.reverse(direction));
		this.buildSteps(editor, rand, origin.copy());
		this.addDoors(editor, rand);
	}

	private void addDoors(IWorldEditor editor, Random rand) {
		Fragment.generate(Fragment.ARCH, editor, rand, theme, worldPos, Cardinal.reverse(direction));
		
		for(Cardinal dir : this.getEntrancesFromType(Entrance.DOOR)) {
			Fragment.generate(Fragment.ARCH, editor, rand, theme, worldPos, dir);
		}
	}
	
	private void buildSteps(IWorldEditor editor, Random rand, Coord origin) {
		for(int i = 0; i < 10; ++i) {
			this.oneStep(editor, rand, origin, i);
		}
	}
	
	private void oneStep(IWorldEditor editor, Random rand, Coord origin, int step) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(direction, 2 + step).add(Cardinal.UP, 3 - step);
		bb.grow(Cardinal.DOWN, 3).grow(Cardinal.orthogonal(direction));
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = new BoundingBox(origin.copy());
		bb.add(direction, 2 + step).add(Cardinal.UP, 3 - step);
		bb.grow(Cardinal.orthogonal(direction));
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		for(Cardinal o : Cardinal.orthogonal(direction)) {
			Coord pos = origin.copy();
			pos.add(direction, 2 + step);
			pos.add(Cardinal.UP, 2 - step);
			pos.add(o);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos);
		}
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN, 1 + step).add(direction, step - 2);
		bb.grow(direction, 4).grow(Cardinal.orthogonal(direction));
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN, 1 + step).add(direction, step - 2);
		bb.grow(Cardinal.orthogonal(direction));
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		bb.add(direction);
		stair.setOrientation(direction, false);
		bb.getShape(Shape.RECTSOLID).forEach(p -> {
			stair.set(editor, p);
		});
	}
	
	private void buildCell(IWorldEditor editor, Random rand, Coord origin, Cardinal cellEntry) {
		IStair stair = theme.getPrimary().getStair();
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy());
		
		Coord start = new Coord(origin);
		start.add(new Coord(-2, 0, -2));
		Coord end = new Coord(origin);
		end.add(new Coord(2, 3, 2));
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		start = new Coord(-1, -1, -1).add(origin);
		end = new Coord(1, -1, 1).add(origin);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			start = origin.copy();
			start.add(Cardinal.DOWN);
			start.add(dir, 2);
			end = start.copy();
			start.add(Cardinal.left(dir));
			end.add(Cardinal.right(dir));
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
		}
		
		start = origin.copy();
		start.add(cellEntry, 3);
		start.add(Cardinal.DOWN);
		end = start.copy();
		start.add(Cardinal.left(cellEntry), 2);
		end.add(Cardinal.right(cellEntry), 2);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			start = new Coord(origin);
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			end = new Coord(start);
			start.add(Cardinal.DOWN);
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			
			start = new Coord(end);
			end.add(Cardinal.right(dir), 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord p = new Coord(origin);
				p.add(dir, 2);
				p.add(Cardinal.UP, 2);
				p.add(o);
				stair.setOrientation(Cardinal.reverse(o), true);
				stair.set(editor, p, true, true);
			}
		}
	}

	@Override
	public CellManager getCells() {
		CellManager cells = new CellManager();
		
		Coord origin = new Coord(0,0,0);
		Cell entry = new Cell(origin.copy().add(Cardinal.reverse(direction)), CellState.POTENTIAL);
		cells.add(entry);
		
		Coord pos = origin.copy();
		Cell topFirst = new Cell(pos.copy(), CellState.OBSTRUCTED);
		Cardinal.orthogonal(direction).forEach(dir -> topFirst.addWall(dir));
		cells.add(topFirst);
		pos.add(direction);
		Cell topMid = new Cell(pos.copy(), CellState.OBSTRUCTED);
		Cardinal.orthogonal(direction).forEach(dir -> topMid.addWall(dir));
		cells.add(topMid);
		pos.add(direction);
		Cell topEnd = new Cell(new Coord(pos), CellState.OBSTRUCTED);
		for(Cardinal dir : Cardinal.directions) {
			if(dir == Cardinal.reverse(direction)) continue;
			topEnd.addWall(dir);
		}
		cells.add(topEnd);
				
		pos = new Coord(origin);
		pos.add(Cardinal.DOWN);
		Cell bottomEnd = new Cell(new Coord(pos), CellState.OBSTRUCTED);
		for(Cardinal dir : Cardinal.directions) {
			if(dir == direction) continue;
			bottomEnd.addWall(dir);
		}
		cells.add(bottomEnd);
		pos.add(direction);
		Cell bottomMid = new Cell(new Coord(pos), CellState.OBSTRUCTED);
		for(Cardinal dir : Cardinal.orthogonal(direction)) {
			bottomMid.addWall(dir);
		}
		cells.add(bottomMid);
		pos.add(direction);
		cells.add(new Cell(new Coord(pos), CellState.OBSTRUCTED));
		
		// spiral above entry level, otherwise random directions
		if(this.worldPos != null && Dungeon.getLevelFromY(this.worldPos.getY()) == 0) {
			Coord p = pos.copy();
			p.add(Cardinal.right(direction));
			cells.add(new Cell(p.copy(), CellState.POTENTIAL));
		} else {
			for(Cardinal dir : Cardinal.directions) {
				if(dir == Cardinal.reverse(direction)) continue;
				Coord p = pos.copy();
				p.add(dir);
				cells.add(new Cell(p.copy(), CellState.POTENTIAL));
			}
		}
		return cells;
	}

	@Override
	public IBounded getBoundingBox() {
		Coord origin = new Coord(this.getWorldPos());
		Coord start = new Coord(origin);
		start.add(Cardinal.reverse(direction), 3);
		start.add(Cardinal.UP, 3);
		start.add(Cardinal.left(direction), 4);
		
		Coord end = new Coord(origin);
		end.add(direction, 13);
		end.add(Cardinal.DOWN, 10);
		end.add(Cardinal.right(direction), 4);
		
		return new BoundingBox(start, end);
	}
	

	@Override
	public String getName() {
		return Room.STAIRWAY.name();
	}
	
	@Override
	public boolean isDirectional() {
		return true;
	}

	@Override
	public void determineEntrances(Floor f, Coord floorPos) {}

}
