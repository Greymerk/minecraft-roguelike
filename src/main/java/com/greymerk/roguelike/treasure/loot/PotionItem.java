package com.greymerk.roguelike.treasure.loot;

import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;

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
		
		Item potion;
		
		switch(type){
		case REGULAR: potion = Items.POTION; break;
		case SPLASH: potion = Items.SPLASH_POTION; break;
		case LINGERING: potion = Items.LINGERING_POTION; break;
		default: potion = Items.POTION; break;
		}
		
		RegistryEntry<Potion> data = getEffect(effect, upgrade, extend);
		
		return PotionContentsComponent.createStack(potion, data);
		
		
	}
	
	public static RegistryEntry<Potion> getEffect(PotionItem effect, boolean upgrade, boolean extend){
		
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
