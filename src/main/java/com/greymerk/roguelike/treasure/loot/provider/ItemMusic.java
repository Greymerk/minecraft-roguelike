package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.items.MusicDisk;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class ItemMusic extends ItemBase{

	public ItemMusic(int weight, Difficulty diff) {
		super(weight, diff);
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {
		return MusicDisk.getRandomRecord(rand);
	}
	
	

}
