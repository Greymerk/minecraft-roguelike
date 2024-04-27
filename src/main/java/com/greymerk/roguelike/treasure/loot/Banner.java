package com.greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class Banner {

	public static ItemStack get(Random rand){
		ItemStack banner = new ItemStack(Items.BLACK_BANNER);
		return banner;
	}
		
}