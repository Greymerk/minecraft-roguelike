package com.greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;

import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class ItemJunk extends ItemBase{

	private Map<Integer, WeightedRandomizer<ItemStack>> loot;
	
	public ItemJunk(int weight, int level) {
		super(weight, level);
		
		this.loot = new HashMap<Integer, WeightedRandomizer<ItemStack>>();
		
		for(int i = 0; i < 5; ++i) {
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			loot.put(i, randomizer);

			if(i > 2) {
				randomizer.add(new WeightedRandomLoot(Items.BLAZE_ROD, 1, 3, 100));
				randomizer.add(new WeightedRandomLoot(Items.AMETHYST_SHARD, 1, 3, 100));
				randomizer.add(new WeightedRandomLoot(Items.BLAZE_ROD, 1, 3, 100));
			}
			
			if(i > 1) {
				randomizer.add(new WeightedRandomLoot(Items.ENDER_PEARL, 1, 3, 100));
				randomizer.add(new WeightedRandomLoot(Items.GLOW_INK_SAC, 1, 1, 100));
			}
			
			randomizer.add(new WeightedRandomLoot(Items.LEATHER, 1, i + 3, 100));
			randomizer.add(new WeightedRandomLoot(Items.ARROW, 1, i + 3, 100));
			randomizer.add(new WeightedRandomLoot(Items.INK_SAC, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.FEATHER, 1, 3, 100));
			randomizer.add(new WeightedRandomLoot(Items.PAPER, 1, 3, 100));
			randomizer.add(new WeightedRandomLoot(Items.STICK, 1, 3, 100));
			randomizer.add(new WeightedRandomLoot(Items.STRING, 1, 3, 100));
			randomizer.add(new WeightedRandomLoot(Items.DRIED_KELP, 1, 3, 100));
			randomizer.add(new WeightedRandomLoot(Items.BOWL, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.GLASS_BOTTLE, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.ROTTEN_FLESH, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.SLIME_BALL, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.CLAY_BALL, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.FLINT, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.BONE, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.SPIDER_EYE, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.SNOWBALL, 1, 1, 100));
			randomizer.add(new WeightedRandomLoot(Items.LEAD, 1, 1, 10));
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, int level){
		if(rand.nextInt(5000) == 0) return ItemNovelty.getItem(ItemNovelty.ZISTEAU);
		if(rand.nextInt(5000) == 0) return ItemNovelty.getItem(ItemNovelty.VECHS);
		return this.loot.get(level).get(rand);
	}
}
