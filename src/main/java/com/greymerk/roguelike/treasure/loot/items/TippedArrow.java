package com.greymerk.roguelike.treasure.loot.items;

import com.greymerk.roguelike.treasure.loot.potions.PotionItem;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

public class TippedArrow {

	public static ItemStack get(RandomSource rand){	
		return get(rand, 1);
	}
	
	public static ItemStack get(RandomSource rand, int amount){
		PotionItem type = PotionItem.values()[rand.nextInt(PotionItem.values().length)];
		return get(type, amount);
	}
		
	public static ItemStack get(PotionItem type){
		return get(type, 1);
	}
	
	public static ItemStack get(Holder<Potion> type){
		return get(type, 1);
	}
	
	public static ItemStack get(PotionItem type, int amount){
		
		Holder<Potion> pot = PotionItem.getEffect(type, false, false);
		return get(pot, amount);

	}
	
	public static ItemStack get(Holder<Potion> type, int amount){
		
		ItemStack arrow = new ItemStack(Items.TIPPED_ARROW, amount);
		PotionContents contents = new PotionContents(type);
		arrow.set(DataComponents.POTION_CONTENTS, contents);
		
		return arrow;
	}
	
	public static ItemStack getHarmful(RandomSource rand, int amount){
		switch(rand.nextInt(4)){
		case 0: return TippedArrow.get(PotionItem.HARM, amount);
		case 1: return TippedArrow.get(PotionItem.POISON, amount);
		case 2: return TippedArrow.get(PotionItem.SLOWNESS, amount);
		case 3: return TippedArrow.get(PotionItem.WEAKNESS, amount);
		default: return new ItemStack(Items.ARROW, amount);
		}
	}
	
	
	
}