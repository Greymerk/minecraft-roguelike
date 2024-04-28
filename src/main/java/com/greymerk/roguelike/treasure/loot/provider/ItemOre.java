package com.greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemOre extends ItemBase{

	private Map<Integer, WeightedRandomizer<ItemStack>> loot;
	
	public ItemOre(int weight, int level) {
		super(weight, level);
		this.loot = new HashMap<Integer, WeightedRandomizer<ItemStack>>();
		for(int i = 0; i < 5; ++i){
			
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			
			switch(i){
			case 4:
				randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.EMERALD, 1, 1, 2));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 2, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_NUGGET, 2, 8, 2));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 2, 5, 5));
				break;
			case 3:
				randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.EMERALD, 1, 1, 2));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 1, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_NUGGET, 2, 6, 5));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 1, 4, 10));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 4, 15, 3));
				break;
			case 2:
				randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 1, 4, 3));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_NUGGET, 1, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 1, 3, 10));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 3, 7, 10));
				break;
			case 1:	
				randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 1, 3, 5));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_NUGGET, 1, 4, 10));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 1, 2, 5));
				randomizer.add(new WeightedRandomLoot(Items.IRON_NUGGET, 1, 5, 20));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 2, 5, 20));
				randomizer.add(new WeightedRandomLoot(Items.LEATHER, 3, 9, 10));
				break;
			case 0:
				randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 1, 1, 3));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_NUGGET, 1, 2, 15));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 1, 1, 10));
				randomizer.add(new WeightedRandomLoot(Items.IRON_NUGGET, 1, 5, 30));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 1, 3, 40));
				randomizer.add(new WeightedRandomLoot(Items.LEATHER, 1, 7, 15));
				break;
			default:
				randomizer.add(new WeightedRandomLoot(Items.COAL, 1));
			}
			
			loot.put(i, randomizer);
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return this.loot.get(level).get(rand);
	}
}
