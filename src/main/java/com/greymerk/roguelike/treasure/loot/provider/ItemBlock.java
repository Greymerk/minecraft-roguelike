package com.greymerk.roguelike.treasure.loot.provider;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.item.ItemStack;

public class ItemBlock extends ItemBase{
	
	private WeightedRandomizer<ItemStack> loot;
	
	public ItemBlock(int weight, int level) {
		super(weight, level);
		this.loot = new WeightedRandomizer<ItemStack>();
		this.loot.add(new WeightedRandomLoot(BlockType.get(BlockType.ANDESITE_POLISHED).getBlock(), 8, 32, 1));
		this.loot.add(new WeightedRandomLoot(BlockType.get(BlockType.GRANITE_POLISHED).getBlock(), 8, 32, 1));
		this.loot.add(new WeightedRandomLoot(BlockType.get(BlockType.DIORITE_POLISHED).getBlock(), 8, 32, 1));
		this.loot.add(new WeightedRandomLoot(BlockType.get(BlockType.COBBLESTONE).getBlock(), 8, 32, 10));
		this.loot.add(new WeightedRandomLoot(BlockType.get(BlockType.STONE_BRICK).getBlock(), 8, 32, 5));
		this.loot.add(new WeightedRandomLoot(BlockType.get(BlockType.STONE_BRICK_MOSSY).getBlock(), 8, 32, 1));
		this.loot.add(new WeightedRandomLoot(BlockType.get(BlockType.STONE_BRICK_CRACKED).getBlock(), 8, 32, 1));
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return this.loot.get(rand);
	}
}
