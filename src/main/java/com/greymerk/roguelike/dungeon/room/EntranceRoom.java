package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;

public class EntranceRoom  extends AbstractRoom implements IRoom{

	public EntranceRoom(ITheme theme, IBounded box, Coord pos) {
		super(theme, box, pos);
	}

	public EntranceRoom(ITheme theme, Coord fp, Coord wp) {
		super(theme, fp, wp);
	}

	@Override
	public void generate(IWorldEditor editor) {		
		Coord pos = this.getWorldPos();
		Random rand = editor.getRandom(pos);
		IStair stair = theme.getPrimary().getStair();
		
		Coord s = new Coord(pos);
		s.add(new Coord(5, 5, 5));
		Coord e = new Coord(pos);
		e.add(new Coord(-5, -1, -5));
		RectHollow.fill(editor, rand, s, e, theme.getPrimary().getWall(), false, true);
		
		s = new Coord(-5, -1, -5);
		s.add(pos);
		e = new Coord(5, -1, 5);
		e.add(pos);
		RectSolid.fill(editor, rand, s, e, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			s = new Coord(pos);
			s.add(dir, 4);
			s.add(Cardinal.left(dir), 4);
			e = new Coord(s);
			e.add(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, s, e, theme.getPrimary().getPillar());
			s = new Coord(e);
			s.add(Cardinal.DOWN);
			s.add(Cardinal.right(dir), 7);
			RectSolid.fill(editor, rand, s, e, theme.getPrimary().getWall());
			
			s = new Coord(pos);
			s.add(dir, 3);
			s.add(Cardinal.left(dir), 3);
			s.add(Cardinal.UP, 4);
			e = new Coord(s);
			e.add(Cardinal.right(dir), 5);
			RectSolid.fill(editor, rand, s, e, theme.getPrimary().getWall());
			
			for(Cardinal orth : Cardinal.orthogonal(dir)) {
				Coord p = new Coord(pos);
				p.add(dir, 4);
				p.add(Cardinal.UP, 2);
				p.add(orth);
				stair.setOrientation(Cardinal.reverse(orth), true).set(editor, p);
				
				p = new Coord(pos);
				p.add(dir, 3);
				p.add(orth, 2);
				p.add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, p);
				p.add(Cardinal.UP);
				theme.getPrimary().getWall().set(editor, rand, p);
				p.add(Cardinal.reverse(orth));
				stair.setOrientation(Cardinal.reverse(orth), true).set(editor, pos);
				
				s = new Coord(pos);
				s.add(dir, 4);
				s.add(orth, 2);
				e = new Coord(s);
				e.add(Cardinal.UP, 3);
				RectSolid.fill(editor, rand, s, e, theme.getPrimary().getPillar());
			}
		}
		
		for(Cardinal dir : Cardinal.directions) {
			s = new Coord(pos);
			s.add(Cardinal.left(dir));
			e = new Coord(pos);
			e.add(Cardinal.right(dir));
			e.add(Cardinal.UP, 2);
			e.add(dir, 9);
			
		}
	}

	@Override
	public List<Cell> getCells() {
		
		List<Cell> cells = new ArrayList<Cell>();
		
		cells.add(new Cell(new Coord(0,0,0), CellState.OBSTRUCTED));
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(0,0,0);
			pos.add(dir);
			cells.add(new Cell(pos, CellState.POTENTIAL));
		}
		
		return cells;
	}
	
	@Override
	public String getName() {
		return Room.ENTRANCE.name();
	}
}
