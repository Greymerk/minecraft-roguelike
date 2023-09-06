package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBars;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.util.math.random.Random;

public class CisternRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(this.worldPos);
		Coord origin = this.worldPos.copy();
		
		this.clear(editor, rand, origin);
		this.walls(editor, rand, origin);
		this.bridges(editor, rand, origin);
		this.water(editor, rand, origin);
		this.ceiling(editor, rand, origin);
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(Cardinal.UP, 5);
			bb.add(dir, 6);
			bb.grow(dir);
			bb.grow(Cardinal.left(dir), 5);
			bb.grow(Cardinal.right(dir), 7);
			RectSolid.fill(editor, rand, bb, wall);
			
			bb = new BoundingBox(origin.copy());
			bb.add(Cardinal.UP, 5);
			bb.add(dir, 2);
			bb.grow(Cardinal.orthogonal(dir), 5);
			RectSolid.fill(editor, rand, bb, wall, true, false);
		}
	}

	private void water(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN, 2);
		bb.grow(Cardinal.directions, 9);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions, 7);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getLiquid(), true, false);
	}

	private void bridges(IWorldEditor editor, Random rand, Coord origin) {
		
		IBlockFactory floor = theme.getPrimary().getFloor();
		IBlockFactory wall = theme.getPrimary().getWall();
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions, 2);
		RectSolid.fill(editor, rand, bb, wall);
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions);
		RectSolid.fill(editor, rand, bb, floor);
		
		for(Cardinal dir : this.entrances) {
			bb = new BoundingBox(origin.copy());
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
			
			if(!this.entrances.contains(dir)) {
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 2);
				bb.grow(Cardinal.orthogonal(dir));
				RectSolid.fill(editor, rand, bb, IronBars.get(dir));
			} else {
				for(Cardinal o : Cardinal.orthogonal(dir)) {
					bb = new BoundingBox(origin.copy());
					bb.add(dir, 3);
					bb.add(o, 2);
					bb.grow(dir, 4);
					RectSolid.fill(editor, rand, bb, IronBars.get(o));
				}
			}
		}
		
		for(Cardinal dir : this.entrances) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			Fragment.generate(Fragment.ARCH, editor, rand, theme, pos, dir);
		}
		
		
	}

	private void walls(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
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
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 8);
			bb.add(Cardinal.left(dir), 8);
			bb.grow(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			for(Cardinal d : Cardinal.directions) {
				Coord pos = origin.copy();
				pos.add(dir, 8).add(Cardinal.left(dir), 8).add(Cardinal.UP, 2);
				pos.add(d);
				stair.setOrientation(d, true);
				stair.set(editor, pos, true, false);
			}
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 8).add(Cardinal.left(dir), 8);
			bb.grow(Cardinal.UP);
			bb.add(Cardinal.reverse(dir));
			bb.add(Cardinal.right(dir));
			bb.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
	}

	private void wallPillarPiece(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		Coord pos = origin.copy();
		pos.add(dir, 2);
		pos.add(Cardinal.UP, 2);
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
		pos.add(Cardinal.UP, 2);
		pos.add(Cardinal.reverse(dir));
		stair.set(editor, pos);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 2);
			bb.add(o);
			bb.grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir);
			bb.add(o);
			bb.add(Cardinal.UP, 3);
			bb.grow(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			pos = origin.copy();
			pos.add(dir);
			pos.add(o);
			pos.add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
			pos.add(Cardinal.reverse(dir));
			pos.add(Cardinal.UP, 2);
			stair.set(editor, pos);
			pos.add(dir);
			pos.add(o);
			stair.setOrientation(o, true).set(editor, pos);
			pos.add(Cardinal.DOWN, 2);
			pos.add(dir);
			stair.set(editor, pos);
		}
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.UP, 6);
		bb.grow(Cardinal.directions, 8);
		RectSolid.fill(editor, rand, bb, Air.get(), false, true);
	}

	@Override
	public String getName() {
		return Room.CISTERN.name();
	}
}
