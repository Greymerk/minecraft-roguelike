package com.greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;

import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class ItemPrecious extends ItemBase{
	
	private Map<Integer, WeightedRandomizer<ItemStack>> loot;

	
	public ItemPrecious(int weight, int level) {
		super(weight, level);
		
		this.loot = new HashMap<Integer, WeightedRandomizer<ItemStack>>();
		
		for(int i = 0; i < 5; ++i) {
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			loot.put(i, randomizer);
			
			randomizer.add(new WeightedRandomLoot(Items.EMERALD, i + 1, 3 * (i + 1), 100));
			randomizer.add(new WeightedRandomLoot(Items.AMETHYST_SHARD, 1, 1, 10));
			randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 1, 1, 5));
			if(i > 1) {
				randomizer.add(new WeightedRandomLoot(Items.SHULKER_SHELL, 1, 1, 1));	
			}
			
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, int level){
		return this.loot.get(level).get(rand);
	}

}
