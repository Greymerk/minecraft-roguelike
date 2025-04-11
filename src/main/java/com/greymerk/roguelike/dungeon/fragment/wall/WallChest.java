package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public class WallChest implements IFragment {

	private Treasure type;
	
	public static void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir, Treasure type) {
		new WallChest(type).generate(editor, rand, settings, origin, dir);
	}
	
	private WallChest(Treasure type) {
		this.type = type;
	}
	
	public WallChest() {};
	
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
		
		if(this.type == null) {
			WeightedRandomizer<Treasure> types = new WeightedRandomizer<Treasure>();
			types.add(new WeightedChoice<Treasure>(Treasure.SUPPLY, 1));
			types.add(new WeightedChoice<Treasure>(Treasure.BLOCK, 1));
			types.add(new WeightedChoice<Treasure>(Treasure.WEAPON, 2));
			types.add(new WeightedChoice<Treasure>(Treasure.ARMOR, 2));
			types.add(new WeightedChoice<Treasure>(Treasure.TOOL, 3));
			types.add(new WeightedChoice<Treasure>(Treasure.ORE, 1));
			Treasure.generate(editor, rand, settings.getDifficulty(), pos, Cardinal.reverse(dir), types.get(rand));
		} else {
			Treasure.generate(editor, rand, settings.getDifficulty(), pos, Cardinal.reverse(dir), this.type);
		}
		
		
	}

}
