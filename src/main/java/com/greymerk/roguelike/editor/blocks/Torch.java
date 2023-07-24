package com.greymerk.roguelike.editor.blocks;


import java.util.Arrays;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;

public enum Torch {

	WOODEN, SOUL;
	
	public static void generate(IWorldEditor editor, Torch type, Cardinal dir, Coord pos){
		
		BlockState torch;
		boolean wall = Arrays.asList(Cardinal.directions).contains(dir);
		
		switch(type){
		case WOODEN: torch = wall 
				? Blocks.WALL_TORCH.getDefaultState()
				: Blocks.TORCH.getDefaultState(); break;
		case SOUL: torch = wall
				? Blocks.SOUL_WALL_TORCH.getDefaultState()
				: Blocks.SOUL_TORCH.getDefaultState(); break;
		default: torch = Blocks.TORCH.getDefaultState(); break;
		}		
		
		if(wall) {
			torch.with(WallTorchBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		}
		
		editor.set(pos, new MetaBlock(torch));
	}	
}
