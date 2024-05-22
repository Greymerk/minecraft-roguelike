package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.util.math.random.Random;

public class EntranceRoom  extends AbstractRoom implements IRoom{


	@Override
	public void generate(IWorldEditor editor) {		
		Coord origin = this.getWorldPos();
		Random rand = editor.getRandom(origin);
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.directions, 5).grow(Cardinal.UP, 5).grow(Cardinal.DOWN);
		RectHollow.fill(editor, rand, bb, theme.getPrimary().getWall(), false, true);
		
		bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.directions, 4).add(Cardinal.DOWN);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.UP, 4).grow(Cardinal.directions, 4);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		for(Cardinal dir : Cardinal.directions) {
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 3).add(Cardinal.left(dir), 3).grow(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, theme.getSecondary().getPillar());
			bb.add(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());

			bb = new BoundingBox(origin.copy());
			bb.add(dir, 4).add(Cardinal.UP, 3);
			bb.grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 4).add(o, 2);
				bb.grow(Cardinal.UP, 4).grow(o, 1);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
				
				Coord pos = origin.copy().add(dir, 3).add(o, 2).add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
				pos.add(Cardinal.UP);
				theme.getPrimary().getWall().set(editor, rand, pos);
				pos.add(Cardinal.reverse(o));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			}
		}
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin);
	}

	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = new Coord(0,0,0);
		CellManager cells = new CellManager();
		
		cells.add(new Cell(origin, CellState.OBSTRUCTED));
		
		for(Cardinal d : Cardinal.directions) {
			cells.add(Cell.of(origin.copy().add(d), CellState.POTENTIAL));
		}
		
		return cells;
	}
	
	@Override
	public String getName() {
		return Room.ENTRANCE.name();
	}

	@Override
	public void determineEntrances(Floor f, Coord floorPos) {}
}
