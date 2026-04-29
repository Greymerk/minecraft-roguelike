package com.greymerk.roguelike.treasure.loot.items;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Firework {
	
	public static ItemStack get(RandomSource rand, int stackSize){
		return new ItemStack(Items.FIREWORK_ROCKET);
	}
}
