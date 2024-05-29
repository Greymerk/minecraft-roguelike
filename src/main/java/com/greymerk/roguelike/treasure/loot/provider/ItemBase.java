package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.util.IWeighted;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public abstract class ItemBase implements IWeighted<ItemStack> {

	private int weight;
	protected Difficulty diff;
	
	public ItemBase(int weight){
		this.weight = weight;
		this.diff = Difficulty.EASIEST;
	}
	
	public ItemBase(int weight, Difficulty diff){
		this.weight = weight;
		this.diff = diff;
	}
	
	public abstract ItemStack getLootItem(Random rand, Difficulty diff);

	@Override
	public int getWeight() {
		return weight;
	}
	
	@Override
	public ItemStack get(Random rand) {
		return getLootItem(rand, diff);
	}
}
