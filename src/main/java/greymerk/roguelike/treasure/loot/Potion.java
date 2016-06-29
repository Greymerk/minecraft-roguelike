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
		case REGULAR: potion = new ItemStack(Items.POTIONITEM); break;
		case SPLASH: potion = new ItemStack(Items.SPLASH_POTION); break;
		case LINGERING: potion = new ItemStack(Items.LINGERING_POTION); break;
		default: potion = new ItemStack(Items.POTIONITEM); break;
		}
		
		net.minecraft.potion.PotionType data = getEffect(effect, upgrade, extend);
		
		return PotionUtils.addPotionToItemStack(potion, data);
	}
	
	public static net.minecraft.potion.PotionType getEffect(Potion effect, boolean upgrade, boolean extend){
		
		if(effect == null) return PotionTypes.AWKWARD;
		
		switch(effect){
		case HEALING: return upgrade ? PotionTypes.STRONG_HEALING : PotionTypes.HEALING;
		case HARM: return upgrade ? PotionTypes.STRONG_HARMING : PotionTypes.HARMING;
		case REGEN:
			if(extend){
				return PotionTypes.LONG_REGENERATION;
			} else {
				return upgrade ? PotionTypes.STRONG_REGENERATION : PotionTypes.REGENERATION;
			}
		case POISON:
			if(extend){
				return PotionTypes.LONG_POISON;
			} else {
				return upgrade ? PotionTypes.STRONG_POISON : PotionTypes.POISON;
			}
		case STRENGTH:
			if(extend){
				return PotionTypes.LONG_STRENGTH;
			} else {
				return upgrade ? PotionTypes.STRONG_STRENGTH : PotionTypes.STRENGTH;
			}
		case WEAKNESS:
			if(extend){
				return PotionTypes.LONG_WEAKNESS;
			} else {
				return PotionTypes.WEAKNESS;
			}
		case SLOWNESS:
			if(extend){
				return PotionTypes.LONG_SLOWNESS;
			} else {
				return PotionTypes.SLOWNESS;
			}
		case SWIFTNESS:
			if(extend){
				return PotionTypes.LONG_SWIFTNESS;
			} else {
				return upgrade ? PotionTypes.STRONG_SWIFTNESS : PotionTypes.SWIFTNESS;
			}
		case FIRERESIST:
			if(extend){
				return PotionTypes.LONG_FIRE_RESISTANCE;
			} else {
				return PotionTypes.FIRE_RESISTANCE;
			} 
		default: return PotionTypes.AWKWARD;
		}
	}
}
