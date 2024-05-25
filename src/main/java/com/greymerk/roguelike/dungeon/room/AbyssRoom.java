package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CryptFragment;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Campfire;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.Skull;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.util.Color;

import net.minecraft.util.math.random.Random;

public class AbyssRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(this.worldPos);
		Coord origin = this.worldPos.copy().add(direction, Cell.SIZE);
		this.clear(editor, rand, origin);
		this.pillars(editor, rand, origin);
		this.level(editor, rand, origin.copy());
		this.level(editor, rand, origin.copy().add(Cardinal.DOWN, 10));
		this.level(editor, rand, origin.copy().add(Cardinal.DOWN, 20));
		this.doors(editor, rand, origin);
		this.floor(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.fires(editor, rand, origin);
		this.bridge(editor, rand, origin);
		this.features(editor, rand, origin);
	}

	private void features(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy().add(dir, 8).add(Cardinal.DOWN, 6);
			this.feature(editor, rand, pos.copy(), dir);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				this.feature(editor, rand, pos.copy().add(o, 6), dir);
			}
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy().add(dir, 8).add(Cardinal.DOWN, 16);
			this.feature(editor, rand, pos.copy(), dir);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				this.feature(editor, rand, pos.copy().add(o, 6), dir);
			}
		}
	}

	private void feature(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir);
		bb.grow(dir, 5).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		this.decorations(editor, rand, origin, dir);
	}

	private void decorations(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy();
			pos.add(Cardinal.UP).add(o).add(dir);
			this.deco(editor, rand, pos, dir);
			pos.add(Cardinal.UP, 2);
			this.deco(editor, rand, pos, dir);
		}		
	}

	private void deco(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {

		if(rand.nextBoolean()) {
			if(rand.nextBoolean()) {
				Candle.generate(editor, rand, origin.copy(), Color.BLACK); return;
			} else {
				Skull.set(editor, rand, origin.copy(), Cardinal.reverse(dir), Skull.SKELETON); return;	
			}
		}

		CryptFragment crypt = new CryptFragment();
		crypt.setEmpty(rand.nextInt(3) != 0);
		crypt.generate(editor, rand, theme, origin.copy(), dir);
	}
	
	private void bridge(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(Cardinal.DOWN).add(dir, 3);
			bb.grow(dir, 4).grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		}
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN).grow(Cardinal.directions, 2);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		this.cell(editor, rand, origin);
	}

	private void fires(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy().add(dir, 9);
			this.fire(editor, rand, pos.copy().add(Cardinal.right(dir), 3));
			this.fire(editor, rand, pos.copy().add(Cardinal.left(dir), 3));
			this.fire(editor, rand, pos.copy().add(Cardinal.left(dir), 9));
		}
	}

	private void fire(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		
		Campfire.generate(editor, origin, Campfire.SOUL);
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy().add(dir);
			stair.setOrientation(dir, false).set(editor, pos);
			pos.add(Cardinal.UP, 2);
			stair.setOrientation(dir, true).set(editor, pos);
			pos.add(Cardinal.UP);
			theme.getPrimary().getWall().set(editor, rand, pos);
		}
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.UP, 4);
		bb.grow(Cardinal.directions, 7);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		for(Cardinal dir : Cardinal.directions) {
			bb = new BoundingBox(origin.copy());
			bb.add(Cardinal.UP, 4);
			bb.grow(Cardinal.orthogonal(dir), 7);
			bb.add(dir, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			bb.add(dir, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			bb.add(dir, 4);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
		
		
	}

	private void floor(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN, 21);
		bb.grow(Cardinal.directions, 7);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
	}

	private void level(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 9).add(Cardinal.DOWN);
			bb.grow(dir, 6).grow(Cardinal.left(dir), 15).grow(Cardinal.right(dir), 7);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 8).add(Cardinal.DOWN);
			bb.grow(Cardinal.orthogonal(dir), 7);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			bb.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			Coord pos = origin.copy();
			pos.add(dir, 12);
			this.cell(editor, rand, pos.copy().add(Cardinal.right(dir), 6));
			this.cell(editor, rand, pos.copy());
			this.cell(editor, rand, pos.copy().add(Cardinal.left(dir), 6));
			this.cell(editor, rand, pos.copy().add(Cardinal.left(dir), 12));
		}
	}

	private void cell(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		IBlockFactory walls = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.left(dir), 2);
			bb.grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, walls);
			bb = new BoundingBox(origin.copy());
			bb.add(Cardinal.UP, 3).add(dir, 2);
			bb.grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, walls);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(Cardinal.UP, 2).add(dir, 2).add(o);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);	
			}
		}
	}

	private void pillars(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			this.innerPillarSet(editor, rand, origin.copy().add(dir, 8).add(Cardinal.UP, 3), dir);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(dir, 8).add(Cardinal.UP, 3).add(o, 6);
				this.innerPillarSet(editor, rand, pos, dir);
			}
		}
	}

	private void innerPillarSet(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.grow(Cardinal.DOWN, 23);
			bb.add(o, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			Coord pos = origin.copy();
			pos.add(o, 2);
			pos.add(Cardinal.reverse(dir));
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
		}
		Coord pos = origin.copy();
		this.crossBar(editor, rand, pos, dir);
		pos.add(Cardinal.DOWN, 4);
		this.crossBar(editor, rand, pos, dir);
		
		pos.add(Cardinal.DOWN, 6);
		this.crossBar(editor, rand, pos, dir);
		pos.add(Cardinal.DOWN, 4);
		this.crossBar(editor, rand, pos, dir);
		
		pos.add(Cardinal.DOWN, 6);
		this.crossBar(editor, rand, pos, dir);
	}

	private void crossBar(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = new BoundingBox(origin);
		bb.grow(Cardinal.orthogonal(dir));
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy();
			pos.add(o).add(Cardinal.DOWN);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
		}
	}

	private void doors(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			if(this.entrances.get(dir) != Entrance.DOOR) continue;
			Coord pos = origin.copy();
			pos.add(dir, 12);
			Fragment.generate(Fragment.ARCH, editor, rand, theme, pos, dir);
		}
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.UP, 3).grow(Cardinal.directions, 14);
		RectSolid.fill(editor, rand, bb, Air.get());
		bb.add(Cardinal.DOWN, 10);
		RectSolid.fill(editor, rand, bb, Air.get());
		bb.add(Cardinal.DOWN, 10);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.DOWN, 5).grow(Cardinal.directions, 8);
		RectSolid.fill(editor, rand, bb, Air.get());
		bb.add(Cardinal.DOWN, 10);
		RectSolid.fill(editor, rand, bb, Air.get());
	}

	@Override
	public CellManager getCells(Cardinal dir) {
		CellManager cells = super.getCells(dir);
		
		BoundingBox bb = new BoundingBox(new Coord(0,0,0).add(dir, 2));
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions, 2).grow(Cardinal.DOWN);
		bb.getShape(Shape.RECTSOLID).forEach(pos -> cells.add(new Cell(pos, CellState.OBSTRUCTED)));
		
		return cells;
	}
	
	@Override
	public String getName() {
		return Room.ABYSS.name();
	}
}
