package com.greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Difficulty;
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
	
	public static ILoot getLoot(IWorldEditor editor){
		
		LootProvider loot = new LootProvider();
		List.of(Difficulty.values()).forEach(diff -> {
			loot.put(diff, new LootSettings(diff, editor));
		});
		
		return loot;
	}
	
	public static void fillChest(IWorldEditor editor, ITreasureChest chest, Random rand) {
		RoguelikeLootRules.getLoot(editor).process(rand, chest);
	}
	
	public static ItemStack getLootItem(IWorldEditor editor, Loot type, Random rand, Difficulty diff) {
		return getProvider(type, diff, editor).get(rand);
	}
	
	public static IWeighted<ItemStack> getProvider(Loot type, Difficulty diff, IWorldEditor editor){
		FeatureSet features = editor.getFeatureSet();
		DynamicRegistryManager reg = editor.getRegistryManager();
		
		switch(type){
		case WEAPON: return new ItemWeapon(features, 0, diff);
		case ARMOUR: return new ItemArmour(0, diff, features, reg);
		case BLOCK: return new ItemBlock(0, diff);
		case JUNK: return new ItemJunk(0, diff);
		case ORE: return new ItemOre(0, diff);
		case TOOL: return new ItemTool(features, 0, diff);
		case POTION: return new ItemPotion(0, diff);
		case BREWING: return new ItemBrewing(0, diff);
		case FOOD: return new ItemFood(0, diff);
		case ENCHANTING: return new ItemEnchanting(0, diff);
		case SUPPLY: return new ItemSupply(0, diff);
		case MUSIC: return new ItemMusic(0, diff);
		case SPECIAL: return new ItemSpecialty(0, diff);
		case PRECIOUS: return new ItemPrecious(0, diff);
		}
		
		return new WeightedRandomLoot(Items.STICK, 1);
	}
	
	public static ItemStack getEquipmentBySlot(FeatureSet features, DynamicRegistryManager reg, Random rand, EquipmentSlot slot, Difficulty diff, boolean enchant){
		if(slot == EquipmentSlot.MAINHAND){
			return ItemWeapon.getRandom(features, rand, diff, enchant);
		}
		
		return ItemArmour.getRandom(features, reg, rand, diff, Slot.getSlot(slot), enchant);
	}
	
	public static ItemStack getEquipmentBySlot(FeatureSet features, DynamicRegistryManager reg, Random rand, Slot slot, Difficulty diff, boolean enchant){
		
		if(slot == Slot.WEAPON){
			return ItemWeapon.getRandom(features, rand, diff, enchant);
		}
		
		return ItemArmour.getRandom(features, reg, rand, diff, slot, enchant);
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
