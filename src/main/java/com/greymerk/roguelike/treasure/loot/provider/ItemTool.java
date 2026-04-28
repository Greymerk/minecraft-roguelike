package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Quality;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemTool extends ItemBase {

	RegistryAccess reg;
	FeatureFlagSet features;
	
	public ItemTool(RegistryAccess reg, FeatureFlagSet features, int weight, Difficulty diff) {
		super(weight, diff);
		this.features = features;
		this.reg = reg;
	}
	
	@Override
	public ItemStack getLootItem(RandomSource rand, Difficulty diff) {
		if(rand.nextInt(2000) == 0) return ItemNovelty.getItem(reg, ItemNovelty.CLEO);
		return getRandom(reg, features, rand, diff, true);
	}
	
	public static ItemStack getRandom(RegistryAccess reg, FeatureFlagSet features, RandomSource rand, Difficulty diff, boolean enchant){
		
		if(enchant && rand.nextInt(30) == 0){
			return ItemSpecialty.getRandomTool(reg, rand, Quality.get(diff));
		}
		
		ItemStack tool = pickTool(rand, diff);
		if(enchant) Enchant.enchantItem(reg, rand, tool, diff);
		return tool;
	}
	
	private static ItemStack pickTool(RandomSource rand, Difficulty diff){
		
		switch(rand.nextInt(3)){
		case 0: return pickPick(rand, diff);
		case 1: return pickAxe(rand, diff);
		case 2: return pickShovel(rand, diff);		
		default: return pickPick(rand, diff);
		}
	}
	

	private static ItemStack pickAxe(RandomSource rand, Difficulty diff) {
		Quality quality = Quality.getToolQuality(rand, diff);
		switch (quality) {
		case NETHERITE: return new ItemStack(Items.NETHERITE_AXE);
		case DIAMOND: return new ItemStack(Items.DIAMOND_AXE);
		case GOLD: return new ItemStack(Items.GOLDEN_AXE);
		case IRON: return new ItemStack(Items.IRON_AXE);
		case COPPER: return new ItemStack(Items.COPPER_AXE);
		case STONE: return new ItemStack(Items.STONE_AXE);
		default: return new ItemStack(Items.WOODEN_AXE);
		}
	}
	
	private static ItemStack pickShovel(RandomSource rand, Difficulty diff) {

		Quality quality = Quality.getToolQuality(rand, diff);
		switch (quality) {
		case NETHERITE: return new ItemStack(Items.NETHERITE_SHOVEL);
		case DIAMOND: return new ItemStack(Items.DIAMOND_SHOVEL);
		case GOLD: return new ItemStack(Items.GOLDEN_SHOVEL);
		case IRON: return new ItemStack(Items.IRON_SHOVEL);
		case COPPER: return new ItemStack(Items.COPPER_SHOVEL);
		case STONE: return new ItemStack(Items.STONE_SHOVEL);
		default: return new ItemStack(Items.WOODEN_SHOVEL);
		}
	}
	
	private static ItemStack pickPick(RandomSource rand, Difficulty diff) {

		Quality quality = Quality.getToolQuality(rand, diff);
		switch (quality) {
		case NETHERITE: return new ItemStack(Items.NETHERITE_PICKAXE);
		case DIAMOND: return new ItemStack(Items.DIAMOND_PICKAXE);
		case GOLD: return new ItemStack(Items.GOLDEN_PICKAXE);
		case IRON: return new ItemStack(Items.IRON_PICKAXE);
		case COPPER: return new ItemStack(Items.COPPER_PICKAXE);
		case STONE: return new ItemStack(Items.STONE_PICKAXE);
		default: return new ItemStack(Items.WOODEN_PICKAXE);
		}
	}


	
}
