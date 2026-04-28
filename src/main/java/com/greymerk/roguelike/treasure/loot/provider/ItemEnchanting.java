package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemEnchanting extends ItemBase{

	public ItemEnchanting(int weight, Difficulty diff) {
		super(weight, diff);
	}

	@Override
	public ItemStack getLootItem(RandomSource rand, Difficulty diff) {
		if(rand.nextBoolean()) return new ItemStack(Items.EXPERIENCE_BOTTLE, 1 + rand.nextInt(5));
		return new ItemStack(Items.ENDER_PEARL, 1 + rand.nextInt(2));
	}

	
}
