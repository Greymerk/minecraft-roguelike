package com.greymerk.roguelike.treasure.loot;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Unit;

public enum ItemHideFlags {

	ENCHANTMENTS, ATTRIBUTES, UNBREAKABLE, CANDESTROY, CANPLACEON, EFFECTS;
		
	public static void set(ItemHideFlags flag, ItemStack item){
		switch(flag) {
		case EFFECTS: item.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
		default:
			break; 
		}
	}
}
