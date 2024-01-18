package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class ItemBlock extends ItemBase{
	
	private WeightedRandomizer<ItemStack> loot;
	
	public ItemBlock(int weight, int level) {
		super(weight, level);
		this.loot = new WeightedRandomizer<ItemStack>();
		this.loot.add(new WeightedRandomLoot(Blocks.COBBLESTONE, 8, 32, 20));
		this.loot.add(new WeightedRandomLoot(Blocks.STONE, 8, 32, 10));
		this.loot.add(new WeightedRandomLoot(Blocks.DIORITE, 8, 32, 5));
		this.loot.add(new WeightedRandomLoot(Blocks.ANDESITE, 8, 32, 5));
		this.loot.add(new WeightedRandomLoot(Blocks.GRANITE, 8, 32, 5));
		this.loot.add(new WeightedRandomLoot(Blocks.OAK_LOG, 8, 32, 10));
		this.loot.add(new WeightedRandomLoot(Blocks.GLASS, 8, 32, 5));
		this.loot.add(new WeightedRandomLoot(Blocks.GLOWSTONE, 4, 16, 1));
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return this.loot.get(rand);
	}
}
