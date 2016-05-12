package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;

public enum Potion {
	
	HEALING, HARM, REGEN, POISON, STRENGTH, WEAKNESS, SLOWNESS, SWIFTNESS, FIRERESIST;
	
	public static ItemStack getRandom(Random rand){
		Potion type = Potion.values()[rand.nextInt(Potion.values().length)];
		return getSpecific(rand, type);
	}
	
	public static ItemStack getSpecific(Random rand, Potion effect){
		return getSpecific(PotionType.REGULAR, effect, rand.nextBoolean(), rand.nextBoolean());
	}
	
	public static ItemStack getSpecific(Random rand, PotionType type, Potion effect){
		return getSpecific(type, effect, rand.nextBoolean(), rand.nextBoolean());
	}

	public static ItemStack getSpecific(PotionType type, Potion effect, boolean upgrade, boolean extend){
		
		ItemStack potion;
		
		switch(type){
		case REGULAR: potion = new ItemStack(Items.potionitem); break;
		case SPLASH: potion = new ItemStack(Items.splash_potion); break;
		case LINGERING: potion = new ItemStack(Items.lingering_potion); break;
		default: potion = new ItemStack(Items.potionitem); break;
		}
		
		net.minecraft.potion.PotionType data = getEffect(effect, upgrade, extend);
		
		return PotionUtils.addPotionToItemStack(potion, data);
	}
	
	public static net.minecraft.potion.PotionType getEffect(Potion effect, boolean upgrade, boolean extend){
		switch(effect){
		case HEALING: return upgrade ? PotionTypes.strong_healing : PotionTypes.healing;
		case HARM: return upgrade ? PotionTypes.strong_harming : PotionTypes.harming;
		case REGEN:
			if(extend){
				return PotionTypes.long_regeneration;
			} else {
				return upgrade ? PotionTypes.strong_regeneration : PotionTypes.regeneration;
			}
		case POISON:
			if(extend){
				return PotionTypes.long_poison;
			} else {
				return upgrade ? PotionTypes.strong_poison : PotionTypes.poison;
			}
		case STRENGTH:
			if(extend){
				return PotionTypes.long_strength;
			} else {
				return upgrade ? PotionTypes.strong_strength : PotionTypes.strength;
			}
		case WEAKNESS:
			if(extend){
				return PotionTypes.long_weakness;
			} else {
				return PotionTypes.weakness;
			}
		case SLOWNESS:
			if(extend){
				return PotionTypes.long_slowness;
			} else {
				return PotionTypes.slowness;
			}
		case SWIFTNESS:
			if(extend){
				return PotionTypes.long_swiftness;
			} else {
				return upgrade ? PotionTypes.strong_swiftness : PotionTypes.swiftness;
			}
		case FIRERESIST:
			if(extend){
				return PotionTypes.long_fire_resistance;
			} else {
				return PotionTypes.fire_resistance;
			} 
		default: return PotionTypes.healing;
		}
	}
}
