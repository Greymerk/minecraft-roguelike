package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.RedstoneWallTorchBlock;

public class RedstoneTorch {

	public static void generate(IWorldEditor editor, Coord pos, boolean lit) {
		MetaBlock torch = MetaBlock.of(Blocks.REDSTONE_TORCH);
		torch.with(RedstoneTorchBlock.LIT, lit);
		torch.set(editor, pos);
	}
	
	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean lit) {
		MetaBlock torch = MetaBlock.of(Blocks.REDSTONE_WALL_TORCH);
		torch.with(RedstoneWallTorchBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		torch.with(RedstoneWallTorchBlock.LIT, lit);
		torch.set(editor, pos);
	}
	
}
