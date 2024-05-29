package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class ItemBlock extends ItemBase{
	
	private WeightedRandomizer<ItemStack> loot;
	
	public ItemBlock(int weight, Difficulty diff) {
		super(weight, diff);
		
		WeightedRandomizer<ItemStack> stone = new WeightedRandomizer<ItemStack>(10);
		stone.add(new WeightedRandomLoot(Blocks.COBBLESTONE, 8, 32, 1));
		stone.add(new WeightedRandomLoot(Blocks.STONE_BRICKS, 8, 32, 1));
		
		WeightedRandomizer<ItemStack> logs = new WeightedRandomizer<ItemStack>(3);
		logs.add(new WeightedRandomLoot(Blocks.OAK_LOG, 8, 32, 5));
		logs.add(new WeightedRandomLoot(Blocks.BIRCH_LOG, 8, 32, 3));
		logs.add(new WeightedRandomLoot(Blocks.SPRUCE_LOG, 8, 32, 3));
		logs.add(new WeightedRandomLoot(Blocks.JUNGLE_LOG, 8, 32, 2));
		logs.add(new WeightedRandomLoot(Blocks.DARK_OAK_LOG, 8, 32, 2));
		logs.add(new WeightedRandomLoot(Blocks.ACACIA_LOG, 8, 32, 1));
		logs.add(new WeightedRandomLoot(Blocks.MANGROVE_LOG, 8, 32, 1));
		
		this.loot = new WeightedRandomizer<ItemStack>();
		this.loot.add(stone);
		this.loot.add(logs);
		this.loot.add(new WeightedRandomLoot(Blocks.GLASS, 8, 32, 2));
		this.loot.add(new WeightedRandomLoot(Blocks.GLOWSTONE, 4, 16, 1));
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {
		return this.loot.get(rand);
	}
}
