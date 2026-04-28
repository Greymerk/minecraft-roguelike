package com.greymerk.roguelike.editor.blocks;


import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public enum Torch {

	WOODEN, SOUL;
	
	public static void generate(IWorldEditor editor, Torch type, Cardinal dir, Coord pos){
		
		BlockState torch;
		boolean wall = Cardinal.directions.contains(dir);
		
		switch(type){
		case WOODEN: torch = wall 
				? Blocks.WALL_TORCH.defaultBlockState()
				: Blocks.TORCH.defaultBlockState(); break;
		case SOUL: torch = wall
				? Blocks.SOUL_WALL_TORCH.defaultBlockState()
				: Blocks.SOUL_TORCH.defaultBlockState(); break;
		default: torch = Blocks.TORCH.defaultBlockState(); break;
		}		
		
		if(wall) {
			torch.setValue(WallTorchBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		}
		
		MetaBlock.of(Blocks.TORCH).set(editor, pos, Fill.SUPPORTED);
	}	
}
