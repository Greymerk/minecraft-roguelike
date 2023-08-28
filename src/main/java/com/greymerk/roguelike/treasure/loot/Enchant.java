package com.greymerk.roguelike.treasure.loot;

import java.util.List;
import net.minecraft.util.math.random.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.Difficulty;

public enum Enchant {

	SHARPNESS, SMITE, ARTHOPODS, LOOTING, KNOCKBACK, FIREASPECT,
	AQUAAFFINITY, RESPIRATION, FEATHERFALLING, DEPTHSTRIDER,
	PROTECTION, BLASTPROTECTION, FIREPROTECTION, PROJECTILEPROTECTION, 
	THORNS, UNBREAKING, EFFICIENCY, SILKTOUCH, FORTUNE,
	POWER, PUNCH, FLAME, INFINITY, LURE, LUCKOFTHESEA, MENDING;
	
	public static Enchantment getEnchant(Enchant type){
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
	
	public static int getLevel(Random rand, int level) {

		switch(level){
		case 4: return 30 + rand.nextInt(10);
		case 3: return 15 + rand.nextInt(15);
		case 2: return 5 + rand.nextInt(15);
		case 1: return 1 + rand.nextInt(10);
		case 0: return 1 + rand.nextInt(5);
		default: return 1;
		}
	}

	public static boolean canEnchant(Difficulty difficulty, Random rand, int level){
		
		if(difficulty == null) difficulty = Difficulty.NORMAL;
		
		switch(difficulty){
		case PEACEFUL: return false;
		case EASY: return rand.nextInt(6) == 0;
		case NORMAL: return level >= 1 && rand.nextInt(4) == 0;
		case HARD: return rand.nextBoolean();
		}
		
		return false;
	}

	public static ItemStack enchantItem(Random rand, ItemStack item, int enchantLevel) {

		if (item == null ) return null;
		
		List<EnchantmentLevelEntry> enchants = null;
		try{
			enchants = EnchantmentHelper.generateEnchantments(rand, item, enchantLevel, false);
		} catch(NullPointerException e){
			throw e;
		}
		
		boolean isBook = item.getItem() == Items.BOOK;

		if (isBook){
			item = new ItemStack(Items.ENCHANTED_BOOK);
			if(enchants.size() > 1){
				enchants.remove(rand.nextInt(enchants.size()));
			}
		}

		for (EnchantmentLevelEntry toAdd : enchants){
			if (isBook){
				EnchantedBookItem.addEnchantment(item, toAdd);
			} else {
				item.addEnchantment(toAdd.enchantment, toAdd.level);
			}
		}
		
		return item;
	}
}
