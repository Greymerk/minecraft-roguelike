package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.util.math.random.Random;

public class PrisonRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.worldPos.copy();
		Random rand = editor.getRandom(origin);
		this.ceiling(editor, rand, origin);
		this.clear(editor, rand, origin);
		this.outerWall(editor, rand, origin);
		this.center(editor, rand, origin);
		this.floor(editor, rand, origin);
		this.doors(editor, rand, origin);
		this.cells(editor, rand, origin);
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.UP, 4).grow(Cardinal.UP).grow(Cardinal.directions, 9);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
	}

	private void cells(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			if(this.getEntrance(dir) != Entrance.DOOR) {
				Coord pos = origin.copy().add(dir, 6);
				mainCell(editor, rand, pos, dir);
				
				if(this.getEntrance(Cardinal.left(dir)) != Entrance.DOOR) {
					pos.add(Cardinal.left(dir), 6);
					List<Cardinal> doors = new ArrayList<Cardinal>();
					doors.add(Cardinal.reverse(dir));
					doors.add(Cardinal.right(dir));
					closedCell(editor, rand, pos, doors);
				} else {
					pos.add(Cardinal.left(dir), 6);
					sideCell(editor, rand, pos, dir);
				}
			} else { // is a door
				if(this.getEntrance(Cardinal.left(dir)) != Entrance.DOOR) {
					Coord pos = origin.copy();
					pos.add(dir, 6).add(Cardinal.left(dir), 6);
					sideCell(editor, rand, pos, Cardinal.left(dir));
				} else { // left is also a door
					Coord pos = origin.copy();
					pos.add(dir, 6).add(Cardinal.left(dir), 6);
					cornerCell(editor, rand, pos, Cardinal.left(dir));
				}
			}
		}
	}

	private void cornerCell(IWorldEditor editor, Random rand, Coord origin, Cardinal cellDir) {
		IStair stair = theme.getPrimary().getStair();
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.left(dir), 2);
			bb.grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			if(dir == Cardinal.reverse(cellDir) || dir == Cardinal.left(cellDir)) {
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 2).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 2);
				
				BlockWeightedRandom bars = new BlockWeightedRandom();
				bars.addBlock(IronBar.get(), 3);
				bars.addBlock(Air.get(), 1);
				
				RectSolid.fill(editor, rand, bb, bars);
			} else {
				for(Cardinal o : Cardinal.orthogonal(dir)) {
					Coord pos = origin.copy();
					pos.add(dir, 2).add(o).add(Cardinal.UP, 2);
					stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
				}
				
				settings.getWallFragment(rand).generate(editor, rand, theme, origin, dir);
			}
		}
		
		Coord pos = origin.copy();
		pos.add(Cardinal.reverse(cellDir), 2).add(Cardinal.left(cellDir), 2).add(Cardinal.UP, 3);
		for(Cardinal dir : Cardinal.directions) {
			stair.setOrientation(dir, true).set(editor, pos.copy().add(dir), true, false);
		}
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy());
	}

	private void sideCell(IWorldEditor editor, Random rand, Coord origin, Cardinal cellDir) {
		BlockWeightedRandom bars = new BlockWeightedRandom();
		bars.addBlock(IronBar.get(), 2);
		bars.addBlock(Air.get(), 1);
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.reverse(cellDir), 2).grow(Cardinal.UP, 2).grow(Cardinal.orthogonal(cellDir));
		RectSolid.fill(editor, rand, bb, bars);
		
		settings.getWallFragment(rand).generate(editor, rand, theme, origin, cellDir);
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy());
	}

	private void closedCell(IWorldEditor editor, Random rand, Coord origin, List<Cardinal> doors) {
		IStair stair = theme.getPrimary().getStair();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
		
		for(Cardinal dir : Cardinal.directions) {
			if(!doors.contains(dir)) {
				for(Cardinal o : Cardinal.orthogonal(dir)) {
					Coord pos = origin.copy();
					pos.add(dir, 2).add(o).add(Cardinal.UP, 2);
					stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
				}
				
				settings.getWallFragment(rand).generate(editor, rand, theme, origin, dir);
			}
		}
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy());
	}

	private void mainCell(IWorldEditor editor, Random rand, Coord origin, Cardinal cellDir) {
		IStair stair = theme.getPrimary().getStair();


		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}

		for(Cardinal o : Cardinal.orthogonal(cellDir)) {
			BlockWeightedRandom bars = new BlockWeightedRandom();
			bars.addBlock(IronBar.get(), 3);
			bars.addBlock(Air.get(), 1);
			
			Coord pos = origin.copy();
			pos.add(cellDir, 2).add(Cardinal.UP, 2).add(o);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(o, 3).grow(cellDir, 2).grow(Cardinal.reverse(cellDir)).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			bb = new BoundingBox(origin.copy());
			bb.add(o, 3).grow(Cardinal.orthogonal(o)).grow(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, bars);
			
			
		}

		BlockWeightedRandom bars = new BlockWeightedRandom();
		bars.addBlock(IronBar.get(), 2);
		bars.addBlock(Air.get(), 1);
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.reverse(cellDir), 2).grow(Cardinal.UP, 2).grow(Cardinal.orthogonal(cellDir));
		RectSolid.fill(editor, rand, bb, bars);
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy());
	}

	private void doors(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		
		for(Cardinal dir : this.getEntrancesFromType(Entrance.DOOR)) {
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				BoundingBox bb = new BoundingBox(origin.copy());
				bb.add(dir, 8).add(o, 2).grow(Cardinal.UP, 2);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
				
				Coord pos = origin.copy().add(dir, 8).add(o).add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
				
				pos = origin.copy().add(dir, 7).add(o, 2).add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
				
				pos.add(Cardinal.UP);
				theme.getPrimary().getWall().set(editor, rand, pos);
				
			}
			
			Fragment.generate(Fragment.ARCH, editor, rand, theme, origin.copy().add(dir, 6), dir);
		}
	}

	private void floor(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN).grow(Cardinal.directions, 9);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
	}

	private void outerWall(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 9).grow(Cardinal.orthogonal(dir), 9).grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), false, true);
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 8).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 8);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
	}

	private void center(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		for(Cardinal dir : Cardinal.directions) {
			if(this.getEntrance(dir) != Entrance.DOOR) {
				BoundingBox bb = new BoundingBox(origin.copy());
				bb.add(dir, 4).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.UP, 4);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());

			}
			BoundingBox bb = new BoundingBox(origin.copy());
			bb.add(dir, 3).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
				
		for(Cardinal dir : Cardinal.directions) {
			if(this.getEntrance(dir) != Entrance.DOOR || this.getEntrance(Cardinal.left(dir)) != Entrance.DOOR){
				BoundingBox bb = new BoundingBox(origin.copy());
				bb.add(dir, 3).add(Cardinal.left(dir), 3).grow(Cardinal.UP, 4);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
				
				Coord pos = origin.copy();
				pos.add(dir, 3).add(Cardinal.left(dir), 3).add(Cardinal.UP, 3).add(Cardinal.right(dir));
				stair.setOrientation(Cardinal.right(dir), true).set(editor, pos);
				pos.add(Cardinal.left(dir)).add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
			}
		}
		
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.directions, 8).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.UP, 4).grow(Cardinal.directions, 2);
		RectSolid.fill(editor, rand, bb, Air.get());
	}

	@Override
	public String getName() {
		return Room.PRISON.name();
	}

}
