package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBars;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
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
			Coord start = origin.copy();
			start.add(Cardinal.UP, 5);
			Coord end = start.copy();
			start.add(dir, 6);
			end.add(dir, 7);
			start.add(Cardinal.left(dir), 5);
			end.add(Cardinal.right(dir), 7);
			RectSolid.fill(editor, rand, start, end, wall);
			
			start = origin.copy();
			start.add(Cardinal.UP, 5);
			start.add(dir, 2);
			end = start.copy();
			start.add(Cardinal.left(dir), 5);
			end.add(Cardinal.right(dir), 5);
			RectSolid.fill(editor, rand, start, end, wall, true, false);
		}
	}

	private void water(IWorldEditor editor, Random rand, Coord origin) {
		Coord start = origin.copy();
		start.add(Cardinal.DOWN, 2);
		Coord end = start.copy();
		start.add(Cardinal.NORTH, 9);
		start.add(Cardinal.WEST, 9);
		end.add(Cardinal.SOUTH, 9);
		end.add(Cardinal.EAST, 9);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
		
		start = origin.copy();
		start.add(Cardinal.DOWN);
		end = start.copy();
		start.add(Cardinal.NORTH, 7);
		start.add(Cardinal.WEST, 7);
		end.add(Cardinal.SOUTH, 7);
		end.add(Cardinal.EAST, 7);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getLiquid(), true, false);
	}

	private void bridges(IWorldEditor editor, Random rand, Coord origin) {
		
		IBlockFactory floor = theme.getPrimary().getFloor();
		IBlockFactory wall = theme.getPrimary().getWall();
		Coord start;
		Coord end;
		
		start = origin.copy();
		start.add(Cardinal.DOWN);
		end = start.copy();
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		RectSolid.fill(editor, rand, start, end, wall);
		
		start = origin.copy();
		start.add(Cardinal.DOWN);
		end = start.copy();
		start.add(Cardinal.NORTH);
		start.add(Cardinal.WEST);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.EAST);
		RectSolid.fill(editor, rand, start, end, floor);
		
		for(Cardinal dir : this.entrances) {
			start = origin.copy();
			start.add(dir, 3);
			start.add(Cardinal.DOWN);
			end = start.copy();
			start.add(Cardinal.left(dir), 2);
			end.add(Cardinal.right(dir), 2);
			end.add(dir, 4);
			RectSolid.fill(editor, rand, start, end, wall);
			start.add(Cardinal.right(dir));
			end.add(Cardinal.left(dir));
			RectSolid.fill(editor, rand, start, end, floor);	
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 2);
			pos.add(Cardinal.left(dir), 2);
			wall.set(editor, rand, pos);
			
			if(!this.entrances.contains(dir)) {
				start = origin.copy();
				start.add(dir, 2);
				end = start.copy();
				start.add(Cardinal.left(dir));
				end.add(Cardinal.right(dir));
				RectSolid.fill(editor, rand, start, end, IronBars.get(dir));
			} else {
				for(Cardinal o : Cardinal.orthogonal(dir)) {
					start = origin.copy();
					start.add(dir, 3);
					start.add(o, 2);
					end = start.copy();
					end.add(dir, 4);
					RectSolid.fill(editor, rand, start, end, IronBars.get(o));
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
			Coord start = origin.copy();
			start.add(Cardinal.DOWN);
			start.add(dir, 8);
			Coord end = start.copy();
			start.add(Cardinal.left(dir), 8);
			end.add(Cardinal.right(dir), 8);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
			
			start.add(Cardinal.UP, 4);
			end.add(Cardinal.UP, 6);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
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
			Coord start = origin.copy();
			start.add(dir, 8);
			start.add(Cardinal.left(dir), 8);
			Coord end = start.copy();
			end.add(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			for(Cardinal d : Cardinal.directions) {
				Coord pos = end.copy();
				pos.add(d);
				stair.setOrientation(d, true);
				stair.set(editor, pos, true, false);
			}
			
			start = end.copy();
			start.add(Cardinal.reverse(dir));
			start.add(Cardinal.right(dir));
			start.add(Cardinal.UP);
			end = start.copy();
			end.add(Cardinal.UP);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
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
			Coord start = origin.copy();
			start.add(dir, 2);
			start.add(o);
			Coord end = start.copy();
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			start = end.copy();
			start.add(Cardinal.reverse(dir));
			end = start.copy();
			end.add(Cardinal.UP);
			RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());
			
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
		Coord start = origin.copy();
		start.add(Cardinal.DOWN);
		start.add(Cardinal.NORTH, 8);
		start.add(Cardinal.WEST, 8);
		Coord end = origin.copy();
		end.add(Cardinal.UP, 5);
		end.add(Cardinal.SOUTH, 8);
		end.add(Cardinal.EAST, 8);
		RectSolid.fill(editor, rand, start, end, Air.get(), false, true);
	}

	@Override
	public String getName() {
		return Room.CISTERN.name();
	}

}
