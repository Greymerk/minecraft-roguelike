package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.layout.Exit;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.util.math.random.Random;

public class CisternRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(this.worldPos);
		Coord origin = this.worldPos.copy().add(direction, Cell.SIZE);
		
		this.clear(editor, rand, origin);
		this.walls(editor, rand, origin);
		this.bridges(editor, rand, origin);
		this.water(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.supports(editor, rand, origin.copy().add(Cardinal.DOWN, 2));
		this.generateExits(editor, rand);
	}
	
	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin.copy());
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			CellSupport.generate(editor, rand, theme, pos);
			pos.add(Cardinal.left(dir), 6);
			CellSupport.generate(editor, rand, theme, pos);
		}
	}
	
	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox.of(origin).add(Cardinal.UP, 5).add(dir, 6)
				.grow(dir).grow(Cardinal.left(dir), 5).grow(Cardinal.right(dir), 7)
				.fill(editor, rand, wall);
			
			BoundingBox.of(origin.copy()).add(Cardinal.UP, 5).add(dir, 2)
				.grow(Cardinal.orthogonal(dir), 5)
				.fill(editor, rand, wall, Fill.AIR);
		}
		
		BoundingBox.of(origin).add(Cardinal.UP, 6).grow(Cardinal.directions, 8).fill(editor, rand, wall, Fill.SOLID);
	}

	private void water(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).add(Cardinal.DOWN, 2)
			.grow(Cardinal.directions, 9).grow(Cardinal.DOWN)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 7)
			.fill(editor, rand, theme.getPrimary().getLiquid(), Fill.AIR);
	}

	private void bridges(IWorldEditor editor, Random rand, Coord origin) {
		
		IBlockFactory floor = theme.getPrimary().getFloor();
		IBlockFactory wall = theme.getPrimary().getWall();
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions, 2);
		RectSolid.fill(editor, rand, bb, wall);
		
		bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions);
		RectSolid.fill(editor, rand, bb, floor);
		
		for(Exit exit : this.exits) {
			if(exit.type() != ExitType.DOOR) continue;
			Cardinal dir = exit.dir();
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 3);
			bb.add(Cardinal.DOWN);
			bb.grow(Cardinal.orthogonal(dir), 2);
			bb.grow(dir, 4);
			RectSolid.fill(editor, rand, bb, wall);
			bb.grow(Cardinal.orthogonal(dir), -1);
			RectSolid.fill(editor, rand, bb, floor);
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 2);
			pos.add(Cardinal.left(dir), 2);
			wall.set(editor, rand, pos);
			
			if(this.getExitType(dir) != ExitType.DOOR) {
				bb = BoundingBox.of(origin.copy());
				bb.add(dir, 2);
				bb.grow(Cardinal.orthogonal(dir));
				RectSolid.fill(editor, rand, bb, IronBar.get());
			} else {
				for(Cardinal o : Cardinal.orthogonal(dir)) {
					bb = BoundingBox.of(origin.copy());
					bb.add(dir, 3);
					bb.add(o, 2);
					bb.grow(dir, 4);
					RectSolid.fill(editor, rand, bb, IronBar.get());
				}
			}
		}
	}

	private void walls(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 9).grow(Cardinal.orthogonal(dir), 9).grow(Cardinal.DOWN).grow(Cardinal.UP, 5).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		});
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(Cardinal.DOWN);
			bb.add(dir, 8);
			bb.grow(Cardinal.orthogonal(dir), 8);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			bb.add(Cardinal.UP, 4);
			bb.grow(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
		
		for(Cardinal dir : Cardinal.directions) {
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(o, 3);
				pos.add(dir, 6);
				this.wallPillarPiece(editor, rand, pos.copy(), dir);
			}
		}
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 8);
			bb.add(Cardinal.left(dir), 8);
			bb.grow(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			for(Cardinal d : Cardinal.directions) {
				Coord pos = origin.copy();
				pos.add(dir, 8).add(Cardinal.left(dir), 8).add(Cardinal.UP, 2);
				pos.add(d);
				stair.setOrientation(d, true);
				stair.set(editor, rand, pos, Fill.AIR);
			}
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 8).add(Cardinal.left(dir), 8);
			bb.grow(Cardinal.UP);
			bb.add(Cardinal.reverse(dir));
			bb.add(Cardinal.right(dir));
			bb.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			pos.add(Cardinal.left(dir), 6);
			settings.getWallFragment(rand).generate(editor, rand, settings, pos.copy(), dir);
			settings.getWallFragment(rand).generate(editor, rand, settings, pos.copy(), Cardinal.left(dir));
		}
	}

	private void wallPillarPiece(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		Coord pos = origin.copy();
		pos.add(dir, 2);
		pos.add(Cardinal.UP, 2);
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
		pos.add(Cardinal.UP, 2);
		pos.add(Cardinal.reverse(dir));
		stair.set(editor, rand, pos);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 2);
			bb.add(o);
			bb.grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = BoundingBox.of(origin.copy());
			bb.add(dir);
			bb.add(o);
			bb.add(Cardinal.UP, 3);
			bb.grow(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			pos = origin.copy();
			pos.add(dir);
			pos.add(o);
			pos.add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
			pos.add(Cardinal.reverse(dir));
			pos.add(Cardinal.UP, 2);
			stair.set(editor, rand, pos);
			pos.add(dir);
			pos.add(o);
			stair.setOrientation(o, true).set(editor, rand, pos);
			pos.add(Cardinal.DOWN, 2);
			pos.add(dir);
			stair.set(editor, rand, pos);
		}
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin.copy()).add(Cardinal.DOWN)
			.grow(Cardinal.UP, 6).grow(Cardinal.directions, 8)
			.fill(editor, rand, Air.get(), Fill.SOLID);
	}

	@Override
	public String getName() {
		return Room.CISTERN.name();
	}
}
