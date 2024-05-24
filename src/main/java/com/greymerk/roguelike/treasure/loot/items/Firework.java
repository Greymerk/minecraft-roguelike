package com.greymerk.roguelike.treasure.loot.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class Firework {
	
	public static ItemStack get(Random rand, int stackSize){
		return new ItemStack(Items.FIREWORK_ROCKET);
	}
}
