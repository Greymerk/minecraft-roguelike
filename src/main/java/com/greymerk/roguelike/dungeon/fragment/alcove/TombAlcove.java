package com.greymerk.roguelike.dungeon.fragment.alcove;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.FragmentBase;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CryptFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class TombAlcove extends FragmentBase implements IFragment{

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		BoundingBox bb = BoundingBox.of(origin);
		bb.add(dir, 3).grow(Cardinal.UP, 4).grow(dir, 5).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.DOWN);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		Coord pos = origin.copy().add(dir, 3).add(Cardinal.UP);		
		CryptFragment crypt = new CryptFragment();
		crypt.setEmpty(false);
		
		crypt.generate(editor, rand, theme, pos, dir);
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy().add(dir, 6));
	}

}
