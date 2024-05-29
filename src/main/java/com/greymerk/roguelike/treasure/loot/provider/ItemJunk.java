package com.greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class ItemJunk extends ItemBase{

	private Map<Difficulty, WeightedRandomizer<ItemStack>> loot;
	
	public ItemJunk(int weight, Difficulty diff) {
		super(weight, diff	);
		
		this.loot = new HashMap<Difficulty, WeightedRandomizer<ItemStack>>();
		
		List.of(Difficulty.values()).forEach(d -> {
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			loot.put(d, randomizer);

			if(d.gt(Difficulty.MEDIUM)) {
				randomizer.add(new WeightedRandomLoot(Items.BLAZE_ROD, 1, 3, 100));
				randomizer.add(new WeightedRandomLoot(Items.AMETHYST_SHARD, 1, 3, 100));
				randomizer.add(new WeightedRandomLoot(Items.BLAZE_ROD, 1, 3, 100));
			}
			
			if(d.gt(Difficulty.EASY)) {
				randomizer.add(new WeightedRandomLoot(Items.ENDER_PEARL, 1, 3, 100));
				randomizer.add(new WeightedRandomLoot(Items.GLOW_INK_SAC, 1, 1, 100));
			}
			
			randomizer.add(new WeightedRandomLoot(Items.LEATHER, 1, d.value + 3, 100));
			randomizer.add(new WeightedRandomLoot(Items.ARROW, 1, d.value + 3, 100));
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
		});
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff){
		if(rand.nextInt(5000) == 0) return ItemNovelty.getItem(ItemNovelty.ZISTEAU);
		if(rand.nextInt(5000) == 0) return ItemNovelty.getItem(ItemNovelty.VECHS);
		return this.loot.get(diff).get(rand);
	}
}
