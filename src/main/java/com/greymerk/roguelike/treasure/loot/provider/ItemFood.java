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
				randomizer.add(new WeightedRandomLoot(Items.GOLDEN_APPLE, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLDEN_CARROT, 1, 6, 1));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 1, 9, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 1, 9, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 1, 9, 3));
				break;
			case HARD:
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 1, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 1, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 1, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 1, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 1, 5, 5));
				break;
			case MEDIUM:
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 1, 3, 2));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 1, 3, 2));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 1, 3, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 1, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 1, 5, 5));
				break;
			case EASY:
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.APPLE, 1));
				randomizer.add(new WeightedRandomLoot(Items.MUSHROOM_STEW, 1));
				randomizer.add(new WeightedRandomLoot(Items.BREAD, 1, 3, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_COD, 1, 3, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_SALMON, 1, 2, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 1, 3, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 1, 3, 5));
				break;
			case EASIEST:
				randomizer.add(new WeightedRandomLoot(Items.BREAD, 1, 2, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_COD, 1, 2, 2));
				randomizer.add(new WeightedRandomLoot(Items.APPLE, 1));
				randomizer.add(new WeightedRandomLoot(Items.MUSHROOM_STEW, 1));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 1, 2, 2));
				randomizer.add(new WeightedRandomLoot(Items.COOKIE, 1));
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
		if(diff.lt(Difficulty.MEDIUM) && rand.nextInt(200) == 0) return PotionMixture.getPotion(rand, PotionMixture.COFFEE);
		if(diff.gt(Difficulty.EASY) && rand.nextInt(100) == 0) return PotionMixture.getPotion(rand, PotionMixture.LAUDANUM);
		return this.loot.get(diff).get(rand);
	}


}
