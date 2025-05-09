package com.greymerk.roguelike.treasure.loot.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class OminousBottle {

	public static ItemStack get(int rank) {
		
		ItemStack bottle = new ItemStack(Items.OMINOUS_BOTTLE);
		int amp = Math.clamp(rank, 0, 4);
		bottle.set(DataComponentTypes.OMINOUS_BOTTLE_AMPLIFIER, amp);
		
		return bottle;
	}
	
}
