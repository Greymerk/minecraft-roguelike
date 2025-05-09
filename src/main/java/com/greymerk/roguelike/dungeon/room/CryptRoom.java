package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.CryptFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.util.math.random.Random;

public class CryptRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.worldPos.copy().add(direction, Cell.SIZE).freeze();
		Random rand = editor.getRandom(origin);
		roomCenter(editor, rand, origin);
		CellSupport.generate(editor, rand, theme, origin.copy());
		
		for(Cardinal dir : Cardinal.directions) {
			if(this.getExitType(dir) == ExitType.DOOR) {				
				this.entryWay(editor, rand, origin.copy(), dir);
			} else {
				if(rand.nextBoolean()) {
					this.sarcophagus(editor, rand, origin.copy().add(dir, 5), dir);
				} else {
					this.cryptWall(editor, rand, origin.copy().add(dir, 5), dir);	
				}
				
			}
			
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, 6));
		}
		
		for(Cardinal dir : Cardinal.directions) {
			if(this.getExitType(dir) == ExitType.DOOR) {
				if(this.getExitType(Cardinal.left(dir)) == ExitType.DOOR) {
					Coord pos = origin.copy().add(dir, 6).add(Cardinal.left(dir), 6);
					List<Cardinal> doors = new ArrayList<Cardinal>();
					doors.add(Cardinal.reverse(dir));
					doors.add(Cardinal.right(dir));
					this.cornerCell(editor, rand, pos, doors);
				} else {
					Coord pos = origin.copy().add(dir, 6).add(Cardinal.left(dir), 2);
					this.entrySideWall(editor, rand, pos, Cardinal.left(dir));
				}
				
				if(this.getExitType(Cardinal.right(dir)) != ExitType.DOOR) {
					Coord pos = origin.copy().add(dir, 6).add(Cardinal.right(dir), 2);
					this.entrySideWall(editor, rand, pos, Cardinal.right(dir));
				}		
			}
		}
	}
	
	private void sarcophagus(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.grow(dir, 3).grow(Cardinal.orthogonal(dir), 4).grow(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin);
		bb.add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 5).grow(dir, 4);
		RectSolid.fill(editor, rand, bb, walls, Fill.SOLID);
		
		bb = BoundingBox.of(origin);
		bb.add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 5).grow(dir, 4);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		bb = BoundingBox.of(origin);
		bb.add(dir, 4).grow(Cardinal.orthogonal(dir), 5).grow(Cardinal.UP, 5);
		RectSolid.fill(editor, rand, bb, walls, Fill.SOLID);
		
		bb = BoundingBox.of(origin);
		bb.add(dir, 3).grow(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, bb, walls);
		Coord pos = origin.copy().add(dir, 2).add(Cardinal.UP, 3);
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
		pos.add(Cardinal.UP);
		walls.set(editor, rand, pos);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			
			bb = BoundingBox.of(origin);
			bb.add(o, 5).grow(dir, 4).grow(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, walls, Fill.SOLID);
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 3).add(o, 4).grow(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, walls);
			
			bb = BoundingBox.of(origin);
			bb.add(o, 4).add(Cardinal.UP, 4).grow(dir, 2);
			RectSolid.fill(editor, rand, bb, walls);
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 3).add(o, 4).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, walls);
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 3).add(o).add(Cardinal.UP, 4).grow(o, 2);
			RectSolid.fill(editor, rand, bb, walls);
			
			stair.setOrientation(o, true).set(editor, rand, origin.copy().add(dir, 3).add(o).add(Cardinal.UP, 3));
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 3).add(o, 3).add(Cardinal.UP, 3));
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 2).add(Cardinal.UP, 3).add(o, 4));
			stair.setOrientation(dir, true).set(editor, rand, origin.copy().add(Cardinal.UP, 3).add(o, 4));
		}
		
		Fragment.generate(Fragment.SARCOPHAGUS, editor, rand, settings, origin.copy(), dir);
	}

	private void entryWay(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = this.theme.getPrimary().getStair();
		IBlockFactory wall = this.theme.getPrimary().getWall();
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(dir, 5).grow(Cardinal.orthogonal(dir), 4).grow(Cardinal.UP, 3).grow(dir, 3);
		RectSolid.fill(editor, rand, bb, Air.get());
		
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
		lc.forEach(c -> stair.set(editor, rand, c, Fill.AIR));
		
		for(Cardinal o : Cardinal.orthogonal(dir)){
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 8).add(o, 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			Coord pos = origin.copy().add(dir, 7).add(o, 2).add(Cardinal.UP, 3);
			wall.set(editor, rand, pos);
			pos.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, rand, pos);
			
			pos = origin.copy().add(dir, 8).add(o).add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
			pos.add(o).add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
			pos.add(dir).add(o);
			stair.set(editor, rand, pos);
		}
		
				
		Fragment.generate(Fragment.ARCH, editor, rand, settings, origin.copy().add(dir, 6), dir);
	}

	private void entrySideWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(dir, 2).grow(Cardinal.orthogonal(dir)).add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir));
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 3).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), Fill.SOLID);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 2).add(o, 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			Coord pos = origin.copy().add(dir, 2).add(o).add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
		}
		
		settings.getWallFragment(rand).generate(editor, rand, settings, origin, dir);
	}

	private void cornerCell(IWorldEditor editor, Random rand, Coord origin, List<Cardinal> doors) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = BoundingBox.of(origin.copy()).grow(Cardinal.directions, 2).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			bb = BoundingBox.of(origin.copy()).add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = BoundingBox.of(origin.copy()).add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy().add(dir, 2).add(Cardinal.UP, 2).add(o);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
			}

			if(!doors.contains(dir)) {
				bb = BoundingBox.of(origin.copy());
				bb.add(dir, 3).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), Fill.SOLID);
				settings.getWallFragment(rand).generate(editor, rand, settings, origin.copy(), dir);
			}


		}
	}

	private void roomCenter(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = this.theme.getPrimary().getStair();
		IBlockFactory wall = this.theme.getPrimary().getWall();
		
		BoundingBox.of(origin).add(Cardinal.UP, 7).grow(Cardinal.directions, 4).fill(editor, rand, wall, Fill.SOLID);
		BoundingBox.of(origin).grow(Cardinal.UP, 4).grow(Cardinal.directions, 4).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.directions, 3).grow(Cardinal.UP).fill(editor, rand, Air.get());
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.UP, 5).add(dir, 4).grow(Cardinal.orthogonal(dir), 4).grow(Cardinal.UP).fill(editor, rand, wall);
			Pillar.generate(editor, rand, origin.copy().add(dir, 4).add(Cardinal.left(dir), 4), theme, 4, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
		});
				
		Cardinal.directions.forEach(dir -> {
			theme.getPrimary().getWall().set(editor, rand, origin.copy().add(Cardinal.UP, 6).add(dir, 3));
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(Cardinal.UP, 5).add(dir, 3));
			BoundingBox.of(origin).add(Cardinal.UP, 6).add(dir, 2).grow(Cardinal.orthogonal(dir)).grow(Cardinal.left(dir)).fill(editor, rand, wall);
		});

		BoundingBox.of(origin.copy()).add(Cardinal.DOWN).grow(Cardinal.directions, 4).fill(editor, rand, theme.getPrimary().getFloor());
	}
	
	private void cryptWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox bb = BoundingBox.of(origin.copy());
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
		crypt.generate(editor, rand, settings, origin.copy(), dir);
	}

	@Override
	public String getName() {
		return Room.CRYPT.name();
	}

}
