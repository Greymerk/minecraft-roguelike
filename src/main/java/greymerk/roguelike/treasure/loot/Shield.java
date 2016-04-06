package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityBanner;

public class Shield {

	public static ItemStack get(Random rand){
		
		ItemStack banner = Banner.get(rand);
		
		ItemStack shield = new ItemStack(Items.shield, 1, 0); 
		
		applyBanner(banner, shield);
		
		return shield;
	}
	
	public static void applyBanner(ItemStack banner, ItemStack shield){
		
		NBTTagCompound patterns = (NBTTagCompound)banner.getTagCompound().copy();
		EnumDyeColor dye = EnumDyeColor.byDyeDamage(TileEntityBanner.getBaseColor(banner));
		
		shield.setTagCompound(patterns);
		TileEntityBanner.func_184248_a(shield, dye);
	}
	
}
