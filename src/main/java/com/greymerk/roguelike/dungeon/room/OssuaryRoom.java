package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.Skull;
import com.greymerk.roguelike.editor.blocks.slab.ISlab;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.util.Color;

import net.minecraft.util.math.random.Random;

public class OssuaryRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.worldPos.copy();
		Random rand = editor.getRandom(origin);
		this.clear(editor, rand, origin);
		this.corners(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.skullShelves(editor, rand, origin);
		for(Cardinal dir : Cardinal.directions) {
			if(this.entrances.contains(dir)) {
				this.entry(editor, rand, origin, dir);
			} else {
				this.faceWall(editor, rand, origin, dir);
			}
		}
		this.floor(editor, rand, origin);
		this.supports(editor, rand, origin);
	}

	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin);
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, pos);
			pos.add(Cardinal.left(dir), 6);
			Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, pos);
		}
	}

	private void faceWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 9);
		bb.grow(Cardinal.orthogonal(dir), 3);
		bb.grow(Cardinal.UP, 5);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		bb = new BoundingBox(origin.copy());
		bb.add(dir, 7);
		bb.add(Cardinal.UP, 5);
		bb.grow(Cardinal.orthogonal(dir), 2);
		bb.grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 7);
			bb.add(o, 3);
			bb.grow(Cardinal.UP, 5);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			Coord pos = origin.copy();
			pos.add(dir, 7);
			pos.add(o, 2);
			stair.setOrientation(Cardinal.reverse(o), false).set(editor, pos);
			pos.add(Cardinal.UP, 4);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			pos.add(Cardinal.reverse(dir));
			pos.add(o);
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
			pos.add(Cardinal.UP);
			bb = new BoundingBox(pos.copy());
			bb.grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
		
		Coord pos = origin.copy();
		pos.add(dir, 8);
		this.face(editor, rand, pos, dir);
		
	}

	private void face(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		ISlab slab = theme.getPrimary().getSlab();
		IStair stair = theme.getPrimary().getStair();
		IBlockFactory wall = theme.getPrimary().getWall();
		
		// middle of face
		Coord pos = origin.copy();
		slab.upsideDown(false).set(editor, pos);
		pos.add(Cardinal.UP);
		slab.upsideDown(true).set(editor, pos);
		pos.add(Cardinal.UP);
		candle(editor, rand, pos);
		pos.add(Cardinal.UP);
		wall.set(editor, rand, pos);
		pos.add(Cardinal.UP);
		BoundingBox bb = new BoundingBox(pos.copy());
		bb.grow(Cardinal.orthogonal(dir), 2);
		RectSolid.fill(editor, rand, bb, wall);
		
		// sides of face
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			pos = origin.copy();
			pos.add(o);
			slab.upsideDown(false).set(editor, pos);
			pos.add(Cardinal.UP);
			stair.setOrientation(o, true).set(editor, pos);
			pos.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			pos.add(Cardinal.UP);
			candle(editor, rand, pos);
			
			pos = origin.copy();
			pos.add(o, 2);
			wall.set(editor, rand, pos);
			pos.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			pos.add(Cardinal.UP);
			wall.set(editor, rand, pos);
			pos.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
		}
	}
	
	private void candle(IWorldEditor editor, Random rand, Coord origin) {
		Color c = rand.nextBoolean() ? Color.BLACK : Color.RED;
		int count = rand.nextInt(2) + 2;
		Candle.generate(editor, origin, c, count);
	}

	private void floor(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions, 9);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
	}

	private void skullShelves(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(dir, 5);
				pos.add(o, 5);
				this.skullShelf(editor, rand, pos, dir);
			}
		}
		
	}

	private void skullShelf(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 3);
		bb.grow(dir);
		bb.grow(Cardinal.orthogonal(dir), 2);
		bb.grow(Cardinal.UP, 6);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy();
			pos.add(dir, 3);
			pos.add(o);
			pos.add(Cardinal.UP);
			for(int i = 0; i < 3; ++i) {
				this.nich(editor, rand, pos, dir);
				pos.add(Cardinal.UP, 2);
			}
		}
	}

	private void nich(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Air.get().set(editor, origin);
		if(rand.nextBoolean()) {
			if(rand.nextBoolean()) this.candle(editor, rand, origin);
		} else {
			Skull type = rand.nextInt(10) == 0 ? Skull.WITHER : Skull.SKELETON;
			Skull.set(editor, rand, origin, Cardinal.reverse(dir), type);	
		}
		
	}

	private void entry(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 5);
		bb.add(Cardinal.UP, 7);
		bb.grow(Cardinal.orthogonal(dir), 3);
		for(int i = 0; i < 4; ++i) {
			bb.grow(Cardinal.DOWN);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			bb.add(dir);	
		}
		
		for(Cardinal orth : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy();
			pos.add(dir, 8);
			pos.add(orth, 2);
			bb = new BoundingBox(pos.copy());
			bb.grow(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			pos.add(Cardinal.UP, 2);
			
			IStair stair = theme.getPrimary().getStair();
			
			for(int i = 0; i < 4; ++i) {
				for(Cardinal d : Cardinal.directions) {
					Coord p = pos.copy();
					p.add(d);
					stair.setOrientation(d, true);
					stair.set(editor, p, true, false);
				}
				pos.add(Cardinal.reverse(dir));
				pos.add(Cardinal.UP);
			}
			
			pos = origin.copy();
			pos.add(dir, 3);
			pos.add(orth, 2);
			pos.add(Cardinal.UP, 6);
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, pos);
			pos.add(dir);
			theme.getPrimary().getWall().set(editor, rand, pos);
		}
		
		bb = new BoundingBox(origin.copy());
		bb.add(dir, 8);
		bb.grow(Cardinal.orthogonal(dir));
		bb.grow(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		Coord door = origin.copy().add(dir, 6);
		Fragment.generate(Fragment.ARCH, editor, rand, theme, door, dir);
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(Cardinal.UP, 7);
			bb.add(dir, 2);
			bb.grow(Cardinal.orthogonal(dir), 7);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
	}

	private void corners(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 7);
			bb.add(Cardinal.left(dir), 7);
			bb.grow(dir);
			bb.grow(Cardinal.left(dir));
			bb.grow(Cardinal.UP, 5);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 3);
			bb.add(Cardinal.left(dir), 3);
			bb.add(Cardinal.UP, 6);
			bb.grow(dir, 6);
			bb.grow(Cardinal.left(dir), 6);
			bb.grow(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
						
			Coord pos = origin.copy();
			pos.add(dir, 5);
			pos.add(Cardinal.left(dir), 5);
			pos.add(Cardinal.UP, 6);
			Air.get().set(editor, pos);
			for(Cardinal d : Cardinal.directions) {
				Coord p = pos.copy();
				p.add(d);
				stair.setOrientation(Cardinal.reverse(d), true).set(editor, p);
			}
			for(Cardinal d : Cardinal.directions) {
				Coord p = pos.copy();
				p.add(d);
				p.add(Cardinal.left(d));
				stair.setOrientation(Cardinal.reverse(d), true).set(editor, p);
			}
		}
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.directions, 7);
		bb.grow(Cardinal.UP, 7);
		RectSolid.fill(editor, rand, bb, Air.get());
	}

	@Override
	public String getName() {
		return Room.OSSUARY.name();
	}

}
