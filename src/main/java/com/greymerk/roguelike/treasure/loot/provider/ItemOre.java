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
				randomizer.add(new WeightedRandomLoot(Items.NETHERITE_SCRAP, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 1, 1, 5));
				randomizer.add(new WeightedRandomLoot(Items.RAW_GOLD, 1, 16, 25));
				randomizer.add(new WeightedRandomLoot(Items.RAW_IRON, 1, 16, 25));
				randomizer.add(new WeightedRandomLoot(Items.RAW_COPPER, 4, 32, 3));
				randomizer.add(new WeightedRandomLoot(Items.LAPIS_LAZULI, 8, 32, 5));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 8, 32, 10));
				break;
			case HARD:
				randomizer.add(new WeightedRandomLoot(Items.NETHERITE_SCRAP, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.DIAMOND, 1, 1, 5));
				randomizer.add(new WeightedRandomLoot(Items.RAW_GOLD, 2, 12, 20));
				randomizer.add(new WeightedRandomLoot(Items.RAW_IRON, 1, 7, 40));
				randomizer.add(new WeightedRandomLoot(Items.RAW_COPPER, 8, 32, 3));
				randomizer.add(new WeightedRandomLoot(Items.LAPIS_LAZULI, 4, 16, 10));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 8, 32, 10));
				break;
			case MEDIUM:
				randomizer.add(new WeightedRandomLoot(Items.RAW_GOLD, 1, 6, 30));
				randomizer.add(new WeightedRandomLoot(Items.RAW_IRON, 1, 5, 50));
				randomizer.add(new WeightedRandomLoot(Items.RAW_COPPER, 4, 16, 5));
				randomizer.add(new WeightedRandomLoot(Items.LAPIS_LAZULI, 2, 8, 10));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 4, 24, 20));
				break;
			case EASY:	
				randomizer.add(new WeightedRandomLoot(Items.RAW_GOLD, 1, 4, 5));
				randomizer.add(new WeightedRandomLoot(Items.RAW_IRON, 1, 3, 10));
				randomizer.add(new WeightedRandomLoot(Items.RAW_COPPER, 2, 8, 1));
				randomizer.add(new WeightedRandomLoot(Items.LAPIS_LAZULI, 1, 4, 2));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 1, 8, 5));
				break;
			case EASIEST:
				randomizer.add(new WeightedRandomLoot(Items.RAW_GOLD, 1, 2, 3));
				randomizer.add(new WeightedRandomLoot(Items.RAW_IRON, 1, 2, 10));
				randomizer.add(new WeightedRandomLoot(Items.RAW_COPPER, 1, 1, 2));
				randomizer.add(new WeightedRandomLoot(Items.COAL, 1, 3, 10));
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
