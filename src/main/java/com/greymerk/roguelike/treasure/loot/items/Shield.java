package com.greymerk.roguelike.treasure.loot.items;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public class Shield {

	public static ItemStack get(RegistryAccess reg, RandomSource rand){
		
		Registry<BannerPattern> patterns = reg.lookupOrThrow(Registries.BANNER_PATTERN);
		ItemStack shield = new ItemStack(Items.SHIELD); 
		
		BannerPatternLayers component = Banner.createLayersComponent(patterns, rand, rand.nextInt(3) + 3);
		shield.set(DataComponents.BANNER_PATTERNS, component);
		
		return shield;
	}
	

	

}
