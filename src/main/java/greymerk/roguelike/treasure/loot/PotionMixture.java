package greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

public enum PotionMixture {

	TEQUILA, LAUDANUM, MOONSHINE, ABSINTHE, STOUT, VILE;
	
	public static ItemStack getPotion(Random rand, PotionMixture type){
		ItemStack potion;
		int duration;
		switch(type){
		case TEQUILA:
			potion = Potion.getSpecific(rand, PotionType.REGULAR, Potion.FIRERESIST, false, false);
			duration = 1000 + rand.nextInt(2000);
			PotionEffect.addCustomEffect(potion, PotionEffect.STRENGTH, 3, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.FATIGUE, 1, duration);
			Loot.setItemName(potion, "Tequila");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			return potion;
		case LAUDANUM:
			potion = Potion.getSpecific(rand, PotionType.REGULAR, Potion.STRENGTH, false, false);
			duration = 200;
			PotionEffect.addCustomEffect(potion, PotionEffect.REGEN, 2, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.WEAKNESS, 2, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.SLOWNESS, 2, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.FATIGUE, 2, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.NAUSIA, 0, duration);
			Loot.setItemName(potion, "Laudanum");
			Loot.setItemLore(potion, "A medicinal tincture.");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			return potion;
		case MOONSHINE:
			potion = Potion.getSpecific(rand, PotionType.REGULAR, Potion.WEAKNESS, false, false);
			duration = 1000 + rand.nextInt(2000);
			PotionEffect.addCustomEffect(potion, PotionEffect.DAMAGE, 2, 1);
			PotionEffect.addCustomEffect(potion, PotionEffect.BLINDNESS, 1, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.RESISTANCE, 2, duration);
			Loot.setItemName(potion, "Moonshine");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			return potion;
		case ABSINTHE:
			potion = Potion.getSpecific(rand, PotionType.REGULAR, Potion.POISON, false, false);
			duration = 1000 + rand.nextInt(2000);
			PotionEffect.addCustomEffect(potion, PotionEffect.POISON, 1, 200 + rand.nextInt(300));
			PotionEffect.addCustomEffect(potion, PotionEffect.NIGHTVISION, 1, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.JUMP, 2, duration);
			Loot.setItemName(potion, "Absinthe");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			return potion;
		case STOUT:
			potion = Potion.getSpecific(rand, PotionType.REGULAR, Potion.HARM, false, false);
			duration = 2000 + rand.nextInt(2000);
			PotionEffect.addCustomEffect(potion, PotionEffect.REGEN, 1, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.FATIGUE, 1, duration);
			PotionEffect.addCustomEffect(potion, PotionEffect.HEALTHBOOST, 2, duration);
			Loot.setItemName(potion, "Stout");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			return potion;
		case VILE:
			potion = Potion.getSpecific(rand, Potion.values()[rand.nextInt(Potion.values().length)]);
			addRandomEffects(rand, potion, 2 + rand.nextInt(2));
			Loot.setItemName(potion, "Vile Mixture");
			ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
			return potion;
		}
		
		return null;
	}
	
	public static ItemStack getBooze(Random rand){
		
		final PotionMixture[] booze = {TEQUILA, LAUDANUM, MOONSHINE, ABSINTHE, STOUT};
		List<PotionMixture> potions = Arrays.asList(booze);
		int choice = rand.nextInt(potions.size());
		PotionMixture type = potions.get(choice);
		return getPotion(rand, type);
	}
	
	public static ItemStack getRandom(Random rand){
		List<PotionMixture> potions = Arrays.asList(PotionMixture.values());
		int choice = rand.nextInt(potions.size());
		PotionMixture type = potions.get(choice);
		return getPotion(rand, type);
	}
	
	public static void addRandomEffects(Random rand, ItemStack potion, int numEffects){
		
		List<PotionEffect> effects = new ArrayList<PotionEffect>(Arrays.asList(PotionEffect.values()));
		Collections.shuffle(effects, rand);
		
		for(int i = 0; i < numEffects; ++i){
			
			PotionEffect type = effects.get(i);
			int duration;
			switch(type){
			case SATURATION:
			case HEALTH:
			case DAMAGE: duration = 1; break;
			case REGEN: duration = 200 + rand.nextInt(400); break;
			case HUNGER: duration = 100 + rand.nextInt(400); break;
			case WITHER:
			case POISON: duration = 100 + rand.nextInt(200); break;
			default: duration = 1000 + rand.nextInt(2000);
			}
			
			PotionEffect.addCustomEffect(potion, type, rand.nextInt(3), duration);
		}
		
	}
	
	
}
