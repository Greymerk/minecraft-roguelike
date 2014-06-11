package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum Record{

	THIRTEEN, CAT, BLOCKS, CHIRP, FAR, MALL, MELLOHI,
	STAL, STRAD, WARD, ELEVEN, WAIT;
	
	public static ItemStack getRecord(Record type){
		return new ItemStack(getId(type), 1, 0);
	}
	
	public static ItemStack getRandomRecord(Random rand){
		return getRecord(Record.values()[rand.nextInt(Record.values().length)]);
	}
	
	public static Item getId(Record type){
		
		switch(type){
		case THIRTEEN: return Items.record_13;
		case CAT: return Items.record_cat;
		case BLOCKS: return Items.record_blocks;
		case CHIRP: return Items.record_chirp;
		case FAR: return Items.record_far;
		case MALL: return Items.record_mall;
		case MELLOHI: return Items.record_mellohi;
		case STAL: return Items.record_stal;
		case STRAD: return Items.record_strad;
		case WARD: return Items.record_ward;
		case ELEVEN: return Items.record_11;
		case WAIT: return Items.record_wait;
		default: return Items.record_cat;
		}
	}
	
}
