package com.greymerk.roguelike.treasure.loot;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;

public class TippedArrow {

	public static ItemStack get(Random rand){	
		return get(rand, 1);
	}
	
	public static ItemStack get(Random rand, int amount){
		PotionItem type = PotionItem.values()[rand.nextInt(PotionItem.values().length)];
		return get(type, amount);
	}
		
	public static ItemStack get(PotionItem type){
		return get(type, 1);
	}
	
	public static ItemStack get(RegistryEntry<Potion> type){
		return get(type, 1);
	}
	
	public static ItemStack get(PotionItem type, int amount){
		
		RegistryEntry<Potion> pot = PotionItem.getEffect(type, false, false);
		return get(pot, amount);

	}
	
	public static ItemStack get(RegistryEntry<Potion> type, int amount){
		
		ItemStack arrow = new ItemStack(Items.TIPPED_ARROW, amount);
		PotionContentsComponent contents = new PotionContentsComponent(type);
		arrow.set(DataComponentTypes.POTION_CONTENTS, contents);
		
		return arrow;
	}
	
	public static ItemStack getHarmful(Random rand, int amount){
		switch(rand.nextInt(4)){
		case 0: return TippedArrow.get(PotionItem.HARM, amount);
		case 1: return TippedArrow.get(PotionItem.POISON, amount);
		case 2: return TippedArrow.get(PotionItem.SLOWNESS, amount);
		case 3: return TippedArrow.get(PotionItem.WEAKNESS, amount);
		default: return new ItemStack(Items.ARROW, amount);
		}
	}
	
	
	
}