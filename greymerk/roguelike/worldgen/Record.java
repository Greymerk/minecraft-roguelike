package greymerk.roguelike.worldgen;

import net.minecraft.src.ItemStack;

public enum Record {

	THIRTEEN, CAT, BLOCKS, CHIRP, FAR, MALL, MELLOHI,
	STAL, STRAD, WARD, ELEVEN, WAIT;
	
	public static ItemStack getRecord(Record type){
		return new ItemStack(getId(type), 1, 0);
	}
	
	public static int getId(Record type){
		
		int base = 2256;
		
		switch(type){
		case THIRTEEN: return base + 0;
		case CAT: return base + 1;
		case BLOCKS: return base + 2;
		case CHIRP: return base + 3;
		case FAR: return base + 4;
		case MALL: return base + 5;
		case MELLOHI: return base + 6;
		case STAL: return base + 7;
		case STRAD: return base + 8;
		case WARD: return base + 9;
		case ELEVEN: return base + 10;
		case WAIT: return base + 11;
		default: return base;
		}
	}
	
}
