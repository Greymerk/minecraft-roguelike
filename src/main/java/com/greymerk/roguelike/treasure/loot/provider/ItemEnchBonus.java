package com.greymerk.roguelike.treasure.loot.provider;

import net.minecraft.util.math.random.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemEnchBonus extends ItemBase{

	public ItemEnchBonus(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(rand.nextBoolean()) return new ItemStack(Items.EXPERIENCE_BOTTLE, 1 + rand.nextInt(5));
		return new ItemStack(Items.ENDER_PEARL, 1 + rand.nextInt(2));
	}

	
}
