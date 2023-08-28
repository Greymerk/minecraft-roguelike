package com.greymerk.roguelike.treasure.loot.provider;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.treasure.loot.PotionItem;
import com.greymerk.roguelike.treasure.loot.PotionForm;

import net.minecraft.item.ItemStack;

public class ItemPotion extends ItemBase{

	public ItemPotion(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
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
