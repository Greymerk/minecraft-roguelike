package com.greymerk.roguelike.dungeon.fragment.alcove;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.CryptFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class TombAlcove implements IFragment{

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		ITheme theme = settings.getTheme();
		BoundingBox bb = BoundingBox.of(origin);
		bb.add(dir, 3).grow(Cardinal.UP, 4).grow(dir, 5).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.DOWN);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		
		Coord pos = origin.copy().add(dir, 3).add(Cardinal.UP);		
		CryptFragment crypt = new CryptFragment();
		crypt.setEmpty(false);
		
		crypt.generate(editor, rand, settings, pos, dir);
		
		CellSupport.generate(editor, rand, theme, origin.copy().add(dir, 6));
	}

}
