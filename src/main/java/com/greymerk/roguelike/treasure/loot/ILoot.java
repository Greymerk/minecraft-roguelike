package com.greymerk.roguelike.treasure.loot;

import com.greymerk.roguelike.util.IWeighted;

import net.minecraft.item.ItemStack;

public interface ILoot {
	
	public IWeighted<ItemStack> get(Loot type, int level);
	
}
