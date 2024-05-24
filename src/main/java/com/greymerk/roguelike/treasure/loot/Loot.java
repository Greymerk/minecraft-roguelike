package com.greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;
import com.greymerk.roguelike.treasure.loot.provider.ItemArmour;
import com.greymerk.roguelike.treasure.loot.provider.ItemBlock;
import com.greymerk.roguelike.treasure.loot.provider.ItemBrewing;
import com.greymerk.roguelike.treasure.loot.provider.ItemEnchanting;
import com.greymerk.roguelike.treasure.loot.provider.ItemFood;
import com.greymerk.roguelike.treasure.loot.provider.ItemJunk;
import com.greymerk.roguelike.treasure.loot.provider.ItemMusic;
import com.greymerk.roguelike.treasure.loot.provider.ItemOre;
import com.greymerk.roguelike.treasure.loot.provider.ItemPotion;
import com.greymerk.roguelike.treasure.loot.provider.ItemPrecious;
import com.greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import com.greymerk.roguelike.treasure.loot.provider.ItemSupply;
import com.greymerk.roguelike.treasure.loot.provider.ItemTool;
import com.greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import com.greymerk.roguelike.treasure.loot.rules.RoguelikeLootRules;
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
import net.minecraft.util.Rarity;
import net.minecraft.util.math.random.Random;

public enum Loot {
	
	WEAPON, ARMOUR, BLOCK, JUNK, ORE, TOOL, POTION, FOOD,
	ENCHANTING, SUPPLY, MUSIC, SPECIAL, BREWING, PRECIOUS;
	
	public static ILoot getLoot(FeatureSet features, DynamicRegistryManager reg){
		
		LootProvider loot = new LootProvider();
		for(int i = 0; i < 5; ++i){
			loot.put(i, new LootSettings(i, features, reg));
		}
		
		return loot;
	}
	
	public static void fillChest(IWorldEditor editor, ITreasureChest chest, Random rand) {
		RoguelikeLootRules.getLoot(editor.getFeatureSet(), editor.getRegistryManager()).process(rand, chest);
	}
	
	public static ItemStack getLootItem(IWorldEditor editor, Loot type, Random rand, int level) {
		return getProvider(type, level, editor.getFeatureSet(), editor.getRegistryManager()).get(rand);
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
		case ENCHANTING: return new ItemEnchanting(0, level);
		case SUPPLY: return new ItemSupply(0, level);
		case MUSIC: return new ItemMusic(0, level);
		case SPECIAL: return new ItemSpecialty(0, level);
		case PRECIOUS: return new ItemPrecious(0, level);
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

	public static void setRarity(ItemStack item, Rarity type) {
		switch(type) {
		case COMMON: item.set(DataComponentTypes.RARITY, Rarity.COMMON); return;
		case UNCOMMON: item.set(DataComponentTypes.RARITY, Rarity.UNCOMMON); return;
		case RARE: item.set(DataComponentTypes.RARITY, Rarity.RARE); return;
		case EPIC: item.set(DataComponentTypes.RARITY, Rarity.EPIC); return;
		default: 
		}
		
	}
	
	public static void setItemLore(ItemStack item, String loreText){
		List<Text> lines = new ArrayList<Text>();
		lines.add(Text.of(loreText));
		LoreComponent lore = new LoreComponent(lines);
		item.set(DataComponentTypes.LORE, lore);
	}
	
	public static void setItemLore(ItemStack item, String loreText, TextFormat option){
		setItemLore(item, TextFormat.apply(loreText, option).getString());
	}
		
	public static void setItemName(ItemStack item, String name){
		item.set(DataComponentTypes.CUSTOM_NAME, Text.literal(name));
	}
}
