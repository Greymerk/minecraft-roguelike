package com.greymerk.roguelike.treasure.loot;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.ItemStack;

public enum ItemHideFlags {

	ENCHANTMENTS, ATTRIBUTES, UNBREAKABLE, CANDESTROY, CANPLACEON, EFFECTS;
		
	public static void set(ItemHideFlags flag, ItemStack item){
		switch(flag) {
		case EFFECTS: item.set(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT);
		default:
			break; 
		}
	}
}
