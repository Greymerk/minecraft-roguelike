package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CryptFragment;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.util.math.random.Random;

public class CryptRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.worldPos.copy();
		Random rand = editor.getRandom(origin);
		roomCenter(editor, rand, origin);
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy());
		
		for(Cardinal dir : Cardinal.directions) {
			if(this.getEntrance(dir) == Entrance.DOOR) {				
				this.entryWay(editor, rand, origin.copy(), dir);
			} else {
				this.cryptWall(editor, rand, origin.copy().add(dir, 5), dir);
			}
			
			Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy().add(dir, 6));
		}
	}
	
	private void entryWay(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = this.theme.getPrimary().getStair();
		IBlockFactory wall = this.theme.getPrimary().getWall();
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(dir, 5).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, BlockType.get(BlockType.AIR));
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 5).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 4).grow(dir, 5);
		RectSolid.fill(editor, rand, bb, this.theme.getPrimary().getFloor());
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 5).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 4).grow(dir, 3);
		RectSolid.fill(editor, rand, bb, wall);
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 8).add(Cardinal.UP, 3);
		bb.grow(Cardinal.orthogonal(dir), 3);
		RectSolid.fill(editor, rand, bb, wall);
		bb.add(Cardinal.reverse(dir));
		List<Coord> lc = bb.getShape(Shape.RECTSOLID).get();
		stair.setOrientation(Cardinal.reverse(dir), true);
		lc.forEach(c -> stair.set(editor, c, true, false));
		
		for(Cardinal o : Cardinal.orthogonal(dir)){
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 8).add(o, 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			Coord pos = origin.copy().add(dir, 7).add(o, 2).add(Cardinal.UP, 3);
			wall.set(editor, rand, pos);
			pos.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, pos);
			
			pos = origin.copy().add(dir, 8).add(o).add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			pos.add(o).add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
			pos.add(dir).add(o);
			stair.set(editor, pos);
		}
		
		if(this.getEntrance(Cardinal.left(dir)) == Entrance.DOOR) {
			Coord pos = origin.copy().add(dir, 6).add(Cardinal.left(dir), 6);
			List<Cardinal> doors = new ArrayList<Cardinal>();
			doors.add(Cardinal.reverse(dir));
			doors.add(Cardinal.right(dir));
			this.cornerCell(editor, rand, pos, doors);
		} else {
			Coord pos = origin.copy().add(dir, 6).add(Cardinal.left(dir), 2);
			this.entrySideWall(editor, rand, pos, Cardinal.left(dir));
		}
		
		if(this.getEntrance(Cardinal.right(dir)) != Entrance.DOOR) {
			Coord pos = origin.copy().add(dir, 6).add(Cardinal.right(dir), 2);
			this.entrySideWall(editor, rand, pos, Cardinal.right(dir));
		}
		
		Fragment.generate(Fragment.ARCH, editor, rand, theme, origin.copy().add(dir, 6), dir);
	}

	private void entrySideWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(dir, 2).grow(Cardinal.orthogonal(dir)).add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, bb, BlockType.get(BlockType.AIR));
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir));
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 3).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), false, true);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 2).add(o, 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			Coord pos = origin.copy().add(dir, 2).add(o).add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
		}
		
		settings.getWallFragment(rand).generate(editor, rand, theme, origin, dir);
	}

	private void cornerCell(IWorldEditor editor, Random rand, Coord origin, List<Cardinal> doors) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = BoundingBox.of(origin.copy()).grow(Cardinal.directions, 2).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, BlockType.get(BlockType.AIR));
		
		bb = BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			bb = BoundingBox.of(origin.copy()).add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = BoundingBox.of(origin.copy()).add(dir, 2).add(Cardinal.UP).grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy().add(dir, 2).add(Cardinal.UP, 2).add(o);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			}

			if(!doors.contains(dir)) {
				bb = BoundingBox.of(origin.copy());
				bb.add(dir, 3).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), false, true);
				settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy(), dir);
			}


		}
	}

	private void roomCenter(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = this.theme.getPrimary().getStair();
		IBlockFactory wall = this.theme.getPrimary().getWall();
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.UP, 4).grow(Cardinal.UP, 3).grow(Cardinal.directions, 4);
		RectHollow.fill(editor, rand, bb, wall);
		
		bb = BoundingBox.of(origin.copy());
		bb.grow(Cardinal.UP, 4).grow(Cardinal.directions, 4);
		RectSolid.fill(editor, rand, bb, BlockType.get(BlockType.AIR));
		
		for(Cardinal dir : Cardinal.directions) {
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 4).add(Cardinal.left(dir), 4).grow(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, this.theme.getPrimary().getPillar());
			for(Cardinal d : Cardinal.directions) {
				Coord pos = origin.copy().add(dir, 4).add(Cardinal.left(dir), 4).add(Cardinal.UP, 4).add(d);
				stair.setOrientation(d, true).set(editor, pos);
			}
		}
		
		Coord pos = origin.copy().add(Cardinal.UP, 6);
		for(Cardinal dir : Cardinal.directions) {
			Coord p = pos.copy().add(dir, 3);
			RectSolid.fill(editor, rand, pos, p, wall);
			p.add(Cardinal.DOWN);
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, p);
		}

		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN).grow(Cardinal.directions, 4);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
	}
	
	private void cryptWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.orthogonal(dir), 4).grow(Cardinal.DOWN).grow(Cardinal.UP, 5).grow(dir, 3);
		RectSolid.fill(editor, rand, bb, this.theme.getPrimary().getWall());
		
		Coord pos = origin.copy().add(Cardinal.UP);
		crypt(editor, rand, pos, dir);
		pos.add(Cardinal.UP, 2);
		crypt(editor, rand, pos, dir);
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			pos = origin.copy().add(o, 2).add(Cardinal.UP);
			crypt(editor, rand, pos, dir);
			pos.add(Cardinal.UP, 2);
			crypt(editor, rand, pos, dir);	
		}
	}
	
	private void crypt(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IFragment crypt = new CryptFragment(rand.nextInt(5) != 0);
		crypt.generate(editor, rand, theme, origin.copy(), dir);
	}

	@Override
	public String getName() {
		return Room.CRYPT.name();
	}

}
