package com.greymerk.roguelike.treasure.loot.trim;

import java.util.List;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.item.trim.ArmorTrimPatterns;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;

public class Trim {

    
	public static ItemStack addRandom(DynamicRegistryManager registry, ItemStack item, Random rand) {

		List<RegistryKey<ArmorTrimPattern>> PATTERNS = List.of(ArmorTrimPatterns.SENTRY, ArmorTrimPatterns.DUNE, ArmorTrimPatterns.COAST, ArmorTrimPatterns.WILD, ArmorTrimPatterns.WARD, ArmorTrimPatterns.EYE, ArmorTrimPatterns.VEX, ArmorTrimPatterns.TIDE, ArmorTrimPatterns.SNOUT, ArmorTrimPatterns.RIB, ArmorTrimPatterns.SPIRE, ArmorTrimPatterns.WAYFINDER, ArmorTrimPatterns.SHAPER, ArmorTrimPatterns.SILENCE, ArmorTrimPatterns.RAISER, ArmorTrimPatterns.HOST);
	    List<RegistryKey<ArmorTrimMaterial>> MATERIALS = List.of(ArmorTrimMaterials.QUARTZ, ArmorTrimMaterials.IRON, ArmorTrimMaterials.NETHERITE, ArmorTrimMaterials.REDSTONE, ArmorTrimMaterials.COPPER, ArmorTrimMaterials.GOLD, ArmorTrimMaterials.EMERALD, ArmorTrimMaterials.DIAMOND, ArmorTrimMaterials.LAPIS, ArmorTrimMaterials.AMETHYST);
		
	    Registry<ArmorTrimPattern> patterns = registry.get(RegistryKeys.TRIM_PATTERN);
	    Registry<ArmorTrimMaterial> materials = registry.get(RegistryKeys.TRIM_MATERIAL);

	    int pk = rand.nextInt(PATTERNS.size());
	    int mk = rand.nextInt(MATERIALS.size());
	    
	    RegistryKey<ArmorTrimPattern> pkey = PATTERNS.get(pk);
	    RegistryKey<ArmorTrimMaterial> mkey = MATERIALS.get(mk);
	    
	    RegistryEntry<ArmorTrimPattern> pattern = patterns.getEntry(pkey).get();
	    RegistryEntry<ArmorTrimMaterial> material = materials.getEntry(mkey).get();
	    
	    ArmorTrim at = new ArmorTrim(material, pattern);
	    
	    item.set(DataComponentTypes.TRIM, at);
	    
	    return item;
	}
	
}