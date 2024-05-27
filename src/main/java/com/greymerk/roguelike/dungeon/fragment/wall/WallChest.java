package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class WallChest implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 2).grow(Cardinal.orthogonal(dir));
		IShape rect = bb.getShape(Shape.RECTSOLID);
		IStair stair = theme.getSecondary().getStair();
		stair.setOrientation(Cardinal.reverse(dir), true);
		for(Coord c : rect.get()) {
			stair.set(editor, rand, c);
		}
		
		WeightedRandomizer<Treasure> types = new WeightedRandomizer<Treasure>();
		types.add(new WeightedChoice<Treasure>(Treasure.SUPPLIES, 3));
		types.add(new WeightedChoice<Treasure>(Treasure.BLOCKS, 1));
		types.add(new WeightedChoice<Treasure>(Treasure.WEAPONS, 1));
		types.add(new WeightedChoice<Treasure>(Treasure.ARMOUR, 1));
		types.add(new WeightedChoice<Treasure>(Treasure.TOOLS, 1));
		
		Coord pos = origin.copy().add(dir, 2).add(Cardinal.UP);
		Treasure.generate(editor, rand, pos, Cardinal.reverse(dir), types.get(rand));
	}

}
