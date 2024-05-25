package com.greymerk.roguelike.dungeon.fragment.alcove;

import com.greymerk.roguelike.dungeon.fragment.FragmentBase;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Button;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class SafetyCell extends FragmentBase implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 6);
		bb.grow(Cardinal.directions, 3).grow(Cardinal.UP, 3).grow(Cardinal.DOWN);
		RectHollow.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		bb = new BoundingBox(origin.copy());
		bb.add(dir, 6).add(Cardinal.UP, 4).grow(Cardinal.directions);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		bb.add(Cardinal.DOWN, 5);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		for(Cardinal d : Cardinal.directions) {
			bb = new BoundingBox(origin.copy().add(dir, 6));
			bb.add(d, 2).add(Cardinal.left(d), 2);
			bb.grow(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			Coord pos = origin.copy().add(dir, 6);
			pos.add(d).add(Cardinal.UP, 3);
			stair.setOrientation(Cardinal.reverse(d), true).set(editor, rand, pos);
			pos.add(Cardinal.left(d));
			stair.setOrientation(Cardinal.reverse(d), true).set(editor, rand, pos);
			
			for(Cardinal o : Cardinal.orthogonal(d)) {
				pos = origin.copy().add(dir, 6);
				pos.add(d, 2).add(o).add(Cardinal.UP, 2);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
			}
		}
		
		Coord pos = origin.copy().add(dir, 6).add(Cardinal.UP, 3);
		Lantern.set(editor, pos);
		
		pos = origin.copy().add(dir, 3);
		Door.generate(editor, pos, Cardinal.reverse(dir), DoorType.IRON);
		
		pos = origin.copy().add(dir, 2).add(Cardinal.UP).add(Cardinal.right(dir));
		Button.generate(editor, pos, Cardinal.reverse(dir), Button.OAK);;
		pos.add(dir, 2).add(Cardinal.left(dir), 2);
		Button.generate(editor, pos, dir, Button.OAK);;
	}
}
