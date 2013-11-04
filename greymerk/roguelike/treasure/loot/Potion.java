package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public enum Potion {
	
	HEALING, HARM, REGEN, POISON, STRENGTH, WEAKNESS, SLOWNESS, SWIFTNESS, FIRERESIST;
	
	public static final int SPLASH = 16384;
	public static final int REGULAR = 8192;
	public static final int UPGRADE = 32;
	public static final int EXTEND = 64;
	
	public static ItemStack getRandom(Random rand, int rank){

		Potion type = Potion.values()[rand.nextInt(Potion.values().length)];
		return getSpecific(rand, rank, type);
	}
	
	public static ItemStack getSpecific(Random rand, int rank, Potion type){
		
		boolean splash = rand.nextBoolean();
		boolean upgrade = rand.nextInt(5 - rank) == 0;
		boolean extend = rand.nextInt(5 - rank) == 0;
		
		return getSpecific(rand, type, upgrade, extend, splash);
	}
	
	public static ItemStack getSpecific(Random rand, Potion type, boolean upgrade, boolean extend, boolean splash){
		
		int id;
		
		switch(type){
		
		
		case REGEN:
			id = 1;
			break;
		case SWIFTNESS:
			id = 2;
			break;
		case FIRERESIST:
			id = 3;
			break;
		case POISON:
			id = 4;
			break;
		case HEALING:
			id = 5;
			break;
		case WEAKNESS:
			id = 8;
			break;
		case STRENGTH:
			id = 9;
			break;
		case SLOWNESS:
			id = 10;
			break;
		case HARM:
			id = 12;
			break;
			
		default:
			id = 5;
		
		}
		
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
