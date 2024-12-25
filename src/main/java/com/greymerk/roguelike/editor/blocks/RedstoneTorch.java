package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.WallRedstoneTorchBlock;

public class RedstoneTorch {

	public static void generate(IWorldEditor editor, Coord pos, boolean lit) {
		MetaBlock torch = MetaBlock.of(Blocks.REDSTONE_TORCH);
		torch.with(RedstoneTorchBlock.LIT, lit);
		torch.set(editor, pos);
	}
	
	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean lit) {
		MetaBlock torch = MetaBlock.of(Blocks.REDSTONE_WALL_TORCH);
		torch.with(WallRedstoneTorchBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		torch.with(WallRedstoneTorchBlock.LIT, lit);
		torch.set(editor, pos);
	}
	
}
