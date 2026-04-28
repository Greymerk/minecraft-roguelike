package com.greymerk.roguelike.treasure.loot.trim;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;

public class Trim {

    
	public static ItemStack addRandom(RegistryAccess registry, ItemStack item, RandomSource rand) {
	    return set(registry, item, com.greymerk.roguelike.treasure.loot.trim.TrimPattern.getRandom(rand), com.greymerk.roguelike.treasure.loot.trim.TrimMaterial.getRandom(rand));
	}
	
	public static ItemStack set(RegistryAccess registry, ItemStack item, com.greymerk.roguelike.treasure.loot.trim.TrimPattern pat, com.greymerk.roguelike.treasure.loot.trim.TrimMaterial mat) {
		
		Holder<TrimPattern> pattern = com.greymerk.roguelike.treasure.loot.trim.TrimPattern.getEntry(registry, pat);
	    Holder<TrimMaterial> material = com.greymerk.roguelike.treasure.loot.trim.TrimMaterial.getEntry(registry, mat);
	    
	    ArmorTrim at = new ArmorTrim(material, pattern);
	    
	    item.set(DataComponents.TRIM, at);
	    
	    return item;
	}
	
}