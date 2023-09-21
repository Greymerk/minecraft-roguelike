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

import net.minecraft.util.math.random.Random;

public class CryptRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.worldPos.copy();
		Random rand = editor.getRandom(origin);
		roomCenter(editor, rand, origin);
		for(Cardinal dir : Cardinal.directions) {
			if(this.getEntrance(dir) == Entrance.DOOR) {
				Coord start = origin.copy();
				start.add(dir, 5);
				Coord end = start.copy();
				start.add(Cardinal.left(dir), 4);
				start.add(Cardinal.DOWN);
				end.add(Cardinal.right(dir), 4);
				end.add(dir, 4);
				end.add(Cardinal.UP, 4);
				RectHollow.fill(editor, rand, start, end, theme.getPrimary().getWall());
			}
		}
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy());
		
		for(Cardinal dir : Cardinal.directions) {
			if(this.getEntrance(dir) == Entrance.DOOR) {				
				this.entryWay(editor, rand, origin, dir);
			} else {
				Coord pos = origin.copy();
				pos.add(dir, 5);
				this.cryptWall(editor, rand, pos, dir);
			}
			
			Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy().add(dir, 6));
		}
	}
	
	private void entryWay(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = this.theme.getPrimary().getStair();
		IBlockFactory wall = this.theme.getPrimary().getWall();
		
		Coord start;
		Coord end;
		
		start = origin.copy();
		start.add(dir, 5);
		end = start.copy();
		start.add(Cardinal.left(dir), 3);
		end.add(Cardinal.right(dir), 3);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		start = origin.copy();
		start.add(dir, 8);
		start.add(Cardinal.UP, 3);
		end = start.copy();
		start.add(Cardinal.left(dir), 3);
		end.add(Cardinal.right(dir), 3);
		RectSolid.fill(editor, rand, start, end, wall);
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		List<Coord> lc = new RectSolid(start, end).get();
		stair.setOrientation(Cardinal.reverse(dir), true);
		lc.forEach(c -> stair.set(editor, c, true, false));
		
		for(Cardinal o : Cardinal.orthogonal(dir)){
			start = origin.copy();
			start.add(dir, 8);
			start.add(o, 2);
			end = start.copy();
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			end.add(Cardinal.reverse(dir));
			wall.set(editor, rand, end);
			end.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, end);
			
			Coord pos = origin.copy();
			pos.add(dir, 8);
			pos.add(o);
			pos.add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos);
			pos.add(o);
			pos.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, pos);
			pos.add(dir);
			pos.add(o);
			stair.set(editor, pos);
		}
		
		if(this.getEntrance(Cardinal.left(dir)) == Entrance.DOOR) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			pos.add(Cardinal.left(dir), 6);
			List<Cardinal> doors = new ArrayList<Cardinal>();
			doors.add(Cardinal.reverse(dir));
			doors.add(Cardinal.right(dir));
			this.cornerCell(editor, rand, pos, doors);
		} else {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			pos.add(Cardinal.left(dir), 2);
			this.entrySideWall(editor, rand, pos, Cardinal.left(dir));
		}
		
		if(this.getEntrance(Cardinal.right(dir)) != Entrance.DOOR) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			pos.add(Cardinal.right(dir), 2);
			this.entrySideWall(editor, rand, pos, Cardinal.right(dir));
		}
		
		Coord pos = origin.copy();
		pos.add(dir, 6);
		Fragment.generate(Fragment.ARCH, editor, rand, theme, pos, dir);
	}

	private void entrySideWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		Coord start = origin.copy();
		start.add(dir, 2);
		Coord end = start.copy();
		start.add(Cardinal.left(dir));
		end.add(Cardinal.right(dir));
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		start = origin.copy();
		start.add(dir, 2);
		start.add(Cardinal.UP, 3);
		end = start.copy();
		start.add(Cardinal.left(dir));
		end.add(Cardinal.right(dir));
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
		
		start = origin.copy();
		start.add(dir, 3);
		end = start.copy();
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), false, true);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			start = origin.copy();
			start.add(dir, 2);
			start.add(o, 2);
			end = start.copy();
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			
			Coord pos = origin.copy();
			pos.add(dir, 2);
			pos.add(o);
			pos.add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos);
		}
		
		IFragment shelf = settings.getWallFragment(rand);
		shelf.generate(editor, rand, theme, origin, dir);
		
		
	}

	private void cornerCell(IWorldEditor editor, Random rand, Coord origin, List<Cardinal> doors) {
		IStair stair = theme.getPrimary().getStair();
		
		Coord start = origin.copy();
		Coord end = origin.copy();
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		start = origin.copy();
		start.add(Cardinal.DOWN);
		end = start.copy();
		start.add(Cardinal.NORTH);
		start.add(Cardinal.WEST);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.EAST);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			start = origin.copy();
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			end = start.copy();
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			
			start = origin.copy();
			start.add(dir, 2);
			start.add(Cardinal.UP, 3);
			end = start.copy();
			start.add(Cardinal.left(dir));
			end.add(Cardinal.right(dir));
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				
				Coord pos = origin.copy();
				pos.add(dir, 2);
				pos.add(Cardinal.UP, 2);
				pos.add(o);
				stair.setOrientation(Cardinal.reverse(o), true);
				stair.set(editor, pos);
			}

			if(!doors.contains(dir)) {
				start = origin.copy();
				start.add(dir, 3);
				end = start.copy();
				start.add(Cardinal.left(dir), 2);
				end.add(Cardinal.right(dir), 2);
				end.add(Cardinal.UP, 3);
				RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), false, true);
				IFragment shelf = settings.getWallFragment(rand);
				shelf.generate(editor, rand, theme, origin, dir);
			}


		}
	}

	private void roomCenter(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = this.theme.getPrimary().getStair();
		IBlockFactory wall = this.theme.getPrimary().getWall();
		
		Coord start = origin.copy();
		Coord end = origin.copy();
		start.add(Cardinal.NORTH, 4);
		start.add(Cardinal.WEST, 4);
		start.add(Cardinal.UP, 4);
		end.add(Cardinal.SOUTH, 4);
		end.add(Cardinal.EAST, 4);
		end.add(Cardinal.UP, 7);
		RectHollow.fill(editor, rand, start, end, wall);
		
		start = origin.copy();
		end = origin.copy();
		start.add(Cardinal.NORTH, 4);
		start.add(Cardinal.WEST, 4);
		end.add(Cardinal.SOUTH, 4);
		end.add(Cardinal.EAST, 4);
		end.add(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		for(Cardinal dir : Cardinal.directions) {
			start = origin.copy();
			start.add(dir, 4);
			start.add(Cardinal.left(dir), 4);
			end = start.copy();
			end.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, start, end, this.theme.getPrimary().getPillar());
			for(Cardinal d : Cardinal.directions) {
				Coord pos = end.copy();
				pos.add(d);
				stair.setOrientation(d, true);
				stair.set(editor, pos);
			}
		}
		
		Coord pos = origin.copy();
		pos.add(Cardinal.UP, 6);
		for(Cardinal dir : Cardinal.directions) {
			Coord p = pos.copy();
			p.add(dir, 3);
			RectSolid.fill(editor, rand, pos, p, wall);
			p.add(Cardinal.DOWN);
			stair.setOrientation(Cardinal.reverse(dir), true);
			stair.set(editor, p);
		}

		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN).grow(Cardinal.directions, 4);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
	}
	
	private void cryptWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.orthogonal(dir), 4);
		bb.grow(Cardinal.DOWN).grow(Cardinal.UP, 5);
		bb.grow(dir, 3);
		RectSolid.fill(editor, rand, bb, this.theme.getPrimary().getWall());
		
		Coord pos = origin.copy();
		pos.add(Cardinal.UP);
		crypt(editor, rand, pos, dir);
		pos.add(Cardinal.UP, 2);
		crypt(editor, rand, pos, dir);
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			pos = origin.copy();
			pos.add(o, 2);
			pos.add(Cardinal.UP);
			crypt(editor, rand, pos, dir);
			pos.add(Cardinal.UP, 2);
			crypt(editor, rand, pos, dir);	
		}
	}
	
	private void crypt(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IFragment crypt = new CryptFragment(rand.nextInt(3) != 0);
		crypt.generate(editor, rand, theme, origin.copy(), dir);
	}

	@Override
	public String getName() {
		return Room.CRYPT.name();
	}

}
