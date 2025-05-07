package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.potions.PotionItem;
import com.greymerk.roguelike.treasure.loot.potions.PotionMixture;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class ItemPotion extends ItemBase{
	
	public ItemPotion(int weight, Difficulty diff) {
		super(weight, diff);
	}
	
	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {
		
		if(rand.nextInt(20) == 0) {
			if(diff.gt(Difficulty.MEDIUM) && rand.nextInt(3) == 0) {
				return PotionMixture.getPotion(rand, PotionMixture.VILE);
			} else if(diff.gt(Difficulty.EASIEST) && rand.nextInt(3) == 0) {
				return PotionMixture.getRandom(rand);
			}	
			
			if(rand.nextInt(6) == 0) {
				return PotionMixture.getBooze(rand);
			}
			
			return PotionMixture.getPotion(rand, PotionMixture.LAUDANUM);
		}
		
		return PotionItem.getRandom(rand);
	}
}
