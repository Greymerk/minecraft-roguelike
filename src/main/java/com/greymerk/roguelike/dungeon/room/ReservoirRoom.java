package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.util.math.random.Random;

public class ReservoirRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = worldPos.copy().add(direction, Cell.SIZE * 2);
		Random rand = editor.getRandom(origin);
		this.clearUpper(editor, rand, origin);
		this.buildCells(editor, rand, origin);
		this.clearBasin(editor, rand, origin);
		this.basin(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.addArches(editor, rand, origin);
		this.addLiquid(editor, rand, origin);
		this.generateExits(editor, rand);
	}
	

	private void clearBasin(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.DOWN, 2);
		bb.grow(Cardinal.directions, 10);
		bb.grow(Cardinal.DOWN, 5);
		RectSolid.fill(editor, rand, bb, Air.get());
	}

	private void clearUpper(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 10).grow(dir, 4);
			bb.grow(Cardinal.left(dir), 9);
			bb.grow(Cardinal.right(dir), 14);
			bb.grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, Air.get());
		}
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.grow(Cardinal.directions, 10);
		bb.grow(Cardinal.UP, 4);
		bb.grow(Cardinal.DOWN);
		RectSolid.fill(editor, rand, bb, Air.get());
	}

	private void addLiquid(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory liquid = theme.getPrimary().getLiquid();
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.add(Cardinal.DOWN, 5).grow(Cardinal.DOWN).grow(Cardinal.directions, 11);
		RectSolid.fill(editor, rand, bb, liquid, Fill.AIR);
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		for(Cardinal dir : Cardinal.directions) {
			// outer rim
			BoundingBox bb = BoundingBox.of(origin);
			bb.add(Cardinal.UP, 4).add(dir, 9).grow(dir).grow(Cardinal.left(dir), 8).grow(Cardinal.right(dir), 9);
			RectSolid.fill(editor, rand, bb, wall);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				bb = BoundingBox.of(origin);
				bb.add(Cardinal.UP, 4).add(dir, 2).grow(o, 8);
				RectSolid.fill(editor, rand, bb, wall);
				Coord pos = origin.copy().add(Cardinal.UP, 3).add(dir, 2).add(o, 8);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
				pos.add(o);
				wall.set(editor, rand, pos);
				pos.add(Cardinal.DOWN);
				stair.set(editor, rand, pos);
				
				bb = BoundingBox.of(origin);
				bb.add(Cardinal.UP, 4).add(dir, 4).grow(o, 8);
				RectSolid.fill(editor, rand, bb, wall);
				pos = origin.copy().add(Cardinal.UP, 3).add(dir, 4).add(o, 8);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
				pos.add(o);
				wall.set(editor, rand, pos);
				pos.add(Cardinal.DOWN);
				stair.set(editor, rand, pos);
				
				pos = origin.copy().add(Cardinal.UP, 3).add(o, 9).add(dir, 8);
				wall.set(editor, rand, pos);
				pos.add(Cardinal.DOWN);
				stair.set(editor, rand, pos);
			}
		}
	}

	private void buildCells(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 10).add(Cardinal.DOWN);
			bb.grow(dir, 3);
			bb.grow(Cardinal.left(dir), 13);
			bb.grow(Cardinal.right(dir), 9);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 12);
			cell(editor, rand, pos.copy());
			pos.add(Cardinal.left(dir), 6);
			cell(editor, rand, pos.copy());
			settings.getWallFragment(rand).generate(editor, rand, settings, pos.copy(), dir);
			pos.add(Cardinal.right(dir), 12);
			cell(editor, rand, pos.copy());
			settings.getWallFragment(rand).generate(editor, rand, settings, pos.copy(), dir);
			pos.add(Cardinal.right(dir), 6);
			cell(editor, rand, pos.copy());
			settings.getWallFragment(rand).generate(editor, rand, settings, pos.copy(), dir);
			settings.getWallFragment(rand).generate(editor, rand, settings, pos.copy(), Cardinal.right(dir));
			
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 10).add(Cardinal.DOWN);
			bb.grow(Cardinal.orthogonal(dir), 14);
			RectSolid.fill(editor, rand, bb, wall, Fill.AIR);
			bb.add(dir, 4);
			RectSolid.fill(editor, rand, bb, wall, Fill.AIR);
			bb.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, wall, Fill.AIR);
			bb.add(Cardinal.reverse(dir), 2);
			RectSolid.fill(editor, rand, bb, wall, Fill.AIR);
		}
	}
	
	private void cell(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		IStair stair = theme.getPrimary().getStair();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin);
			bb.add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, pillar);
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 2).add(Cardinal.left(dir)).add(Cardinal.DOWN).grow(Cardinal.right(dir), 2);
			RectSolid.fill(editor, rand, bb, wall);
			bb.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, wall);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(dir, 2);
				pos.add(o);
				pos.add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(o), true);
				stair.set(editor, rand, pos);
			}
		}
		CellSupport.generate(editor, rand, theme, origin.copy());
	}

	private void basin(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 11).grow(dir, 2);
			bb.grow(Cardinal.left(dir), 10).grow(Cardinal.right(dir), 13);
			bb.add(Cardinal.DOWN).grow(Cardinal.DOWN, 8);
			RectSolid.fill(editor, rand, bb, wall);	
		}
			
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.grow(Cardinal.directions, 13);
		bb.add(Cardinal.DOWN, 7).grow(Cardinal.DOWN, 3);
		RectSolid.fill(editor, rand, bb, wall);
		
		Coord pos = origin.copy();
		pos.add(Cardinal.DOWN, 9);
		CellSupport.generate(editor, rand, theme, pos.copy());
		
		for(Cardinal dir : Cardinal.directions) {
			pos = origin.copy().add(Cardinal.DOWN, 9).add(dir, 6);
			CellSupport.generate(editor, rand, theme, pos.copy());
			pos.add(Cardinal.left(dir), 6);
			CellSupport.generate(editor, rand, theme, pos.copy());
		}
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
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.grow(Cardinal.orthogonal(dir), 3).grow(dir).grow(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin);
		bb.add(dir).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 3);
		RectSolid.fill(editor, rand, bb, wall);
		
		Coord pos = origin.copy();
		pos.add(dir);
		pos.add(Cardinal.UP, 3);
		Lantern.set(editor, pos, Lantern.SOUL, true);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			bb = BoundingBox.of(origin);
			bb.add(o, 3).add(dir).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, pillar);
			
			pos = origin.copy().add(o, 3).add(Cardinal.UP, 3);
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
			pos.add(Cardinal.UP);
			wall.set(editor, rand, pos);
			pos.add(Cardinal.reverse(o));
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
			pos.add(dir).add(Cardinal.DOWN);
			stair.set(editor, rand, pos);
		}
	}
	
	@Override
	public CellManager getCells(Cardinal dir) {
		
		CellManager cells = super.getCells(dir);
		
		Coord origin = Coord.ZERO.add(Cardinal.DOWN);
		BoundingBox bb = BoundingBox.of(origin);
		bb.add(dir, 2).grow(Cardinal.directions);
		bb.getShape(Shape.RECTSOLID).get().forEach(pos -> {
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
		});
		
		for(Cardinal d : Cardinal.directions) {
			cells.add(Cell.of(origin.copy().add(dir, 2).add(d, 2), CellState.OBSTRUCTED, this).addWall(d));
			cells.add(Cell.of(origin.copy().add(dir, 2).add(d, 2).add(Cardinal.left(d), 2), CellState.OBSTRUCTED, this).addWall(d).addWall(Cardinal.left(d)));
			for(Cardinal o : Cardinal.orthogonal(d)) {
				cells.add(Cell.of(origin.copy().add(dir, 2).add(d, 2).add(o), CellState.OBSTRUCTED, this).addWall(d));
			}
		}
		
		
		return cells;
	}
	
	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return super.getBoundingBox(origin, dir).grow(Cardinal.DOWN, 10);
	}
	
	@Override
	public String getName() {
		return Room.RESERVOIR.name();
	}

}
