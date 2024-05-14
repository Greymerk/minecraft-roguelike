package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Loot;
import com.greymerk.roguelike.util.IWeighted;
import com.greymerk.roguelike.util.TextFormat;
import com.greymerk.roguelike.util.WeightedChoice;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;

public enum ItemNovelty {

	GREYMERK, NULL, ZISTEAU, AVIDYA, ASHLEA, KURT, 
	CLEO, WINDFORCE, RLEAHY, VECHS, GENERIKB, FOURLES;
		
	public static IWeighted<ItemStack> get(ItemNovelty choice, int weight){
		return new WeightedChoice<ItemStack>(getItem(choice), weight);
	}
	
	public static ItemStack getItem(ItemNovelty choice){
		
		ItemStack item;
		
		switch(choice){
		
		case GREYMERK:
			item = new ItemStack(Items.IRON_AXE);
			Loot.setItemName(item, "Greymerk's Hatchet");
			Loot.setItemLore(item, "Pointlessly sharp", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
			item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 2);
			return item;
		case NULL:
			item = new ItemStack(Items.NETHERITE_SWORD);
			Loot.setItemName(item, "Null Pointer");
			Loot.setItemLore(item, "Exceptional", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
			item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
			item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
			return item;
		case ZISTEAU:
			item = new ItemStack(Items.OAK_SIGN);
			Loot.setItemName(item, "Battle Sign");
			Loot.setItemLore(item, "\"That's what you get!\"", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
			item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 3);
			item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
			return item;
		case AVIDYA:
			item = new ItemStack(Items.MILK_BUCKET);
			Loot.setItemName(item, "White Russian");
			Loot.setItemLore(item, "The dude's favourite", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			return item;
		case ASHLEA:
			item = new ItemStack(Items.COOKIE);
			Loot.setItemName(item, "Oatmeal Cookie");
			Loot.setItemLore(item, "Perfect for elevensies", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			return item;
		case KURT:
			item = new ItemStack(Items.LEATHER_BOOTS);
			Loot.setItemName(item, "Farland Travellers");
			Loot.setItemLore(item, "Indeed!", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), 3);
			item.addEnchantment(Enchant.getEnchant(Enchant.FEATHERFALLING), 2);
			item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			ItemArmour.dyeArmor(item, 165, 42, 42);
			return item;
		case CLEO:
			item = new ItemStack(Items.COD);
			Loot.setItemName(item, "Digging Feesh");
			Loot.setItemLore(item, "Feesh are not efeeshent for digging", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 10);
			return item;
		case RLEAHY:
			item = new ItemStack(Items.BREAD);
			Loot.setItemName(item, "Battle Sub");
			Loot.setItemLore(item, "With extra pastrami", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 1);
			item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
			item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
			return item;
		case WINDFORCE:
			item = new ItemStack(Items.BOW);
			Loot.setItemName(item, "Windforce");
			Loot.setItemLore(item, "Found on many battlefields", TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.POWER), 5);
			item.addEnchantment(Enchant.getEnchant(Enchant.PUNCH), 2);
			item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
			item.addEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
			item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
			return item;
		case VECHS:
			item = new ItemStack(Items.STICK);
			Loot.setItemName(item, "Legendary Stick");
			Loot.setItemLore(item, "\"Really?!\"",  TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1);
			return item;
		case GENERIKB:
			item = new ItemStack(Items.BAKED_POTATO);
			Loot.setItemName(item, "Hot Potato");
			Loot.setItemLore(item, "All a hermit needs",  TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
			return item;
		case FOURLES:
			item = new ItemStack(Items.COCOA_BEANS);
			Loot.setItemName(item, "Darkroast Beans");
			Loot.setItemLore(item, "\"Mmmm... Dark Roast\"",  TextFormat.DARKGREEN);
			Loot.setRarity(item, Rarity.EPIC);
			return item;
		default:
			return new ItemStack(Items.STICK);
		}
	}
}
