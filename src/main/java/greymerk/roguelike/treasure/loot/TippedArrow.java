package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TippedArrow {

	public static ItemStack get(Random rand){
		Potion type = Potion.values()[rand.nextInt(Potion.values().length)];
		return get(type);
	}

	public static ItemStack get(Potion type){
		return get(type, 1);
	}
	
	public static ItemStack get(Potion type, int amount){
		
		net.minecraft.potion.PotionType pot = Potion.getEffect(type, false, false);
		String id = net.minecraft.potion.PotionType.potionTypeRegistry.getNameForObject(pot).toString();
		
		ItemStack arrow = new ItemStack(Items.tipped_arrow, amount);
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Potion", id);
		
		arrow.setTagCompound(nbt);
        
		return arrow;
	}
	
	
	
}
