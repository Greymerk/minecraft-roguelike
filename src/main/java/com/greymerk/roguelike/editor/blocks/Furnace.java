package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.item.ItemStack;

public class Furnace {

	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, dir, pos, false, ItemStack.EMPTY);
	}
	
	public static void generate(IWorldEditor editor, boolean lit, Cardinal dir, Coord pos){
		generate(editor, dir, pos, lit, ItemStack.EMPTY);
	}
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos, boolean lit, ItemStack fuel){
		editor.setBlockEntity(pos, 
			MetaBlock.of(Blocks.FURNACE)
				.with(FurnaceBlock.LIT, lit)
				.with(FurnaceBlock.FACING, Cardinal.facing(Cardinal.reverse(dir))), 
			FurnaceBlockEntity.class).ifPresent(furnace -> {
				if(fuel.equals(ItemStack.EMPTY)) return;
				furnace.setStack(FUEL_SLOT, fuel);	
		});
	}
}
