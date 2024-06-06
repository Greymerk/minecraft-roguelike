package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.EnderChestBlock;

public class EnderChest {
	public static void set(IWorldEditor editor, Cardinal dir, Coord pos){
		MetaBlock.of(Blocks.ENDER_CHEST)
			.with(EnderChestBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)))
			.set(editor, pos);
	}
}
