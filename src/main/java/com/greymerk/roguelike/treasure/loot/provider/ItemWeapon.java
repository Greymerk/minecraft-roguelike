package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.Quality;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemWeapon extends ItemBase{
	
	RegistryAccess reg;
	FeatureFlagSet features;
	
	public ItemWeapon(RegistryAccess reg, FeatureFlagSet features, int weight, Difficulty diff) {
		super(weight, diff);
		this.features = features;
		this.reg = reg;
	}

	
	@Override
	public ItemStack getLootItem(RandomSource rand, Difficulty diff) {
		if(rand.nextInt(1000) == 0) return ItemNovelty.getItem(reg, ItemNovelty.GREYMERK);
		return getRandom(this.reg, this.features, rand, diff, true);
	}

	public static ItemStack getRandom(RegistryAccess reg, FeatureFlagSet features, RandomSource rand, Difficulty diff, boolean enchant){
		if(rand.nextInt(10) == 0){
			return ItemWeapon.getBow(reg, features, rand, diff, enchant);
		} else {
			return ItemWeapon.getSword(reg, features, rand, diff, enchant);
		}
	}
	
	public static ItemStack getBow(RegistryAccess reg, FeatureFlagSet features, RandomSource rand, Difficulty diff, boolean enchant){
		
		if(enchant && rand.nextInt(1000) == 0) return ItemNovelty.getItem(reg, ItemNovelty.WINDFORCE);
		
		if(enchant && rand.nextInt(30) == 0){
			return ItemSpecialty.getRandomItem(reg, Equipment.BOW, rand, diff);
		}
		
		ItemStack bow = new ItemStack(Items.BOW);
		if(enchant)Enchant.enchantItem(reg, rand, bow, diff);
		return bow;
	}
	
	public static ItemStack getSword(RegistryAccess reg, FeatureFlagSet features, RandomSource rand, Difficulty diff, boolean enchant){
		
		if(enchant && rand.nextInt(1000) == 0) return ItemNovelty.getItem(reg, ItemNovelty.NULL);
		
		if(enchant && rand.nextInt(30) == 0){
			return ItemSpecialty.getRandomItem(reg, Equipment.SWORD, rand, diff);
		}
		
		ItemStack sword = pickSword(rand, diff);
		
		if(enchant) Enchant.enchantItem(reg, rand, sword, diff);
		return sword;		
	}
	
	public static ItemStack getSword(RegistryAccess reg, FeatureFlagSet features, RandomSource rand, Difficulty diff, boolean enchant, Quality quality){
		ItemStack sword = quality != null ? getSwordByQuality(quality) : pickSword(rand, diff);
		return enchant ? Enchant.enchantItem(reg, rand, sword, diff) : sword;
	}
	
	private static ItemStack pickSword(RandomSource rand, Difficulty diff){
		Quality quality = Quality.getWeaponQuality(rand, diff);
		return getSwordByQuality(quality);
	}
	
	private static ItemStack getSwordByQuality(Quality quality){
		switch (quality) {
		case NETHERITE: return new ItemStack(Items.NETHERITE_SWORD);
		case DIAMOND: return new ItemStack(Items.DIAMOND_SWORD);
		case GOLD: return new ItemStack(Items.GOLDEN_SWORD);
		case IRON: return new ItemStack(Items.IRON_SWORD);
		case COPPER: return new ItemStack(Items.COPPER_SWORD);
		case STONE: return new ItemStack(Items.STONE_SWORD);
		default: return new ItemStack(Items.WOODEN_SWORD);
		}
	}




	
}
