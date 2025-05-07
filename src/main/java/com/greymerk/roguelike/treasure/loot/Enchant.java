package com.greymerk.roguelike.treasure.loot;

import java.util.List;

import com.greymerk.roguelike.dungeon.Difficulty;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public enum Enchant {

	SHARPNESS, SMITE, ARTHOPODS, LOOTING, KNOCKBACK, FIREASPECT,
	AQUAAFFINITY, RESPIRATION, FEATHERFALLING, DEPTHSTRIDER,
	PROTECTION, BLASTPROTECTION, FIREPROTECTION, PROJECTILEPROTECTION, 
	THORNS, UNBREAKING, EFFICIENCY, SILKTOUCH, FORTUNE,
	POWER, PUNCH, FLAME, INFINITY, LURE, LUCKOFTHESEA, MENDING;
	
	public static Enchantment getEnchant(DynamicRegistryManager reg, Enchant type){
		String ns = "minecraft";
		String path = getName(type);
		Identifier id = new Identifier(ns, path);
		return Registries.ENCHANTMENT.get(id);
	}
	
	public static String getName(Enchant type){
		switch(type){
			case SHARPNESS: return "sharpness";
			case SMITE: return "smite";
			case ARTHOPODS: return "bane_of_arthropods";
			case LOOTING: return "looting";
			case KNOCKBACK: return "knockback";
			case FIREASPECT: return "fire_aspect";
			case AQUAAFFINITY: return "aqua_affinity";
			case RESPIRATION: return "respiration";
			case FEATHERFALLING: return "feather_falling";
			case DEPTHSTRIDER: return "depth_strider";
			case PROTECTION: return "protection";
			case BLASTPROTECTION: return "blast_protection";
			case FIREPROTECTION: return "fire_protection";
			case PROJECTILEPROTECTION: return "projectile_protection";
			case THORNS: return "thorns";
			case UNBREAKING: return "unbreaking";
			case EFFICIENCY: return "efficiency";
			case SILKTOUCH: return "silk_touch";
			case FORTUNE: return "fortune";
			case POWER: return "power";
			case PUNCH: return "punch";
			case FLAME: return "flame";
			case INFINITY: return "infinity";
			case LURE: return "lure";
			case LUCKOFTHESEA: return "luck_of_the_sea";
			case MENDING: return "mending";
			default: return "efficiency";
		}
	}
	
	private static int getLevel(Random rand, Difficulty diff) {

		switch(diff){
		case HARDEST: return 30 + rand.nextInt(10);
		case HARD: return 15 + rand.nextInt(15);
		case MEDIUM: return 5 + rand.nextInt(15);
		case EASY: return 1 + rand.nextInt(10);
		case EASIEST: return 1 + rand.nextInt(5);
		default: return 1;
		}
	}

	public static ItemStack enchantItem(FeatureSet features, Random rand, ItemStack item, Difficulty diff) {
		return enchantItem(features, rand, item, getLevel(rand, diff));
	}
	
	public static ItemStack enchantItem(FeatureSet features, Random rand, ItemStack item, int enchantLevel) {

		if (item == null ) return null;
		
		List<EnchantmentLevelEntry> enchants = null;
		try{
			enchants = EnchantmentHelper.generateEnchantments(rand, item, enchantLevel, false);
		} catch(NullPointerException e){
			throw e;
		}

		if (item.isOf(Items.BOOK)){
			item = new ItemStack(Items.ENCHANTED_BOOK);
			if(enchants.size() > 1){
				enchants.remove(rand.nextInt(enchants.size()));
			}
		}

		for (EnchantmentLevelEntry toAdd : enchants){
			item.addEnchantment(toAdd.enchantment, toAdd.level);
		}
		
		return item;
	}
	
	public static ItemStack getBook(FeatureSet features, Random rand, Difficulty diff) {
		ItemStack book = new ItemStack(Items.BOOK);
		int level = getLevel(rand, diff);
		return EnchantmentHelper.enchant(rand, book, level, true);
	}
	
	public static ItemStack getBook(Enchant type, int rank) {
		Enchantment e = getEnchant(null, type);
		ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentLevelEntry ench = new EnchantmentLevelEntry(e, rank);
		EnchantedBookItem.addEnchantment(book, ench);
		return book;
	}
}
