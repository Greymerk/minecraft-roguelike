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

public class ItemPrecious extends ItemBase{
	
	private Map<Difficulty, WeightedRandomizer<ItemStack>> loot;

	
	public ItemPrecious(int weight, Difficulty diff) {
		super(weight, diff);
		
		this.loot = new HashMap<Difficulty, WeightedRandomizer<ItemStack>>();
		
		List.of(Difficulty.values()).forEach(d -> {
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			loot.put(d, randomizer);
			
			randomizer.add(new WeightedRandomLoot(Items.EMERALD, d.value + 1, 3 * (d.value + 1), 100));
			
			if(d.gt(Difficulty.EASY)) {
				randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 5));
				randomizer.add(new WeightedRandomLoot(Items.TOTEM_OF_UNDYING, diff.value + 1));
				randomizer.add(new WeightedRandomLoot(Items.OMINOUS_BOTTLE, diff.value + 1));
			}
			
			if(d.gt(Difficulty.MEDIUM)) {
				randomizer.add(new WeightedRandomLoot(Items.SHULKER_SHELL, diff.value + 1));
				randomizer.add(new WeightedRandomLoot(Items.NETHERITE_SCRAP, 1));
			}
		});
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff){
		return this.loot.get(diff).get(rand);
	}

}
