package com.greymerk.roguelike.treasure.loot;

import net.minecraft.util.math.random.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

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
	
	public static ItemStack get(Potion type){
		return get(type, 1);
	}
	
	public static ItemStack get(PotionItem type, int amount){
		
		Potion pot = PotionItem.getEffect(type, false, false);
		return get(pot, amount);

	}
	
	public static ItemStack get(Potion type, int amount){
		Identifier id = Registries.POTION.getId(type);
		
		ItemStack arrow = new ItemStack(Items.TIPPED_ARROW, amount);
		
		NbtCompound nbt = new NbtCompound();
		nbt.putString("Potion", id.getPath());
		
		arrow.setNbt(nbt);
		
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
