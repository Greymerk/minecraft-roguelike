package com.greymerk.roguelike.dungeon.fragment.alcove;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Button;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.util.math.random.Random;

public class SafetyCell implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		cell(editor, rand, origin.copy().add(dir, Cell.SIZE).freeze(), dir, theme);
		
		Door.generate(editor, origin.copy().add(dir, 3), Cardinal.reverse(dir), DoorType.IRON);
		Button.generate(editor, origin.copy().add(dir, 2).add(Cardinal.UP).add(Cardinal.right(dir)), Cardinal.reverse(dir), Button.OAK);
		Button.generate(editor, origin.copy().add(dir, 4).add(Cardinal.UP).add(Cardinal.left(dir)), dir, Button.OAK);
	}

	private void cell(IWorldEditor editor, Random rand, Coord origin, Cardinal direction, ITheme theme) {

		BoundingBox.of(origin).grow(Cardinal.directions, 3).grow(Cardinal.UP, 3).grow(Cardinal.DOWN)
			.getShape(Shape.RECTHOLLOW).fill(editor, rand, theme.getPrimary().getWall());
	
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.directions).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions).fill(editor, rand, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			Pillar.generate(editor, rand, origin.copy().add(dir, 2).add(Cardinal.left(dir), 2), theme, 2, 
					List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
		}
		
		BoundingBox.of(origin).add(Cardinal.UP, 3).grow(Cardinal.directions).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.directions, 2).fill(editor, rand, theme.getPrimary().getWall());
		
		Fragment.generate(Fragment.WALL_FOOD_BARREL, editor, rand, theme, origin, 
				RandHelper.pickFrom(List.of(direction, Cardinal.left(direction), Cardinal.right(direction)), rand));
		
		Lantern.set(editor, origin.copy().add(Cardinal.UP, 3));
		CellSupport.generate(editor, rand, theme, origin);
	}
}
