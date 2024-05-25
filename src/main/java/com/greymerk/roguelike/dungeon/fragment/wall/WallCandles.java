package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.FragmentBase;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class WallCandles extends FragmentBase implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 2).grow(Cardinal.orthogonal(dir));
		IShape rect = bb.getShape(Shape.RECTSOLID);
		IStair stair = theme.getSecondary().getStair();
		stair.setOrientation(Cardinal.reverse(dir), true);
		for(Coord c : rect.get()) {
			stair.set(editor, c);
			Coord pos = c.copy().add(Cardinal.UP);
			if(rand.nextBoolean()) {
				Candle.generate(editor, rand, pos);
			}
		}
	}

}
