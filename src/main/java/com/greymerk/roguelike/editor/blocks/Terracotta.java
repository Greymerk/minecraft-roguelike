package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.Color;

import net.minecraft.block.GlazedTerracottaBlock;

public class Terracotta {
	public static MetaBlock get(Color color, Cardinal dir){
		return ColorBlock.get(ColorBlock.GLAZED, color)
				.with(GlazedTerracottaBlock.FACING, Cardinal.facing(dir));
	}
}
