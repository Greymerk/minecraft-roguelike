package com.greymerk.roguelike.dungeon.fragment.alcove;

import com.greymerk.roguelike.dungeon.fragment.FragmentBase;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class SilverfishNest extends FragmentBase implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 3);
		bb.grow(dir, 5).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		Coord pos = origin.copy();
		pos.add(Cardinal.UP).add(dir, 3);
		Air.get().set(editor, pos);
		pos.add(dir);
		Air.get().set(editor, pos);
		pos.add(dir, 2);
		for(Cardinal d : Cardinal.directions) {
			Coord p = pos.copy();
			p.add(d);
			Air.get().set(editor, p);
			p.add(Cardinal.left(d));
			Air.get().set(editor, p);
		}
		pos.add(Cardinal.UP);
		editor.set(pos, BlockType.get(BlockType.WATER_FLOWING));
		for(Cardinal d : Cardinal.directions) {
			Coord p = pos.copy().add(d).add(Cardinal.left(d));
			Air.get().set(editor, p);
		}
		pos.add(Cardinal.DOWN, 2);
		Spawner.generate(editor, rand, pos, Spawner.SILVERFISH);
	}
}
