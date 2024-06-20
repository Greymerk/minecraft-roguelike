package com.greymerk.roguelike.treasure.chest;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface ITreasureChest {
	
	public boolean setSlot(int slot, ItemStack item);
	
	public boolean setRandomEmptySlot(ItemStack item);
	
	public void setLootTable(Identifier table);
	
	public boolean isEmptySlot(int slot);
	
	public Treasure getType();
	
	public int getSize();
	
	public Difficulty getLevel();

}