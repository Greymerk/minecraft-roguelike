package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;

public class IronBars {

	private static Block IRON_BARS = Blocks.IRON_BARS;
	
	public static void set(IWorldEditor editor, Coord pos, Cardinal dir) {
		get(dir).set(editor, pos);
	}

	public static MetaBlock get(Cardinal dir) {
		MetaBlock bars = new MetaBlock(IRON_BARS);
		switch(dir) {
		case EAST:
		case WEST:
			bars.withProperty(PaneBlock.NORTH, true);
			bars.withProperty(PaneBlock.SOUTH, true);
			break;
		case NORTH:
		case SOUTH:
			bars.withProperty(PaneBlock.EAST, true);
			bars.withProperty(PaneBlock.WEST, true);
			break;
		default: break;
		}
		
		return bars;
	}
}
