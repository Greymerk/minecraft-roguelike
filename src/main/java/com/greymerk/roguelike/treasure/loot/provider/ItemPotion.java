package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.potions.PotionForm;
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
		
		if(rand.nextInt(10) == 0) return PotionMixture.getPotion(rand, PotionMixture.LAUDANUM);
		
		if(diff.gt(Difficulty.MEDIUM) && rand.nextBoolean()) {
			return PotionMixture.getPotion(rand, PotionMixture.VILE);
		} else if(diff.gt(Difficulty.EASIEST) && rand.nextBoolean()) {
			return PotionMixture.getRandom(rand);
		}
		
		if(rand.nextInt(10) == 0) {
			return PotionMixture.getBooze(rand);
		}
		
		final PotionItem[] potions = new PotionItem[]{
				PotionItem.HEALING,
				PotionItem.STRENGTH,
				PotionItem.SWIFTNESS,
				PotionItem.REGEN
		};
		PotionItem type = potions[rand.nextInt(potions.length)];
		return PotionItem.getSpecific(PotionForm.REGULAR, type, true, false);
	}
}
