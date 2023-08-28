package com.greymerk.roguelike.treasure.loot.provider;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.treasure.loot.Slot;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

public class ItemArmour extends ItemBase {

	private Equipment type;
	private boolean enchant;
	private Quality quality;
	
	public ItemArmour(int weight, int level) {
		super(weight, level);
	}	
	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(type != null || quality != null) return get(rand, level, quality, type, enchant);
		return getRandom(rand, level, true);
	}

	public static ItemStack get(Random rand, int level, Quality quality, Equipment type, boolean enchant){
		ItemStack tool = Equipment.get(type, quality == null ? Quality.get(level) : quality);
		return enchant ? Enchant.enchantItem(rand, tool, Enchant.getLevel(rand, level)) : tool;
	}
	
	public static ItemStack getRandom(Random rand, int level, boolean enchant){
		return getRandom(rand, level,
				Slot.getSlotByNumber(rand.nextInt(4) + 1),
				enchant ? Enchant.getLevel(rand, level) : 0);
	}
	
	public static ItemStack getRandom(Random rand, int level, Slot slot, boolean enchant){
		return getRandom(rand, level, slot, enchant ? Enchant.getLevel(rand, level) : 0);
	}
	
	@SuppressWarnings("incomplete-switch")
	public static ItemStack getRandom(Random rand, int level, Slot slot, int enchantLevel){

		if(enchantLevel > 0 && rand.nextInt(20 + (level * 10)) == 0){
			switch(slot){
			case HEAD: return ItemSpecialty.getRandomItem(Equipment.HELMET, rand, level); 
			case CHEST: return ItemSpecialty.getRandomItem(Equipment.CHEST, rand, level); 
			case LEGS: return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, level); 
			case FEET: return ItemSpecialty.getRandomItem(Equipment.FEET, rand, level);
			}
		}

		ItemStack item = get(rand, slot, Quality.getArmourQuality(rand, level));
		
		if(enchantLevel > 0) Enchant.enchantItem(rand, item, enchantLevel);
		
		return item;
		
	}
	
	@SuppressWarnings("incomplete-switch")
	public static ItemStack get(Random rand, Slot slot, Quality quality) {
		
		switch(slot){
		
		case HEAD:
			switch (quality) {

			case DIAMOND: return new ItemStack(Items.DIAMOND_HELMET);
			case GOLD: return new ItemStack(Items.GOLDEN_HELMET);
			case IRON: return new ItemStack(Items.IRON_HELMET);
			case STONE: return new ItemStack(Items.CHAINMAIL_HELMET);
			default:
				ItemStack item = new ItemStack(Items.LEATHER_HELMET);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case FEET:
			switch (quality) {

			case DIAMOND: return new ItemStack(Items.DIAMOND_BOOTS);
			case GOLD: return new ItemStack(Items.GOLDEN_BOOTS);
			case IRON: return new ItemStack(Items.IRON_BOOTS);
			case STONE: return new ItemStack(Items.CHAINMAIL_BOOTS);
			default:
				ItemStack item = new ItemStack(Items.LEATHER_BOOTS);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case CHEST:
			switch (quality) {

			case DIAMOND: return new ItemStack(Items.DIAMOND_CHESTPLATE);
			case GOLD: return new ItemStack(Items.GOLDEN_CHESTPLATE);
			case IRON: return new ItemStack(Items.IRON_CHESTPLATE);
			case STONE: return new ItemStack(Items.CHAINMAIL_CHESTPLATE);
			default:
				ItemStack item = new ItemStack(Items.LEATHER_CHESTPLATE);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		case LEGS:
			switch (quality) {
	
			case DIAMOND: return new ItemStack(Items.DIAMOND_LEGGINGS);
			case GOLD: return new ItemStack(Items.GOLDEN_LEGGINGS);
			case IRON: return new ItemStack(Items.IRON_LEGGINGS);
			case STONE: return new ItemStack(Items.CHAINMAIL_LEGGINGS);
			default:
				ItemStack item = new ItemStack(Items.LEATHER_LEGGINGS);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		}
		return null;
	}
	
	public static ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
		
		int color = r << 16 | g << 8 | b << 0;;
        
        NbtCompound nbtdata = armor.getNbt();

        if (nbtdata == null)
        {
            nbtdata = new NbtCompound();
            armor.setNbt(nbtdata);
        }

        NbtCompound nbtDisplay = nbtdata.getCompound("display");

        if (!nbtdata.contains("display"))
        {
            nbtdata.put("display", nbtDisplay);
        }

        nbtDisplay.putInt("color", color);
        
		return armor;
	}


}
