package com.greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.util.Color;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public enum PotionMixture {

	TEQUILA, MOONSHINE, ABSINTHE, VILE, 
	LAUDANUM, RAGE, STOUT, STAMINA, NECTAR, COFFEE, AURA;
	
	public static ItemStack getPotion(Random rand, PotionMixture type){
		ItemStack potion;
		switch(type){
		case TEQUILA:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.STRENGTH, 3, 30 + rand.nextInt(60));
			PotionEffect.addCustomEffect(potion, PotionEffect.FATIGUE, 1, 30 + rand.nextInt(60));
			Loot.setItemName(potion, "Tequila");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(255, 232, 196));
			return potion;
		case LAUDANUM:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.REGEN, 3, 8);
			PotionEffect.addCustomEffect(potion, PotionEffect.WEAKNESS, 2, 5);
			PotionEffect.addCustomEffect(potion, PotionEffect.SLOWNESS, 2, 5);
			PotionEffect.addCustomEffect(potion, PotionEffect.FATIGUE, 2, 5);
			PotionEffect.addCustomEffect(potion, PotionEffect.NAUSIA, 1, 5);
			Loot.setItemName(potion, "Laudanum");
			Loot.setItemLore(potion, "A medicinal tincture.");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(150, 50, 0));
			return potion;
		case MOONSHINE:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.DAMAGE, 1, 1);
			PotionEffect.addCustomEffect(potion, PotionEffect.BLINDNESS, 1, 30 + rand.nextInt(60));
			PotionEffect.addCustomEffect(potion, PotionEffect.RESISTANCE, 2, 30 + rand.nextInt(30));
			Loot.setItemName(potion, "Moonshine");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(250, 240, 230));
			return potion;
		case ABSINTHE:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.POISON, 1, 3);
			PotionEffect.addCustomEffect(potion, PotionEffect.NIGHTVISION, 1, 120);
			PotionEffect.addCustomEffect(potion, PotionEffect.JUMP, 3, 120);
			Loot.setItemName(potion, "Absinthe");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(200, 250, 150));
			return potion;
		case VILE:
			potion = PotionItem.getSpecific(
					rand,
					PotionForm.values()[rand.nextInt(PotionForm.values().length)],
					PotionItem.values()[rand.nextInt(PotionItem.values().length)]
					);
			addRandomEffects(rand, potion, 2 + rand.nextInt(2));
			Loot.setItemName(potion, "Vile Mixture");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			return potion;
		case RAGE:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.STRENGTH, 3, 20);
			PotionEffect.addCustomEffect(potion, PotionEffect.BLINDNESS, 1, 10);
			PotionEffect.addCustomEffect(potion, PotionEffect.WITHER, 1, 3);
			Loot.setItemName(potion, "Animus");
			Loot.setItemLore(potion, "An unstable mixture.");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(255, 0, 0));
			return potion;
		case STAMINA:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.SATURATION, 10, 1);
			PotionEffect.addCustomEffect(potion, PotionEffect.SPEED, 2, 120);
			PotionEffect.addCustomEffect(potion, PotionEffect.HASTE, 2, 120);
			PotionEffect.addCustomEffect(potion, PotionEffect.JUMP, 3, 120);
			Loot.setItemName(potion, "Vitae");
			Loot.setItemLore(potion, "Essence of life.");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(230, 50, 20));
			return potion;
		case STOUT:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.REGEN, 1, 5);
			PotionEffect.addCustomEffect(potion, PotionEffect.SATURATION, 2, 1);
			PotionEffect.addCustomEffect(potion, PotionEffect.HEALTHBOOST, 2, 120);
			PotionEffect.addCustomEffect(potion, PotionEffect.RESISTANCE, 1, 120);
			Loot.setItemName(potion, "Stout");
			Loot.setItemLore(potion, "\"It's Good for You\"");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(50, 40, 20));
			return potion;
		case NECTAR:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.ABSORPTION, 4, 180);
			PotionEffect.addCustomEffect(potion, PotionEffect.RESISTANCE, 3, 180);
			PotionEffect.addCustomEffect(potion, PotionEffect.HEALTH, 2, 1);
			Loot.setItemName(potion, "Nectar");
			Loot.setItemLore(potion, "A Floral extract.");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(250, 150, 250));
			return potion;
		case COFFEE:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.HASTE, 2, 600);
			PotionEffect.addCustomEffect(potion, PotionEffect.SPEED, 1, 600);
			Loot.setItemName(potion, "Coffee");
			Loot.setItemLore(potion, "A darkroast bean brew.");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(20, 20, 10));
			return potion;
		case AURA:
			potion = PotionItem.getSpecific(PotionForm.REGULAR, null, false, false);
			PotionEffect.addCustomEffect(potion, PotionEffect.GLOWING, 1, 600);
			Loot.setItemName(potion, "Luma");
			Loot.setItemLore(potion, "A glowstone extract.");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			setColor(potion, Color.RGBToColor(250, 250, 0));
			return potion;
		default:
		}
		
		return new ItemStack(Items.GLASS_BOTTLE);
	}
	
	public static ItemStack getBooze(Random rand){
		
		final PotionMixture[] booze = {TEQUILA, LAUDANUM, MOONSHINE, ABSINTHE, STOUT};
		List<PotionMixture> potions = Arrays.asList(booze);
		int choice = rand.nextInt(potions.size());
		PotionMixture type = potions.get(choice);
		return getPotion(rand, type);
	}
	
	public static ItemStack getRandom(Random rand){
		final PotionMixture[] potions = {LAUDANUM, RAGE, STAMINA, NECTAR, COFFEE, AURA};
		int choice = rand.nextInt(potions.length);
		PotionMixture type = potions[choice];
		return getPotion(rand, type);
	}
	
	public static void addRandomEffects(Random rand, ItemStack potion, int numEffects){
		
		List<PotionEffect> effects = new ArrayList<PotionEffect>(Arrays.asList(PotionEffect.values()));
		RandHelper.shuffle(effects, rand);
		
		for(int i = 0; i < numEffects; ++i){
			
			PotionEffect type = effects.get(i);
			int duration;
			switch(type){
			case SATURATION:
			case HEALTH:
			case DAMAGE: duration = 1; break;
			case REGEN: duration = 10 + rand.nextInt(20); break;
			case HUNGER: duration = 5 + rand.nextInt(10); break;
			case WITHER:
			case POISON: duration = 5 + rand.nextInt(5); break;
			default: duration = 60 + rand.nextInt(120);
			}
			
			PotionEffect.addCustomEffect(potion, type, rand.nextInt(3), duration);
		}	
	}
	
	public static void setColor(ItemStack potion, int color){
		potion.getNbt().putInt("CustomPotionColor", color);
	}
}
