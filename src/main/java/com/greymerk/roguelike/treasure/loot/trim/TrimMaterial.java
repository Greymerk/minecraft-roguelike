package com.greymerk.roguelike.treasure.loot.trim;

import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public enum TrimMaterial {

	AMETHYST, COPPER, DIAMOND, EMERALD, GOLD, IRON, LAPIS, QUARTZ, NETHERITE, REDSTONE;
	
	public static NbtString getNbt(TrimMaterial material) {
		String path = getId(material);
		Identifier id = Identifier.of("minecraft", path);
		return NbtString.of(id.toString());
	}
	
	public static RegistryEntry<ArmorTrimMaterial> getEntry(DynamicRegistryManager registry, Random rand){
		TrimMaterial choice = getRandom(rand);
		return getEntry(registry, choice);
	}
	
	public static RegistryEntry<ArmorTrimMaterial> getEntry(DynamicRegistryManager registry, TrimMaterial mat){
		Registry<ArmorTrimMaterial> materials = registry.get(RegistryKeys.TRIM_MATERIAL);
		RegistryKey<ArmorTrimMaterial> key = getRegistryKey(mat);
		return materials.getEntry(key).get();
	}
	
	public static RegistryKey<ArmorTrimMaterial> getRegistryKey(TrimMaterial mat) {
		switch(mat) {
		case AMETHYST: return ArmorTrimMaterials.AMETHYST;
		case COPPER: return ArmorTrimMaterials.COPPER;
		case DIAMOND: return ArmorTrimMaterials.DIAMOND;
		case EMERALD: return ArmorTrimMaterials.EMERALD;
		case GOLD: return ArmorTrimMaterials.GOLD;
		case IRON: return ArmorTrimMaterials.IRON;
		case LAPIS: return ArmorTrimMaterials.LAPIS;
		case QUARTZ: return ArmorTrimMaterials.NETHERITE;
		case REDSTONE: return ArmorTrimMaterials.REDSTONE;
		default: return ArmorTrimMaterials.QUARTZ;
		}
	}
	
	public static String getId(TrimMaterial material) {
		switch(material) {
		case AMETHYST: return "amethyst";
		case COPPER: return "copper";
		case DIAMOND: return "diamond";
		case EMERALD: return "emerald";
		case GOLD: return "gold";
		case IRON: return "iron";
		case LAPIS: return "lapis";
		case NETHERITE: return "netherite";
		case QUARTZ: return "quartz";
		case REDSTONE: return "redstone";
		default: return "diamond";
		}	
	}
	
	public static Item getItem(TrimMaterial material) {
		switch(material) {
		case AMETHYST: return Items.AMETHYST_SHARD;
		case COPPER: return Items.COPPER_INGOT;
		case DIAMOND: return Items.DIAMOND;
		case EMERALD: return Items.EMERALD;
		case GOLD: return Items.GOLD_INGOT;
		case IRON: return Items.IRON_INGOT;
		case LAPIS: return Items.LAPIS_LAZULI;
		case NETHERITE: return Items.NETHERITE_INGOT;
		case QUARTZ: return Items.QUARTZ;
		case REDSTONE: return Items.REDSTONE;
		default: return Items.AMETHYST_SHARD;
		}		
	}
	
	public static TrimMaterial getRandom(Random rand) {
		List<TrimMaterial> mats = Arrays.asList(TrimMaterial.values());
		RandHelper.shuffle(mats, rand);
		return mats.get(0);
	}
}
