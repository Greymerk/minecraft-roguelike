package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.util.math.random.Random;

public class EntranceRoom  extends AbstractRoom implements IRoom{


	@Override
	public void generate(IWorldEditor editor) {		
		Coord origin = this.getWorldPos().freeze();
		Random rand = editor.getRandom(origin);
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox.of(origin).grow(Cardinal.directions, 5).grow(Cardinal.UP, 5).grow(Cardinal.DOWN)
			.getShape(Shape.RECTHOLLOW).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		
		BoundingBox.of(origin).grow(Cardinal.directions, 4).add(Cardinal.DOWN)
			.fill(editor, rand, theme.getPrimary().getFloor());
		
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.directions, 4)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox.of(origin).add(dir, 3).add(Cardinal.left(dir), 3).grow(Cardinal.UP)
				.fill(editor, rand, theme.getSecondary().getPillar());
			BoundingBox.of(origin).add(dir, 3).add(Cardinal.left(dir), 3).grow(Cardinal.UP, 3)
				.fill(editor, rand, theme.getPrimary().getWall());

			BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			BoundingBox.of(origin).add(dir, 4).add(Cardinal.left(dir), 4).grow(Cardinal.UP, 3)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				BoundingBox.of(origin.copy()).add(dir, 4).add(o, 2).grow(Cardinal.UP, 4).grow(o)
					.fill(editor, rand, theme.getPrimary().getWall());
				
				stair.setOrientation(Cardinal.reverse(o), true)
					.set(editor, rand, origin.copy().add(dir, 3).add(o, 2).add(Cardinal.UP, 2));
				
				theme.getPrimary().getWall().set(editor, rand, origin.copy().add(dir, 3).add(o, 2).add(Cardinal.UP, 3));
				
				stair.setOrientation(Cardinal.reverse(o), true)
					.set(editor, rand, origin.copy().add(dir, 3).add(o).add(Cardinal.UP, 3));
			}
		}
		
		CellSupport.generate(editor, rand, theme, origin);
	}

	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
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
