package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class WallSpawner implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		ITheme theme = settings.getTheme();
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(dir, 2).grow(Cardinal.orthogonal(dir));
		IShape rect = bb.getShape(Shape.RECTSOLID);
		IStair stair = theme.getSecondary().getStair();
		stair.setOrientation(Cardinal.reverse(dir), true);
		for(Coord c : rect.get()) {
			stair.set(editor, rand, c);
		}
		
		Coord pos = origin.copy().add(dir, 2).add(Cardinal.UP);
		Spawner.generate(editor, rand, settings.getDifficulty(), pos);
	}

}
