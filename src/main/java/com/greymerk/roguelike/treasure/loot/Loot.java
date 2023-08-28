package com.greymerk.roguelike.treasure.loot;

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

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

public enum Loot {
	
	WEAPON, ARMOUR, BLOCK, JUNK, ORE, TOOL, POTION, FOOD, ENCHANTBOOK,
	ENCHANTBONUS, SUPPLY, MUSIC, SMITHY, SPECIAL, REWARD, BREWING;

	public static ILoot getLoot(){
		
		LootProvider loot = new LootProvider();
		for(int i = 0; i < 5; ++i){
			loot.put(i, new LootSettings(i));
		}
		
		return loot;
	}
	
	
	public static IWeighted<ItemStack> getProvider(Loot type, int level){
		switch(type){
		case WEAPON: return new ItemWeapon(0, level);
		case ARMOUR: return new ItemArmour(0, level);
		case BLOCK: return new ItemBlock(0, level);
		case JUNK: return new ItemJunk(0, level);
		case ORE: return new ItemOre(0, level);
		case TOOL: return new ItemTool(0, level);
		case POTION: return new ItemPotion(0, level);
		case BREWING: return new ItemBrewing(0, level);
		case FOOD: return new ItemFood(0, level);
		case ENCHANTBOOK: return new ItemEnchBook(0, level);
		case ENCHANTBONUS: return new ItemEnchBonus(0, level);
		case SUPPLY: return new ItemSupply(0, level);
		case MUSIC: return new ItemRecord(0, level);
		case SMITHY: return new ItemSmithy(0, level);
		case SPECIAL: return new ItemSpecialty(0, level);
		case REWARD:
		}
		
		return new WeightedRandomLoot(Items.STICK, 1);
	}
	
	public static ItemStack getEquipmentBySlot(Random rand, EquipmentSlot slot, int level, boolean enchant){
		if(slot == EquipmentSlot.MAINHAND){
			return ItemWeapon.getRandom(rand, level, enchant);
		}
		
		return ItemArmour.getRandom(rand, level, Slot.getSlot(slot), enchant);
	}
	
	public static ItemStack getEquipmentBySlot(Random rand, Slot slot, int level, boolean enchant){
		
		if(slot == Slot.WEAPON){
			return ItemWeapon.getRandom(rand, level, enchant);
		}
		
		return ItemArmour.getRandom(rand, level, slot, enchant);
	}

	public static void setItemLore(ItemStack item, String loreText){
		
		NbtCompound tag = item.getNbt(); 
		
		if (tag == null){
			tag = new NbtCompound();
			item.setNbt(tag);
		}

		if (!tag.contains("display")){
			tag.put("display", new NbtCompound());
		}
		
		NbtCompound display = tag.getCompound("display");
		
		if (!(display.contains("Lore")))
		{
			display.put("Lore", new NbtList());
		}
		
		NbtList lore = display.getList("Lore", 0);
		
		NbtString toAdd = NbtString.of(loreText);
		
		lore.add(toAdd);
		
		display.put("Lore", lore);   
	}
	
	public static void setItemLore(ItemStack item, String loreText, TextFormat option){
		setItemLore(item, TextFormat.apply(loreText, option).getString());
	}
	
	public static void setItemName(ItemStack item, String name, TextFormat option){
		
		if(option == null){
			Text n = Text.of(name);
			item.setCustomName(n);
			return;
		}
		
		item.setCustomName(TextFormat.apply(name, option));
	}
	
	public static void setItemName(ItemStack item, String name){
		setItemName(item, name, null);
	}
}
