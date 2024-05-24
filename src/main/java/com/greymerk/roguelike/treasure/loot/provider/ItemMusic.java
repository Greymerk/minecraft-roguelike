package com.greymerk.roguelike.treasure.loot.provider;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.treasure.loot.items.MusicDisk;

import net.minecraft.item.ItemStack;

public class ItemMusic extends ItemBase{

	public ItemMusic(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return MusicDisk.getRandomRecord(rand);
	}
	
	

}
