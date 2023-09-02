package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.wall.WallShelf;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Bed;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.Furnace;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.util.math.random.Random;

public class BedRoom extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos();
		Random rand = editor.getRandom(origin);
		
		Corridor cor = new Corridor();
		for(Cardinal ent : this.entrances) {
			cor.addEntrance(ent);
		}
		cor.theme = this.theme;
		cor.worldPos = this.worldPos.copy();
		cor.addEntrance(direction);
		cor.generate(editor);
		
		Coord start = origin.copy();
		Coord end = origin.copy();
		start.add(direction, 3);
		start.add(Cardinal.left(direction), 4);
		start.add(Cardinal.DOWN);
		end.add(direction, 15);
		end.add(Cardinal.right(direction), 4);
		end.add(Cardinal.UP, 3);
		RectHollow.fill(editor, rand, start, end, this.theme.getPrimary().getWall());

		start = origin.copy();
		start.add(direction, 5);
		start.add(Cardinal.DOWN);
		end = start.copy();
		start.add(Cardinal.left(direction), 2);
		end.add(Cardinal.right(direction), 2);
		end.add(direction, 8);
		RectSolid.fill(editor, rand, start, end, this.theme.getSecondary().getFloor());

		
		for(Cardinal o : Cardinal.orthogonal(this.direction)) {
			Coord pos = origin.copy();
			pos.add(this.direction, 4);
			pos.add(o, 3);
			start = pos.copy();
			end = start.copy();
			end.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, start, end, this.theme.getPrimary().getWall());
			pos.add(this.direction, 10);
			start = pos.copy();
			end = start.copy();
			end.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, start, end, this.theme.getPrimary().getWall());
		}
		
		Coord pos = origin.copy();
		pos.add(direction, 3);
		this.theme.getPrimary().getDoor().generate(editor, pos, Cardinal.reverse(direction));
		
		this.setPillars(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.decorations(editor, rand, origin);
	}
	
	private void setPillars(IWorldEditor editor, Random rand, Coord origin) {
		List<Coord> stops = new ArrayList<Coord>();
		Coord pos = origin.copy();
		pos.add(this.direction, 5);
		stops.add(pos.copy());
		pos.add(this.direction, 4);
		stops.add(pos.copy());
		pos.add(this.direction, 4);
		stops.add(pos.copy());
		
		for(Cardinal o : Cardinal.orthogonal(this.direction)) {
			for(Coord p : stops) {
				Coord c = p.copy();
				c.add(o, 3);
				this.pillar(editor, rand, c);
			}
		}
		
		pos = origin.copy();
		pos.add(direction, 4);
		for(Cardinal o : Cardinal.orthogonal(this.direction)) {
			Coord p = pos.copy();
			p.add(o, 2);
			this.pillar(editor, rand, p);
			p.add(direction, 10);
			this.pillar(editor, rand, p);
		}
		
	}

	private void pillar(IWorldEditor editor, Random rand, Coord origin) {
		
		IBlockFactory pillar = this.theme.getSecondary().getPillar();
		IBlockFactory wall = this.theme.getSecondary().getWall();
		IStair stair = this.theme.getSecondary().getStair();
		
		Coord pos = origin.copy();
		pillar.set(editor, rand, pos);
		pos.add(Cardinal.UP);
		pillar.set(editor, rand, pos);
		pos.add(Cardinal.UP);
		wall.set(editor, rand, pos);
		for(Cardinal dir : Cardinal.directions) {
			Coord p = pos.copy();
			p.add(dir);
			stair.setOrientation(dir, true);
			stair.set(editor, p, true, false);
		}
	}
	
	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		Coord start = origin.copy();
		start.add(direction, 5);
		start.add(Cardinal.UP, 4);
		Coord end = start.copy();
		start.add(Cardinal.left(direction), 2);
		end.add(direction, 8);
		end.add(Cardinal.right(direction), 2);
		RectSolid.fill(editor, rand, start, end, this.theme.getPrimary().getWall());
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		RectSolid.fill(editor, rand, start, end, this.theme.getSecondary().getWall());
		
		Coord pos = origin.copy();
		pos.add(direction, 7);
		pos.add(Cardinal.UP, 3);
		start = pos.copy();
		end = pos.copy();
		start.add(Cardinal.NORTH);
		start.add(Cardinal.WEST);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.EAST);
		end.add(Cardinal.UP);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		start.add(direction, 4);
		end.add(direction, 4);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		for(Cardinal dir : Cardinal.directions) {
			if(dir == direction) continue;
			start = pos.copy();
			start.add(dir, 2);
			end = start.copy();
			start.add(Cardinal.left(dir));
			end.add(Cardinal.right(dir));
			IStair stair = this.theme.getPrimary().getStair();
			stair.setOrientation(Cardinal.reverse(dir), true);
			for(Coord c : new RectSolid(start, end).get()) {
				stair.set(editor, c);
			}
		}
		
		pos.add(Cardinal.UP);
		Lantern.set(editor, pos);
		pos.add(direction, 4);
		Lantern.set(editor, pos);
		pos.add(Cardinal.DOWN);
		
		for(Cardinal dir : Cardinal.directions) {
			if(dir == Cardinal.reverse(direction)) continue;
			start = pos.copy();
			start.add(dir, 2);
			end = start.copy();
			start.add(Cardinal.left(dir));
			end.add(Cardinal.right(dir));
			IStair stair = this.theme.getPrimary().getStair();
			stair.setOrientation(Cardinal.reverse(dir), true);
			for(Coord c : new RectSolid(start, end).get()) {
				stair.set(editor, c);
			}
		}
		
		pos = origin.copy();
		pos.add(direction, 9);
		pos.add(Cardinal.UP, 3);
		BlockType.get(BlockType.AIR).set(editor, pos);
		IStair stair = this.theme.getPrimary().getStair();
		for(Cardinal o : Cardinal.orthogonal(direction)) {
			Coord p = pos.copy();
			p.add(o);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, p);
		}
		
		
		

	}
	
	private void decorations(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal o : Cardinal.orthogonal(direction)) {
			Coord pos = origin.copy();
			pos.add(direction, 7);
			pos.add(o);
			new WallShelf().generate(editor, rand, theme, pos, o);
		}
		
		Coord pos = origin.copy();
		pos.add(direction, 11);
		pos.add(Cardinal.left(direction), 3);
		Bed.generate(editor, Cardinal.right(direction), pos);
		pos.add(direction);
		BlockType.get(BlockType.SHELF).set(editor, pos);

		pos = origin.copy();
		pos.add(direction, 12);
		new WallShelf().generate(editor, rand, theme, pos, direction);
		
		pos = origin.copy();
		pos.add(direction, 10);
		pos.add(Cardinal.right(direction), 3);
		Treasure.generate(editor, rand, pos, Treasure.STARTER, 0, false);
		pos.add(direction);
		BlockType.get(BlockType.CRAFTING_TABLE).set(editor, pos);
		pos.add(direction);
		Furnace.generate(editor, Cardinal.left(direction), pos);
	}
	
	@Override
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		Coord pos = new Coord(0,0,0);
		cells.add(new Cell(pos.copy(), CellState.OBSTRUCTED));
		pos.add(direction);
		cells.add(new Cell(pos.copy(), CellState.OBSTRUCTED));
		pos.add(direction);
		Cell cell = new Cell(pos.copy(), CellState.OBSTRUCTED);
		cell.addWall(direction);
		for(Cardinal o : Cardinal.orthogonal(direction)) {
			pos = new Coord(0,0,0);
			pos.add(direction);
			pos.add(o);
			{
				Cell c = new Cell(pos.copy(), CellState.OBSTRUCTED);
				c.addWall(o);
				c.addWall(Cardinal.reverse(direction));
				cells.add(c);
			}
			pos.add(direction);
			{
				Cell c = new Cell(pos.copy(), CellState.OBSTRUCTED);
				c.addWall(o);
				c.addWall(direction);
				cells.add(c);
			}
		}
		
		
		return cells;
	}

	@Override
	public String getName() {
		return Room.BEDROOM.name();
	}

	@Override
	public void determineEntrances(Floor f, Coord floorPos) {
		for(Cardinal dir : Cardinal.directions) {
			Coord fp = floorPos.copy();
			fp.add(dir);
			Cell c = f.getCell(fp);
			if(c.getState() != CellState.CORRIDOR) continue;
			if(!c.getWalls().contains(Cardinal.reverse(dir))){
				this.addEntrance(dir);
			}
		}
	}

}
