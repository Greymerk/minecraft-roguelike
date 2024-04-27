package com.greymerk.roguelike.treasure.loot;

import com.greymerk.roguelike.util.IWeighted;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class WeightedRandomLoot implements Comparable<WeightedRandomLoot>, IWeighted<ItemStack>{
	
	private ItemConvertible item;
	private int min;
	private int max;
	private int enchLevel;
	private int weight;
	
	
	public WeightedRandomLoot(Block block, int minStackSize, int maxStackSize, int weight){
		this.item = (ItemConvertible)block;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
		this.enchLevel = 0;
	}
	
	public WeightedRandomLoot(Item item, int minStackSize, int maxStackSize, int weight){
		this(item, minStackSize, maxStackSize, weight, 0);
	}
	
	public WeightedRandomLoot(Item item, int minStackSize, int maxStackSize, int weight, int ench){
		this.item = (ItemConvertible)item;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
		this.enchLevel = ench;
	}

	public WeightedRandomLoot(Item item, int weight){
		this(item, 1, 1, weight, 0);
	}

	private int getStackSize(Random rand){
		if (max == 1) return 1;
		return rand.nextInt(max - min) + min;
	}
	
	@Override
	public int getWeight(){
		return this.weight;
	}

	@Override
	public ItemStack get(Random rand) {
		ItemStack item = null;
		if(this.item != null) item = new ItemStack((ItemConvertible)this.item, this.getStackSize(rand));
		try{
			if(this.enchLevel > 0 && this.enchLevel <= 30) Enchant.enchantItem(rand, item, this.enchLevel);
		} catch (NullPointerException e){
			// ignore
		}
		return item;
	}

	@Override
	public int compareTo(WeightedRandomLoot other) {
		if (this.weight > other.weight) return -1;
		if (this.weight < other.weight) return 1;		
		return 0;
	}
}