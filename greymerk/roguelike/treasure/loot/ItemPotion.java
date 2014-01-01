package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public enum ItemPotion {
	
	HEALING, HARM, REGEN, POISON, STRENGTH, WEAKNESS, SLOWNESS, SWIFTNESS, FIRERESIST;
	
	public static final int SPLASH = 16384;
	public static final int REGULAR = 8192;
	public static final int UPGRADE = 32;
	public static final int EXTEND = 64;
	
	public static ItemStack getRandom(Random rand){

		if(rand.nextInt(200) == 0){
			return ItemNovelty.getItem(ItemNovelty.AVIDYA);
		}
		
		ItemPotion type = ItemPotion.values()[rand.nextInt(ItemPotion.values().length)];
		return getSpecific(rand, type);
	}
	
	public static ItemStack getSpecific(Random rand, ItemPotion type){
		return getSpecific(rand, type, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean());
	}

	public static ItemStack getSpecific(Random rand, ItemPotion type, boolean upgrade, boolean extend, boolean splash){
		
		int id = getPotionID(type);

		if(upgrade && !extend){
			id = upgrade(type, id);
		}

		if(extend && !upgrade){
			id = extend(type, id);
		}
		
		if(upgrade && extend){
			if(rand.nextBoolean()){
				id = upgrade(type, id);
			} else {
				id = extend(type, id);
			}
		}

		if(splash){
			id = id | SPLASH;
		} else {
			id = id | REGULAR;
		}
		
		return new ItemStack(Item.potion, 1, id);
		
	}
	
	public static int getPotionID(ItemPotion type){
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
	
	private static int upgrade(ItemPotion type, int id){
		
		if(type == FIRERESIST){
			return id;
		}
		
		id = id & 15;
		
		return id | UPGRADE;
	}
	
	private static int extend(ItemPotion type, int id){
		
		if(type == HEALING || type == HARM){
			return id;
		}
		
		id = id & 15;
		
		return id | EXTEND;
	}
	
	
}
