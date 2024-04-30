package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.treasure.loot.Slot;
import com.greymerk.roguelike.treasure.loot.trim.Trim;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.math.random.Random;

public class ItemArmour extends ItemBase {

	private DynamicRegistryManager registry;
	private FeatureSet features;
	
	public ItemArmour(int weight, int level, FeatureSet features, DynamicRegistryManager reg) {
		super(weight, level);
		this.features = features;
		this.registry = reg;
	}	
	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return getRandom(this.features, this.registry, rand, level, true);
	}

	public static ItemStack getRandom(FeatureSet features, DynamicRegistryManager reg, Random rand, int level, boolean enchant){
		ItemStack item = getRandom(features, reg, rand, level,
				Slot.getSlotByNumber(rand.nextInt(4) + 1),
				enchant);
		return item;
	}
	
	public static ItemStack getRandom(FeatureSet features, DynamicRegistryManager reg, Random rand, int level, Slot slot, boolean enchant){
		if(enchant && rand.nextInt(20 + (level * 10)) == 0){
			switch(slot){
			case HEAD: return ItemSpecialty.getRandomItem(Equipment.HELMET, rand, level); 
			case CHEST: return ItemSpecialty.getRandomItem(Equipment.CHEST, rand, level); 
			case LEGS: return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, level); 
			case FEET: return ItemSpecialty.getRandomItem(Equipment.FEET, rand, level);
			default: return new ItemStack(Items.STICK);
			}
		}

		ItemStack item = get(rand, slot, Quality.getArmourQuality(rand, level));
		if(enchant) Enchant.enchantItem(features, rand, item, Enchant.getLevel(rand, level));
		Trim.addRandom(reg, item, rand);
		return item;
	}
	
	public static ItemStack get(Random rand, Slot slot, Quality quality) {
		
		switch(slot){
		
		case HEAD:
			switch (quality) {
			case NETHERITE: return new ItemStack(Items.NETHERITE_HELMET);
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

			case NETHERITE: return new ItemStack(Items.NETHERITE_BOOTS);
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

			case NETHERITE: return new ItemStack(Items.NETHERITE_CHESTPLATE);
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

			case NETHERITE: return new ItemStack(Items.NETHERITE_LEGGINGS);
			case DIAMOND: return new ItemStack(Items.DIAMOND_LEGGINGS);
			case GOLD: return new ItemStack(Items.GOLDEN_LEGGINGS);
			case IRON: return new ItemStack(Items.IRON_LEGGINGS);
			case STONE: return new ItemStack(Items.CHAINMAIL_LEGGINGS);
			default:
				ItemStack item = new ItemStack(Items.LEATHER_LEGGINGS);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		default: return new ItemStack(Items.LEATHER_CHESTPLATE);
		}
	}
	
	public static ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
		
		int color = r << 16 | g << 8 | b << 0;;
        
		DyedColorComponent dye = new DyedColorComponent(color, false);
		armor.set(DataComponentTypes.DYED_COLOR, dye);
		return armor;
	}


}
