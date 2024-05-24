package com.greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import com.greymerk.roguelike.treasure.loot.potions.PotionMixture;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemFood extends ItemBase{

	private Map<Integer, WeightedRandomizer<ItemStack>> loot;
	
	public ItemFood(int weight, int level) {
		super(weight, level);
		this.loot = new HashMap<Integer, WeightedRandomizer<ItemStack>>();
		for(int i = 0; i < 5; ++i){
			
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			
			switch(i){
			case 4:
				randomizer.add(new WeightedRandomLoot(Items.GOLDEN_APPLE, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.GOLDEN_CARROT, 2, 6, 1));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 3, 9, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 3, 9, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 3, 9, 3));
				break;
			case 3:
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 2, 7, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 2, 7, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 2, 7, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 2, 7, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 4, 9, 5));
				break;
			case 2:
				randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 2, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 2, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 2, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 2, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 2, 5, 5));
				break;
			case 1:	
				randomizer.add(new WeightedRandomLoot(Items.BREAD, 2, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_COD, 1, 4, 5));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_SALMON, 1, 4, 5));
				randomizer.add(new WeightedRandomLoot(Items.APPLE, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 2, 5, 5));
				randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 2, 5, 5));
				break;
			case 0:
				randomizer.add(new WeightedRandomLoot(Items.BREAD, 1, 4, 4));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_COD, 1, 4, 2));
				randomizer.add(new WeightedRandomLoot(Items.APPLE, 1, 1, 2));
				randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 1, 4, 1));
				randomizer.add(new WeightedRandomLoot(Items.COOKIE, 1, 1, 1));
				break;
			default:
				randomizer.add(new WeightedRandomLoot(Items.BREAD, 1));
			}
			
			loot.put(i, randomizer);
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(rand.nextInt(2000) == 0) return ItemNovelty.getItem(ItemNovelty.GENERIKB);
		if(rand.nextInt(2000) == 0) return ItemNovelty.getItem(ItemNovelty.AVIDYA);
		if(rand.nextInt(1000) == 0) return ItemNovelty.getItem(ItemNovelty.RLEAHY);
		if(rand.nextInt(1000) == 0) return ItemNovelty.getItem(ItemNovelty.FOURLES);
		if(level == 0 && rand.nextInt(20) == 0) return PotionMixture.getPotion(rand, PotionMixture.COFFEE);
		return this.loot.get(level).get(rand);
	}


}
