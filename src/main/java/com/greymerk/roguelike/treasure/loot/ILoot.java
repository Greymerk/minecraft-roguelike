package com.greymerk.roguelike.treasure.loot;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.util.IWeighted;

import net.minecraft.item.ItemStack;

public interface ILoot {
	
	public IWeighted<ItemStack> get(Loot type, Difficulty diff);
	
}
