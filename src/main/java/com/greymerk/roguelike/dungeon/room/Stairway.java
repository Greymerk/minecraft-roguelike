package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Candle;
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
		buildCell(editor, rand, origin, direction, true);
		Coord middleCell = origin.copy().add(direction, 6).add(Cardinal.DOWN, 6);
		buildCell(editor, rand, middleCell, Cardinal.reverse(direction), true);
		Coord bottomCell = origin.copy().add(direction, 12).add(Cardinal.DOWN, 10);
		buildCell(editor, rand, bottomCell, Cardinal.reverse(direction), false);
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
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(direction, 2 + step).add(Cardinal.UP, 3 - step);
		bb.grow(Cardinal.DOWN, 3).grow(Cardinal.orthogonal(direction));
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin.copy());
		bb.add(direction, 2 + step).add(Cardinal.UP, 3 - step);
		bb.grow(Cardinal.orthogonal(direction));
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		for(Cardinal o : Cardinal.orthogonal(direction)) {
			Coord pos = origin.copy();
			pos.add(direction, 2 + step);
			pos.add(Cardinal.UP, 2 - step);
			pos.add(o);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, rand, pos);
		}
		
		bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.DOWN, 1 + step).add(direction, step - 2);
		bb.grow(direction, 4).grow(Cardinal.orthogonal(direction));
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.DOWN, 1 + step).add(direction, step - 2);
		bb.grow(Cardinal.orthogonal(direction));
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		bb.add(direction);
		stair.setOrientation(direction, false);
		bb.getShape(Shape.RECTSOLID).forEach(p -> {
			stair.set(editor, rand, p);
		});
	}
	
	private void buildCell(IWorldEditor editor, Random rand, Coord origin, Cardinal cellEntry, boolean candles) {
		IStair stair = theme.getPrimary().getStair();
		CellSupport.generate(editor, rand, theme, origin.copy());
		
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
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, p);
			}
		}
		
		if(candles) {
			for(Cardinal o : Cardinal.orthogonal(cellEntry)) {
				bb = BoundingBox.of(origin);
				bb.add(o, 2).grow(Cardinal.orthogonal(o));
				bb.getShape(Shape.RECTSOLID).forEach(pos -> {
					if(rand.nextBoolean()) Candle.generate(editor, rand, pos);
				});
			}
		}
	}

	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();

		cells.add(Cell.of(origin.copy(), CellState.OBSTRUCTED).addWalls(Cardinal.orthogonal(dir)));
		cells.add(Cell.of(origin.copy().add(dir), CellState.OBSTRUCTED).addWalls(Cardinal.orthogonal(dir)));
		cells.add(Cell.of(origin.copy().add(dir, 2), CellState.OBSTRUCTED).addWalls(Cardinal.orthogonal(dir)).addWall(dir));
		
		cells.add(Cell.of(origin.copy().add(Cardinal.DOWN), CellState.OBSTRUCTED).addWall(Cardinal.reverse(dir)).addWalls(Cardinal.orthogonal(dir)));
		cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir), CellState.OBSTRUCTED).addWalls(Cardinal.orthogonal(dir)));
		
		if(this.worldPos != null && this.worldPos.getY() >= 70) {
			cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir, 2), CellState.OBSTRUCTED).addWall(Cardinal.left(dir)).addWall(dir));
			cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir, 2).add(Cardinal.right(dir)), CellState.POTENTIAL));
		} else {
			cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir, 2), CellState.OBSTRUCTED));
			cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir, 3), CellState.POTENTIAL));
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir, 2).add(o), CellState.POTENTIAL));
			}
		}
		
		
		return cells;
	}

	@Override
	public IBounded getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin)
				.grow(Cardinal.reverse(dir), 3)
				.grow(dir, 15)
				.grow(Cardinal.orthogonal(dir), 4)
				.grow(Cardinal.UP, 6)
				.grow(Cardinal.DOWN, 10);
	}
	

	@Override
	public String getName() {
		return Room.STAIRWAY.name();
	}
	
	@Override
	public void determineEntrances(Floor f, Coord floorPos) {}

}
