package com.greymerk.roguelike.treasure.loot;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;

public enum ItemHideFlags {

	ENCHANTMENTS, ATTRIBUTES, UNBREAKABLE, CANDESTROY, CANPLACEON, EFFECTS;
		
	public static void set(ItemHideFlags flag, ItemStack item){
		switch(flag) {
		case EFFECTS: item.set(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
		default:
			break; 
		}
	}
}
