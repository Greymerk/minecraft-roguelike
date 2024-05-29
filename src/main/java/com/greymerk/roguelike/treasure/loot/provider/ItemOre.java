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

public class ItemOre extends ItemBase{

	private Map<Difficulty, WeightedRandomizer<ItemStack>> loot;
	
	public ItemOre(int weight, Difficulty diff) {
		super(weight, diff);
		this.loot = new HashMap<Difficulty, WeightedRandomizer<ItemStack>>();
		List.of(Difficulty.values()).forEach(d -> {
			
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			
			switch(d){
			case HARDEST:
				randomizer.add(new WeightedRandomLoot(Items.NETHERITE_INGOT, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 8, 32, 10));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 8, 32, 10));
				randomizer.add(new WeightedRandomLoot(Items.COPPER_INGOT, 16, 64, 5));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 8, 32, 5));
				break;
			case HARD:
				randomizer.add(new WeightedRandomLoot(Items.NETHERITE_INGOT, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 4, 24, 20));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 4, 24, 20));
				randomizer.add(new WeightedRandomLoot(Items.COPPER_INGOT, 8, 32, 20));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 8, 32, 10));
				break;
			case MEDIUM:
				randomizer.add(new WeightedRandomLoot(Items.NETHERITE_INGOT, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 4, 16, 30));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 4, 16, 50));
				randomizer.add(new WeightedRandomLoot(Items.COPPER_INGOT, 8, 32, 50));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 4, 24, 20));
				break;
			case EASY:	
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 2, 8, 5));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 2, 8, 10));
				randomizer.add(new WeightedRandomLoot(Items.COPPER_INGOT, 4, 16, 5));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 2, 16, 10));
				break;
			case EASIEST:
				randomizer.add(new WeightedRandomLoot(Items.GOLD_INGOT, 1, 4, 5));
				randomizer.add(new WeightedRandomLoot(Items.IRON_INGOT, 1, 4, 10));
				randomizer.add(new WeightedRandomLoot(Items.COPPER_INGOT, 1, 4, 10));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 1, 3, 20));
				break;
			default:
				randomizer.add(new WeightedRandomLoot(Items.COAL, 1));
			}
			
			loot.put(d, randomizer);
		});
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {
		return this.loot.get(diff).get(rand);
	}
}
