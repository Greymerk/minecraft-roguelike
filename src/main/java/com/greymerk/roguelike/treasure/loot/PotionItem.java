package com.greymerk.roguelike.treasure.loot;

import net.minecraft.util.math.random.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;

public enum PotionItem {
	
	HEALING, HARM, REGEN, POISON, STRENGTH, WEAKNESS, SLOWNESS, SWIFTNESS, FIRERESIST;
	
	public static ItemStack getRandom(Random rand){
		PotionItem type = PotionItem.values()[rand.nextInt(PotionItem.values().length)];
		return getSpecific(rand, type);
	}
	
	public static ItemStack getSpecific(Random rand, PotionItem effect){
		return getSpecific(PotionForm.REGULAR, effect, rand.nextBoolean(), rand.nextBoolean());
	}
	
	public static ItemStack getSpecific(Random rand, PotionForm type, PotionItem effect){
		return getSpecific(type, effect, rand.nextBoolean(), rand.nextBoolean());
	}
		
	public static ItemStack getSpecific(PotionForm type, PotionItem effect, boolean upgrade, boolean extend){
		
		ItemStack potion;
		
		switch(type){
		case REGULAR: potion = new ItemStack(Items.POTION); break;
		case SPLASH: potion = new ItemStack(Items.SPLASH_POTION); break;
		case LINGERING: potion = new ItemStack(Items.LINGERING_POTION); break;
		default: potion = new ItemStack(Items.POTION); break;
		}
		
		Potion data = getEffect(effect, upgrade, extend);
		
		return PotionUtil.setPotion(potion, data);
	}
	
	public static Potion getEffect(PotionItem effect, boolean upgrade, boolean extend){
		
		if(effect == null) return Potions.AWKWARD;
		
		switch(effect){
		case HEALING: return upgrade ? Potions.STRONG_HEALING : Potions.HEALING;
		case HARM: return upgrade ? Potions.STRONG_HARMING : Potions.HARMING;
		case REGEN:
			if(extend){
				return Potions.LONG_REGENERATION;
			} else {
				return upgrade ? Potions.STRONG_REGENERATION : Potions.REGENERATION;
			}
		case POISON:
			if(extend){
				return Potions.LONG_POISON;
			} else {
				return upgrade ? Potions.STRONG_POISON : Potions.POISON;
			}
		case STRENGTH:
			if(extend){
				return Potions.LONG_STRENGTH;
			} else {
				return upgrade ? Potions.STRONG_STRENGTH : Potions.STRENGTH;
			}
		case WEAKNESS:
			if(extend){
				return Potions.LONG_WEAKNESS;
			} else {
				return Potions.WEAKNESS;
			}
		case SLOWNESS:
			if(extend){
				return Potions.LONG_SLOWNESS;
			} else {
				return Potions.SLOWNESS;
			}
		case SWIFTNESS:
			if(extend){
				return Potions.LONG_SWIFTNESS;
			} else {
				return upgrade ? Potions.STRONG_SWIFTNESS : Potions.SWIFTNESS;
			}
		case FIRERESIST:
			if(extend){
				return Potions.LONG_FIRE_RESISTANCE;
			} else {
				return Potions.FIRE_RESISTANCE;
			} 
		default: return Potions.AWKWARD;
		}
	}
}
