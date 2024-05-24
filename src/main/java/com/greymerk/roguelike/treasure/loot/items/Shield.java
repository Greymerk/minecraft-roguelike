package com.greymerk.roguelike.treasure.loot.items;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.random.Random;

public class Shield {

	public static ItemStack get(DynamicRegistryManager reg, Random rand){
		
		Registry<BannerPattern> patterns = reg.get(RegistryKeys.BANNER_PATTERN);
		ItemStack shield = new ItemStack(Items.SHIELD); 
		
		BannerPatternsComponent component = Banner.createLayersComponent(patterns, rand, rand.nextInt(3) + 3);
		shield.set(DataComponentTypes.BANNER_PATTERNS, component);
		
		return shield;
	}
	

	

}
