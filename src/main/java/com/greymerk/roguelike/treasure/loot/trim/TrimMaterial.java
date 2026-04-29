package com.greymerk.roguelike.treasure.loot.trim;

import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import com.greymerk.roguelike.util.math.RandHelper;

public enum TrimMaterial {

	AMETHYST, COPPER, DIAMOND, EMERALD, GOLD, IRON, 
	LAPIS, QUARTZ, NETHERITE, REDSTONE, RESIN;
	
	public static StringTag getNbt(TrimMaterial material) {
		String path = getId(material);
		Identifier id = Identifier.fromNamespaceAndPath("minecraft", path);
		return StringTag.valueOf(id.toString());
	}
	
	public static Holder<net.minecraft.world.item.equipment.trim.TrimMaterial> getEntry(RegistryAccess registry, RandomSource rand){
		TrimMaterial choice = getRandom(rand);
		return getEntry(registry, choice);
	}
	
	public static Holder<net.minecraft.world.item.equipment.trim.TrimMaterial> getEntry(RegistryAccess registry, TrimMaterial mat){
		Registry<net.minecraft.world.item.equipment.trim.TrimMaterial> materials = registry.lookupOrThrow(Registries.TRIM_MATERIAL);
		ResourceKey<net.minecraft.world.item.equipment.trim.TrimMaterial> key = getRegistryKey(mat);
		return materials.getOrThrow(key);
	}
	
	public static ResourceKey<net.minecraft.world.item.equipment.trim.TrimMaterial> getRegistryKey(TrimMaterial mat) {
		switch(mat) {
		case AMETHYST: return TrimMaterials.AMETHYST;
		case COPPER: return TrimMaterials.COPPER;
		case DIAMOND: return TrimMaterials.DIAMOND;
		case EMERALD: return TrimMaterials.EMERALD;
		case GOLD: return TrimMaterials.GOLD;
		case IRON: return TrimMaterials.IRON;
		case LAPIS: return TrimMaterials.LAPIS;
		case QUARTZ: return TrimMaterials.NETHERITE;
		case REDSTONE: return TrimMaterials.REDSTONE;
		case RESIN: return TrimMaterials.RESIN;
		default: return TrimMaterials.QUARTZ;
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
	
	public static TrimMaterial getRandom(RandomSource rand) {
		List<TrimMaterial> mats = Arrays.asList(TrimMaterial.values());
		RandHelper.shuffle(mats, rand);
		return mats.get(0);
	}
}
