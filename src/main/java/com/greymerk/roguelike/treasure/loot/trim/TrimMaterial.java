package com.greymerk.roguelike.treasure.loot.trim;

import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public enum TrimMaterial {

	AMETHYST, COPPER, DIAMOND, EMERALD, GOLD, IRON, LAPIS, QUARTZ, NETHERITE, REDSTONE;
	
	public static NbtString getNbt(TrimMaterial material) {
		String path = getId(material);
		Identifier id = new Identifier("minecraft", path);
		return NbtString.of(id.toString());
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
