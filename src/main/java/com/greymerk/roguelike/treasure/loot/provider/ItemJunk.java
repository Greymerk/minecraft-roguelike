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
			
			if(i <= 1) {
				randomizer.add(new WeightedRandomLoot(Items.FEATHER, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.PAPER, 1, 3, 1));
			}
			
			if(i <= 3) {
				randomizer.add(new WeightedRandomLoot(Items.STICK, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.LEATHER, 1, i + 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.INK_SAC, 1, 1, 1));
			} else {
				randomizer.add(new WeightedRandomLoot(Items.BLAZE_ROD, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.AMETHYST_SHARD, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.BLAZE_ROD, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.GLOW_INK_SAC, 1, 1, 1));
			}
			
			randomizer.add(new WeightedRandomLoot(Items.STRING, 1, 3, 1));
			randomizer.add(new WeightedRandomLoot(Items.DRIED_KELP, 1, 3, 1));
			randomizer.add(new WeightedRandomLoot(Items.BOWL, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.MELON_SLICE, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.GLASS_BOTTLE, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.ROTTEN_FLESH, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.SLIME_BALL, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.CLAY_BALL, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.FLINT, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.BONE, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.SPIDER_EYE, 1, 1, 1));
			randomizer.add(new WeightedRandomLoot(Items.SNOWBALL, 1, 1, 1));
			
			randomizer.add(new WeightedRandomLoot(Items.EMERALD, i + 1, (i * 2) + 1, 3));
			
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, int level){
		return this.loot.get(level).get(rand);
	}
}
