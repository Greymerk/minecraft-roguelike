package greymerk.roguelike.treasure.loot;

import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum Potion {
	
	HEALING, HARM, REGEN, POISON, STRENGTH, WEAKNESS, SLOWNESS, SWIFTNESS, FIRERESIST;
	
	public static final int SPLASH = 16384;
	public static final int REGULAR = 8192;
	public static final int UPGRADE = 32;
	public static final int EXTEND = 64;
	
	public static ItemStack getRandom(Random rand){

		if(rand.nextInt(200) == 0){
			return ItemNovelty.getItem(ItemNovelty.AVIDYA);
		}
		
		Potion effect = Potion.values()[rand.nextInt(Potion.values().length)];
		return getSpecific(rand, effect);
	}
	
	public static ItemStack getSpecific(Random rand, Potion effect){
		return getSpecific(rand, PotionType.REGULAR, effect, rand.nextBoolean(), rand.nextBoolean());
	}

	public static ItemStack getSpecific(Random rand, PotionType type, Potion effect, boolean upgrade, boolean extend){
		
		int id = getPotionID(effect);

		if(upgrade && !extend){
			id = upgrade(effect, id);
		}

		if(extend && !upgrade){
			id = extend(effect, id);
		}
		
		if(upgrade && extend){
			if(rand.nextBoolean()){
				id = upgrade(effect, id);
			} else {
				id = extend(effect, id);
			}
		}

		if(type == PotionType.SPLASH){
			id = id | SPLASH;
		} else {
			id = id | REGULAR;
		}
		
		return new ItemStack(Items.potionitem, 1, id);
		
	}
	
	public static int getPotionID(Potion type){
		switch(type){
		case REGEN: return 1;
		case SWIFTNESS: return 2;
		case FIRERESIST: return 3;
		case POISON: return 4;
		case HEALING: return 5;
		case WEAKNESS: return 8;
		case STRENGTH: return 9;
		case SLOWNESS: return 10;
		case HARM: return 12;
		default: return 0;
		}
	}
	
	private static int upgrade(Potion type, int id){
		
		if(type == FIRERESIST){
			return id;
		}
		
		id = id & 15;
		
		return id | UPGRADE;
	}
	
	private static int extend(Potion type, int id){
		
		if(type == HEALING || type == HARM){
			return id;
		}
		
		id = id & 15;
		
		return id | EXTEND;
	}
	

	
}