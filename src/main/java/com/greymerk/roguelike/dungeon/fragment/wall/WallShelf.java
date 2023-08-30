package com.greymerk.roguelike.dungeon.fragment.wall;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.FlowerPot;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.Skull;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class WallShelf implements IFragment {
	
	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		Coord start = new Coord(origin);
		start.add(dir, 2);
		Coord end = new Coord(start);
		start.add(Cardinal.left(dir));
		end.add(Cardinal.right(dir));
		RectSolid rect = new RectSolid(start, end);
		IStair stair = theme.getPrimary().getStair();
		stair.setOrientation(Cardinal.reverse(dir), true);
		for(Coord c : rect.get()) {
			stair.set(editor, c);
			Coord pos = new Coord(c);
			pos.add(Cardinal.UP);
			if(rand.nextInt(10) == 0) {
				FlowerPot.generate(editor, rand, pos);
			} else if(rand.nextInt(10) == 0) {
				Skull.set(editor, rand, pos, Cardinal.reverse(dir), Skull.SKELETON);
			}
		}
		Coord pos = new Coord(origin);
		pos.add(dir, 2);
		pos.add(Cardinal.UP, 2);
		Lantern.set(editor, pos);
	}
}
