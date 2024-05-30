package com.greymerk.roguelike.treasure.loot.items;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;

public class Banner {

	public static ItemStack get(Random rand){
		
		ItemStack banner = new ItemStack(Items.BLACK_BANNER);
		int n = rand.nextInt(8) + 1;
		for(int i = 0; i < n; ++i){
			addPattern(banner, rand);
		}
		
		return banner;
	}
	
	public static ItemStack addPattern(ItemStack banner, Random rand){
		BannerPattern pattern = Registries.BANNER_PATTERN.getRandom(rand).get().value();
		DyeColor color = DyeColor.values()[rand.nextInt(DyeColor.values().length)];
		
		return addPattern(banner, pattern, color);
	}
	
	public static ItemStack addPattern(ItemStack banner, BannerPattern pattern, DyeColor color){
		
		NbtCompound nbt = banner.getNbt();
		if(nbt == null){
			banner.setNbt(new NbtCompound());
			nbt = banner.getNbt();
		}
		
		NbtCompound tag;
		
		if(nbt.contains("BlockEntityTag")){
			tag = nbt.getCompound("BlockEntityTag");
		} else {
			tag = new NbtCompound();
			nbt.put("BlockEntityTag", tag);
		}
		
		NbtList patterns;
		
		if(tag.contains("Patterns")){
			patterns = tag.getList("Patterns", 10);
		} else {
			patterns = new NbtList();
			tag.put("Patterns", patterns);
		}
				
		NbtCompound toAdd = new NbtCompound();
		toAdd.putInt("Color", color.getId());
		toAdd.putString("Pattern", pattern.getId());
		patterns.add(toAdd);
		
		return banner;
	}
	
		
}