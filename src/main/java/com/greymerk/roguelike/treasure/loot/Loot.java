package com.greymerk.roguelike.treasure.loot;

import java.util.List;

import com.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import com.greymerk.roguelike.treasure.loot.provider.ItemBlock;
import com.greymerk.roguelike.treasure.loot.provider.ItemBrewing;
import com.greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import com.greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import com.greymerk.roguelike.treasure.loot.provider.ItemFood;
import com.greymerk.roguelike.treasure.loot.provider.ItemJunk;
import com.greymerk.roguelike.treasure.loot.provider.ItemOre;
import com.greymerk.roguelike.treasure.loot.provider.ItemPotion;
import com.greymerk.roguelike.treasure.loot.provider.ItemRecord;
import com.greymerk.roguelike.treasure.loot.provider.ItemSmithy;
import com.greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import com.greymerk.roguelike.treasure.loot.provider.ItemSupply;
import com.greymerk.roguelike.treasure.loot.provider.ItemTool;
import com.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import com.greymerk.roguelike.util.IWeighted;
import com.greymerk.roguelike.util.TextFormat;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

public enum Loot {
	
	WEAPON, ARMOUR, BLOCK, JUNK, ORE, TOOL, POTION, FOOD, ENCHANTBOOK,
	ENCHANTBONUS, SUPPLY, MUSIC, SMITHY, SPECIAL, REWARD, BREWING;

	public static ILoot getLoot(FeatureSet features, DynamicRegistryManager reg){
		
		LootProvider loot = new LootProvider();
		for(int i = 0; i < 5; ++i){
			loot.put(i, new LootSettings(i, features, reg));
		}
		
		return loot;
	}
	
	
	public static IWeighted<ItemStack> getProvider(Loot type, int level, FeatureSet features, DynamicRegistryManager reg){
		switch(type){
		case WEAPON: return new ItemWeapon(features, 0, level);
		case ARMOUR: return new ItemArmour(0, level, features, reg);
		case BLOCK: return new ItemBlock(0, level);
		case JUNK: return new ItemJunk(0, level);
		case ORE: return new ItemOre(0, level);
		case TOOL: return new ItemTool(features, 0, level);
		case POTION: return new ItemPotion(0, level);
		case BREWING: return new ItemBrewing(0, level);
		case FOOD: return new ItemFood(0, level);
		case ENCHANTBOOK: return new ItemEnchBook(features, 0, level);
		case ENCHANTBONUS: return new ItemEnchBonus(0, level);
		case SUPPLY: return new ItemSupply(0, level);
		case MUSIC: return new ItemRecord(0, level);
		case SMITHY: return new ItemSmithy(0, level);
		case SPECIAL: return new ItemSpecialty(0, level);
		case REWARD:
		}
		
		return new WeightedRandomLoot(Items.STICK, 1);
	}
	
	public static ItemStack getEquipmentBySlot(FeatureSet features, DynamicRegistryManager reg, Random rand, EquipmentSlot slot, int level, boolean enchant){
		if(slot == EquipmentSlot.MAINHAND){
			return ItemWeapon.getRandom(features, rand, level, enchant);
		}
		
		return ItemArmour.getRandom(features, reg, rand, level, Slot.getSlot(slot), enchant);
	}
	
	public static ItemStack getEquipmentBySlot(FeatureSet features, DynamicRegistryManager reg, Random rand, Slot slot, int level, boolean enchant){
		
		if(slot == Slot.WEAPON){
			return ItemWeapon.getRandom(features, rand, level, enchant);
		}
		
		return ItemArmour.getRandom(features, reg, rand, level, slot, enchant);
	}

	public static void setItemLore(ItemStack item, String loreText){
		LoreComponent lore = new LoreComponent(List.of());
		lore.with(Text.of(loreText));
		item.set(DataComponentTypes.LORE, lore);  
	}
	
	public static void setItemLore(ItemStack item, String loreText, TextFormat option){
		setItemLore(item, TextFormat.apply(loreText, option).getString());
	}
	
	public static void setItemName(ItemStack item, String name, TextFormat option){
		
		if(option == null){
			item.set(DataComponentTypes.CUSTOM_NAME, Text.literal(name));
			return;
		}
		
		item.set(DataComponentTypes.CUSTOM_NAME, TextFormat.apply(name, option));
	}
	
	public static void setItemName(ItemStack item, String name){
		setItemName(item, name, null);
	}
}
