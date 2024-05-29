package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class ItemBrewing extends ItemBase{

	private WeightedRandomizer<ItemStack> items;
	
	public ItemBrewing(int weight, Difficulty diff) {
		super(weight, diff);
		this.items = new WeightedRandomizer<ItemStack>();
		this.items.add(new WeightedRandomLoot(Items.SPIDER_EYE, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.BLAZE_POWDER, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.MAGMA_CREAM, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.GHAST_TEAR, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.NETHER_WART, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.REDSTONE, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.GLOWSTONE_DUST, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.SUGAR, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.GLISTERING_MELON_SLICE, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.FERMENTED_SPIDER_EYE, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Blocks.BROWN_MUSHROOM, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Blocks.RED_MUSHROOM, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.RABBIT_FOOT, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.PUFFERFISH, 1, 3, 1));
		this.items.add(new WeightedRandomLoot(Items.GLASS_BOTTLE, 3, 12, 1));
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {
		return this.items.get(rand);
	}
}
