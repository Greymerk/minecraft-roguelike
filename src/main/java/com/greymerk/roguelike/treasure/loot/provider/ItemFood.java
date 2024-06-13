package com.greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.treasure.loot.potions.PotionMixture;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.math.random.Random;

public class ItemFood extends ItemBase{

	private Map<Difficulty, WeightedRandomizer<ItemStack>> loot;
	private DynamicRegistryManager reg;
	
	public ItemFood(DynamicRegistryManager reg, int weight, Difficulty diff) {
		super(weight, diff);
		this.reg = reg;
		this.loot = new HashMap<Difficulty, WeightedRandomizer<ItemStack>>();
		
		List.of(Difficulty.values()).forEach(d -> {
			
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			
			switch(d){
			case HARDEST:
				randomizer.add(new WeightedRandomLoot(Items.GOLDEN_APPLE, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLDEN_CARROT, 2, 6, 1));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 3, 9, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 3, 9, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 3, 9, 3));
				break;
			case HARD:
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 2, 7, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 2, 7, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 2, 7, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 2, 7, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 4, 9, 5));
				break;
			case MEDIUM:
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 2, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 2, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 2, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 2, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 2, 5, 5));
				break;
			case EASY:	
				randomizer.add(new WeightedRandomLoot(Items.BREAD, 2, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_COD, 1, 4, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_SALMON, 1, 4, 5));
				randomizer.add(new WeightedRandomLoot(Items.APPLE, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 2, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 2, 5, 5));
				break;
			case EASIEST:
				randomizer.add(new WeightedRandomLoot(Items.BREAD, 1, 4, 4));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_COD, 1, 4, 2));
				randomizer.add(new WeightedRandomLoot(Items.APPLE, 1, 1, 2));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 1, 4, 1));
				randomizer.add(new WeightedRandomLoot(Items.COOKIE, 1, 1, 1));
				break;
			default:
				randomizer.add(new WeightedRandomLoot(Items.BREAD, 1));
			}
			
			loot.put(d, randomizer);
		});
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {
		if(rand.nextInt(2000) == 0) return ItemNovelty.getItem(reg, ItemNovelty.GENERIKB);
		if(rand.nextInt(2000) == 0) return ItemNovelty.getItem(reg, ItemNovelty.AVIDYA);
		if(rand.nextInt(1000) == 0) return ItemNovelty.getItem(reg, ItemNovelty.RLEAHY);
		if(rand.nextInt(1000) == 0) return ItemNovelty.getItem(reg, ItemNovelty.FOURLES);
		if(diff == Difficulty.EASIEST && rand.nextInt(20) == 0) return PotionMixture.getPotion(rand, PotionMixture.COFFEE);
		return this.loot.get(diff).get(rand);
	}


}
