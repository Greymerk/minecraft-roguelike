package com.greymerk.roguelike.treasure.chest;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public interface ITreasureChest {
	
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, ChestType type) throws ChestPlacementException;
	
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, Cardinal dir, ChestType type) throws ChestPlacementException;
	
	public boolean setSlot(int slot, ItemStack item);
	
	public boolean setRandomEmptySlot(ItemStack item);
	
	public void setLootTable(Identifier table);
	
	public boolean isEmptySlot(int slot);
	
	public Treasure getType();
	
	public int getSize();
	
	public int getLevel();

}