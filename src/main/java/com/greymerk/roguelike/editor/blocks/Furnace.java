package com.greymerk.roguelike.editor.blocks;

import java.util.Optional;

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
		MetaBlock furnace = MetaBlock.of(Blocks.FURNACE);
		
		furnace.with(FurnaceBlock.LIT, lit);
		furnace.with(FurnaceBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)));
		
		furnace.set(editor, pos);
		
		//if(fuel == ItemStack.EMPTY) return;
		
		Optional<BlockEntity> obe = editor.getBlockEntity(pos);
		if(obe.isEmpty()) return;
		BlockEntity te = obe.get();
		if(!(te instanceof FurnaceBlockEntity)) return;
		FurnaceBlockEntity teFurnace = (FurnaceBlockEntity)te;
		teFurnace.setStack(FUEL_SLOT, fuel);
	}
}
