package com.greymerk.roguelike.treasure.loot.trim;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;

public class Trim {

    
	public static ItemStack addRandom(DynamicRegistryManager registry, ItemStack item, Random rand) {
	    return set(registry, item, TrimPattern.getRandom(rand), TrimMaterial.getRandom(rand));
	}
	
	public static ItemStack set(DynamicRegistryManager registry, ItemStack item, TrimPattern pat, TrimMaterial mat) {
		
		RegistryEntry<ArmorTrimPattern> pattern = TrimPattern.getEntry(registry, pat);
	    RegistryEntry<ArmorTrimMaterial> material = TrimMaterial.getEntry(registry, mat);
	    
	    ArmorTrim at = new ArmorTrim(material, pattern);
	    
	    item.set(DataComponentTypes.TRIM, at);
	    
	    return item;
	}
	
}