package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
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
		MetaBlock furnace = new MetaBlock(Blocks.FURNACE);
		
		furnace.withProperty(FurnaceBlock.LIT, lit);
		furnace.withProperty(FurnaceBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		
		furnace.set(editor, pos);
		
		//if(fuel == ItemStack.EMPTY) return;
		
		BlockEntity te = editor.getBlockEntity(pos);
		if(te == null) return;
		if(!(te instanceof FurnaceBlockEntity)) return;
		FurnaceBlockEntity teFurnace = (FurnaceBlockEntity)te;
		teFurnace.setStack(FUEL_SLOT, fuel);
	}
}
