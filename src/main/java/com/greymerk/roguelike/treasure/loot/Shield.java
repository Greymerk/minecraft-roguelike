package com.greymerk.roguelike.treasure.loot;

import net.minecraft.util.math.random.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

public class Shield {

	public static ItemStack get(Random rand){
		
		ItemStack banner = Banner.get(rand);
		
		ItemStack shield = new ItemStack(Items.SHIELD); 
		
		applyBanner(banner, shield);
		
		return shield;
	}
	
	public static void applyBanner(ItemStack banner, ItemStack shield){
		
        NbtCompound bannerNBT = banner.getSubNbt("BlockEntityTag");
		NbtCompound shieldNBT = bannerNBT == null ? new NbtCompound() : bannerNBT.copy();
		
        //shieldNBT.setInteger("Base", 0);
        shield.setSubNbt("BlockEntityTag", shieldNBT);

	}
	
}
