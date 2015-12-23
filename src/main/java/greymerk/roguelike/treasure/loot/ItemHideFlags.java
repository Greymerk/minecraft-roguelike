package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public enum ItemHideFlags {

	ENCHANTMENTS, ATTRIBUTES, UNBREAKABLE, CANDESTROY, CANPLACEON, EFFECTS;
	
	public static void set(ItemHideFlags[] flags, ItemStack item){
		
		int val = 0;
		
		for(ItemHideFlags flag : flags){
			val += get(flag);
		}
		
		NBTTagCompound nbt = item.getTagCompound();
		nbt.setInteger("HideFlags", val);
	}
	
	public static void set(ItemHideFlags flag, ItemStack item){
		set(new ItemHideFlags[]{flag}, item);
	}
	
	public static int get(ItemHideFlags flag){
		switch(flag){
		case ENCHANTMENTS: return 1;
		case ATTRIBUTES: return 2;
		case UNBREAKABLE: return 4;
		case CANDESTROY: return 8;
		case CANPLACEON: return 16;
		case EFFECTS: return 32;
		default: return 0;
		}
	}
	
	
	
}
