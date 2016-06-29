package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TippedArrow {

	public static ItemStack get(Random rand){	
		return get(rand, 1);
	}
	
	public static ItemStack get(Random rand, int amount){
		Potion type = Potion.values()[rand.nextInt(Potion.values().length)];
		return get(type, amount);
	}
		
	public static ItemStack get(Potion type){
		return get(type, 1);
	}
	
	public static ItemStack get(Potion type, int amount){
		
		net.minecraft.potion.PotionType pot = Potion.getEffect(type, false, false);
		String id = net.minecraft.potion.PotionType.REGISTRY.getNameForObject(pot).toString();
		
		ItemStack arrow = new ItemStack(Items.TIPPED_ARROW, amount);
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Potion", id);
		
		arrow.setTagCompound(nbt);
        
		return arrow;
	}
	
	public static ItemStack getHarmful(Random rand, int amount){
		switch(rand.nextInt(4)){
		case 0: return TippedArrow.get(Potion.HARM, amount);
		case 1: return TippedArrow.get(Potion.POISON, amount);
		case 2: return TippedArrow.get(Potion.SLOWNESS, amount);
		case 3: return TippedArrow.get(Potion.WEAKNESS, amount);
		default: return new ItemStack(Items.ARROW, amount);
		}
	}
	
	
	
}
