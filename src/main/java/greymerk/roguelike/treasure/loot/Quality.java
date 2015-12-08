package greymerk.roguelike.treasure.loot;

import java.util.Random;

public enum Quality{
	
	WOOD, STONE, IRON, GOLD, DIAMOND;
	
	public static Quality getArmourQuality(Random rand, int level) {

		switch(level){
		
		case 4:
			if(rand.nextInt(10) == 0) return DIAMOND;
			if(rand.nextInt(5) == 0) return GOLD;
			return IRON;
		case 3:
			if(rand.nextInt(30) == 0) return DIAMOND;
			if(rand.nextInt(10) == 0) return GOLD;
			return IRON;
		case 2:
			if(rand.nextInt(50) == 0) return DIAMOND;
			if(rand.nextInt(20) == 0) return GOLD;
			if(rand.nextInt(5) == 0) return IRON; 
			return STONE;
		case 1:
			if(rand.nextInt(100) == 0) return DIAMOND; 
			if(rand.nextInt(30) == 0) return GOLD; 
			if(rand.nextInt(10) == 0) return IRON;
			if(rand.nextInt(3) == 0) return STONE;
			return WOOD;
		case 0:
			if(rand.nextInt(200) == 0) return DIAMOND;
			if(rand.nextInt(100) == 0) return GOLD;
			if(rand.nextInt(20) == 0) return IRON;
			if(rand.nextInt(10) == 0) return STONE;
			return WOOD;
		default:
			return WOOD;
		}
	}

	public static Quality getWeaponQuality(Random rand, int level) {

		switch(level){
		
		case 4:
			if(rand.nextInt(10) == 0) return DIAMOND;
			if(rand.nextInt(3) == 0) return GOLD;
			return IRON;
		case 3:
			if(rand.nextInt(20) == 0) return DIAMOND;
			if(rand.nextInt(10) == 0) return GOLD;
			if(rand.nextInt(5) == 0) return STONE;
			return IRON;
		case 2:
			if(rand.nextInt(40) == 0) return DIAMOND;
			if(rand.nextInt(20) == 0) return GOLD;
			if(rand.nextInt(3) == 0) return STONE;
			return IRON;
		case 1:
			if(rand.nextInt(100) == 0) return DIAMOND;	
			if(rand.nextInt(50) == 0) return GOLD;
			if(rand.nextInt(10) == 0) return IRON;
			return STONE;
		case 0:
			if(rand.nextInt(1000) == 0)return DIAMOND;
			if(rand.nextInt(200) == 0)return GOLD;
			if(rand.nextInt(20) == 0)return IRON;
			if(rand.nextInt(5) == 0)return WOOD;
			return STONE;
		default:
			return WOOD;
		}
	}
	
	public static Quality getToolQuality(Random rand, int level) {

		switch(level){
		
		case 4:
			if(rand.nextInt(10) == 0) return DIAMOND;
			return IRON;
		case 3:
			if(rand.nextInt(50) == 0) return DIAMOND;
			if(rand.nextInt(10) == 0) return GOLD;
			if(rand.nextInt(5) == 0) return STONE;
			return IRON;
		case 2:
			if(rand.nextInt(100) == 0) return DIAMOND;
			if(rand.nextInt(20) == 0) return GOLD;
			if(rand.nextInt(10) == 0) return IRON;
			return STONE;
		case 1:
			if(rand.nextInt(200) == 0) return DIAMOND;
			if(rand.nextInt(20) == 0) return GOLD;			
			if(rand.nextInt(10) == 0) return IRON;
			return STONE;
		case 0:
			if(rand.nextInt(1000) == 0)	return DIAMOND;
			if(rand.nextInt(200) == 0) return GOLD;
			if(rand.nextInt(50) == 0) return IRON;
			if(rand.nextInt(3) == 0) return WOOD;
			return STONE;
		default:
			return WOOD;
		}
	}

	public static Quality getQuality(Random rand, int level, Equipment type) {
		
		switch(type){
		case SWORD:
		case BOW:
			return getWeaponQuality(rand, level);
		case HELMET:
		case CHEST:
		case LEGS:
		case FEET:
			return getArmourQuality(rand, level);
		case PICK:
		case AXE:
		case SHOVEL:
			return getToolQuality(rand, level);
		}
		return null;
	}
	
	public static Quality get(int level){
		switch(level){
		case 0: return Quality.WOOD;
		case 1: return Quality.STONE;
		case 2: return Quality.IRON;
		case 3: return Quality.GOLD;
		case 4: return Quality.DIAMOND;
		default: return Quality.WOOD;
		}
	}
	
	
}
