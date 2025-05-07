package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;

public class Piston {

	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean sticky) {
		MetaBlock piston = sticky ? MetaBlock.of(Blocks.STICKY_PISTON) : MetaBlock.of(Blocks.PISTON);
		piston.with(PistonBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		piston.set(editor, pos);
	}
	
}
