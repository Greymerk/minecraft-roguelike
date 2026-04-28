package com.greymerk.roguelike.treasure.loot.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.OminousBottleAmplifier;

public class OminousBottle {

	public static ItemStack get(int rank) {
		
		ItemStack bottle = new ItemStack(Items.OMINOUS_BOTTLE);
		OminousBottleAmplifier amp = new OminousBottleAmplifier(Math.clamp(rank, 0, 4));
		bottle.set(DataComponents.OMINOUS_BOTTLE_AMPLIFIER, amp);
		
		return bottle;
	}
	
}
