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
		this.fillWalls(editor, rand, origin.copy());
		buildCell(editor, rand, origin, direction);
		Coord middleCell = origin.copy().add(direction, 6).add(Cardinal.DOWN, 6);
		buildCell(editor, rand, middleCell, Cardinal.reverse(direction));
		Coord bottomCell = origin.copy().add(direction, 12).add(Cardinal.DOWN, 10);
		buildCell(editor, rand, bottomCell, Cardinal.reverse(direction));
		this.buildSteps(editor, rand, origin.copy());
		this.addDoors(editor, rand);
		
		
	}

	private void fillWalls(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal o : Cardinal.orthogonal(direction)) {
			for(int i = 0; i < 10; ++i) {
				BoundingBox bb = BoundingBox.of(origin);
				bb.add(o, 2).add(direction, i - 1).add(Cardinal.DOWN, i + 1).grow(Cardinal.UP, 5);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			}
		}
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
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.grow(Cardinal.directions, 2).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin);
		bb.grow(Cardinal.directions).add(Cardinal.DOWN);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		bb = BoundingBox.of(origin);
		bb.add(Cardinal.UP, 4).grow(Cardinal.directions);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), false, true);
		
		for(Cardinal dir : Cardinal.directions) {
			bb = BoundingBox.of(origin);
			bb.add(Cardinal.DOWN).add(dir, 2).grow(Cardinal.orthogonal(dir), 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 3).grow(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), false, true);
		}
		
		bb = BoundingBox.of(origin);
		bb.add(cellEntry, 3).add(Cardinal.DOWN).grow(Cardinal.orthogonal(cellEntry), 2);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			bb = BoundingBox.of(origin);
			bb.add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			theme.getPrimary().getWall().set(editor, rand, origin.copy().add(dir, 2).add(Cardinal.left(dir), 2).add(Cardinal.UP, 2));
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 2).add(Cardinal.left(dir), 2).add(Cardinal.UP, 3).grow(Cardinal.right(dir), 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord p = origin.copy();
				p.add(dir, 2).add(Cardinal.UP, 2).add(o);
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
		Cell topEnd = new Cell(pos.copy(), CellState.OBSTRUCTED);
		for(Cardinal dir : Cardinal.directions) {
			if(dir == Cardinal.reverse(direction)) continue;
			topEnd.addWall(dir);
		}
		cells.add(topEnd);
				
		pos = origin.copy();
		pos.add(Cardinal.DOWN);
		Cell bottomEnd = new Cell(pos.copy(), CellState.OBSTRUCTED);
		for(Cardinal dir : Cardinal.directions) {
			if(dir == direction) continue;
			bottomEnd.addWall(dir);
		}
		cells.add(bottomEnd);
		pos.add(direction);
		Cell bottomMid = new Cell(pos.copy(), CellState.OBSTRUCTED);
		for(Cardinal dir : Cardinal.orthogonal(direction)) {
			bottomMid.addWall(dir);
		}
		cells.add(bottomMid);
		pos.add(direction);
		cells.add(new Cell(pos.copy(), CellState.OBSTRUCTED));
		
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
		Coord origin = this.getWorldPos().copy();
		Coord start = origin.copy();
		start.add(Cardinal.reverse(direction), 3);
		start.add(Cardinal.UP, 3);
		start.add(Cardinal.left(direction), 4);
		
		Coord end = origin.copy();
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
