package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.Quality;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.math.random.Random;

public class ItemWeapon extends ItemBase{
	
	FeatureSet features;
	
	public ItemWeapon(FeatureSet features, int weight, int level) {
		super(weight, level);
		this.features = features;
	}

	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(rand.nextInt(100) == 0) return ItemNovelty.getItem(ItemNovelty.GREYMERK);
		return getRandom(this.features, rand, level, true);
	}

	public static ItemStack getRandom(FeatureSet features, Random rand, int rank, boolean enchant){
		if(rand.nextInt(10) == 0){
			return ItemWeapon.getBow(features, rand, rank, enchant);
		} else {
			return ItemWeapon.getSword(features, rand, rank, enchant);
		}
	}
	
	public static ItemStack getBow(FeatureSet features, Random rand, int level, boolean enchant){
		
		if(rand.nextInt(200) == 0) return ItemNovelty.getItem(ItemNovelty.WINDFORCE);
		
		if(rand.nextInt(30) == 0){
			return ItemSpecialty.getRandomItem(Equipment.BOW, rand, level);
		}
		
		ItemStack bow = new ItemStack(Items.BOW);
		
		if(enchant && rand.nextInt(6 - level) == 0){
			Enchant.enchantItem(features, rand, bow, Enchant.getLevel(rand, level));
		}
		
		return bow;
		
	}
	
	public static ItemStack getSword(FeatureSet features, Random rand, int level, boolean enchant){
		
		if(enchant && rand.nextInt(1000) == 0) return ItemNovelty.getItem(ItemNovelty.NULL);
		
		if(enchant && rand.nextInt(30) == 0){
			return ItemSpecialty.getRandomItem(Equipment.SWORD, rand, level);
		}
		
		ItemStack sword = pickSword(rand, level);
		
		if(enchant) Enchant.enchantItem(features, rand, sword, Enchant.getLevel(rand, level));
		return sword;		
	}
	
	public static ItemStack getSword(FeatureSet features, Random rand, int level, boolean enchant, Quality quality){
		ItemStack sword = quality != null ? getSwordByQuality(quality) : pickSword(rand, level);
		return enchant ? Enchant.enchantItem(features, rand, sword, Enchant.getLevel(rand, level)) : sword;
	}
	
	private static ItemStack pickSword(Random rand, int level){
		Quality quality = Quality.getWeaponQuality(rand, level);
		return getSwordByQuality(quality);
	}
	
	private static ItemStack getSwordByQuality(Quality quality){
		switch (quality) {
		case NETHERITE: return new ItemStack(Items.NETHERITE_SWORD);
		case DIAMOND: return new ItemStack(Items.DIAMOND_SWORD);
		case GOLD: return new ItemStack(Items.GOLDEN_SWORD);
		case IRON: return new ItemStack(Items.IRON_SWORD);
		case STONE: return new ItemStack(Items.STONE_SWORD);
		default: return new ItemStack(Items.WOODEN_SWORD);
		}
	}




	
}
