package com.greymerk.roguelike.treasure.loot.potions;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public enum PotionItem {
	
	HEALING, HARM,
	REGEN, POISON,
	STRENGTH, WEAKNESS,
	SLOWNESS, SWIFTNESS,
	FIRERESIST, WATER_BREATHING,
	SLOW_FALLING, INVISIBILITY;
	
	public static ItemStack getRandom(RandomSource rand){
		PotionItem type = PotionItem.values()[rand.nextInt(PotionItem.values().length)];
		return getSpecific(rand, type);
	}
	
	public static ItemStack getSpecific(RandomSource rand, PotionItem effect){
		return getSpecific(PotionForm.REGULAR, effect, rand.nextBoolean(), rand.nextBoolean());
	}
	
	public static ItemStack getSpecific(RandomSource rand, PotionForm type, PotionItem effect){
		return getSpecific(type, effect, rand.nextBoolean(), rand.nextBoolean());
	}
		
	public static ItemStack getSpecific(PotionForm type, PotionItem effect, boolean upgrade, boolean extend){
		
		Item potion;
		
		switch(type){
		case REGULAR: potion = Items.POTION; break;
		case SPLASH: potion = Items.SPLASH_POTION; break;
		case LINGERING: potion = Items.LINGERING_POTION; break;
		default: potion = Items.POTION; break;
		}
		
		Holder<Potion> data = getEffect(effect, upgrade, extend);
		
		return PotionContents.createItemStack(potion, data);
		
		
	}
	
	public static Holder<Potion> getEffect(PotionItem effect, boolean upgrade, boolean extend){
		
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
		case INVISIBILITY:
			if(extend) {
				return Potions.LONG_INVISIBILITY;
			} else {
				return Potions.INVISIBILITY;
			}
		case SLOW_FALLING:
			if(extend) {
				return Potions.LONG_SLOW_FALLING;	
			} else {
				return Potions.SLOW_FALLING;
			}
		case WATER_BREATHING:
			if(extend) {
				return Potions.LONG_WATER_BREATHING;
			} else {
				return Potions.WATER_BREATHING;
			}
		default: return Potions.AWKWARD; 
		}
	}
}
