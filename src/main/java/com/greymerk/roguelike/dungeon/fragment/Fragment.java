package com.greymerk.roguelike.dungeon.fragment;

import com.greymerk.roguelike.dungeon.fragment.parts.CellSupportBeamFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CryptFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;

public enum Fragment {

	CRYPT, CELL_SUPPORT;
	
	public static void generate(Fragment type, IWorldEditor editor, Random rand, ITheme theme, Coord pos) {
		generate(type, editor, rand, theme, pos, Cardinal.DOWN);
	}
		
	
	public static void generate(Fragment type, IWorldEditor editor, Random rand, ITheme theme, Coord pos, Cardinal dir) {
		IFragment fragment = fromType(type);
		fragment.generate(editor, rand, theme, pos, dir);
	}
	
	public static IFragment fromType(Fragment type) {
		switch(type) {
		case CELL_SUPPORT: return new CellSupportBeamFragment();
		case CRYPT: return new CryptFragment();
		default:
			return null;
		}
	}
	
}
