package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemTool extends ItemBase {


	public ItemTool(int weight, int level) {
		super(weight, level);
	}
	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return getRandom(rand, level, true);
	}

	public static ItemStack getRandom(Random rand, int level, boolean enchant){
		
		if(level > 1 && rand.nextInt(500) == 0){
			return ItemNovelty.getItem(ItemNovelty.AMLP);
		}
		
		if(enchant && rand.nextInt(20 + (level * 10)) == 0){
			switch(rand.nextInt(3)){
			case 0: return ItemSpecialty.getRandomItem(Equipment.PICK, rand, level);
			case 1: return ItemSpecialty.getRandomItem(Equipment.AXE, rand, level);
			case 2: return ItemSpecialty.getRandomItem(Equipment.SHOVEL, rand, level);
			}
		}
		
		ItemStack tool = pickTool(rand, level);
		
		if(enchant && rand.nextInt(6 - level) == 0){
			Loot.enchantItem(tool, rand, Loot.getEnchantLevel(rand, level));
		}
		
		return tool;
	}
	
	private static ItemStack pickTool(Random rand, int rank){
		
		switch(rand.nextInt(3)){
		case 0: return pickPick(rand, rank);
		case 1: return pickAxe(rand, rank);
		case 2: return pickShovel(rand, rank);		
		default: return pickPick(rand, rank);
		}
	}
	

	private static ItemStack pickAxe(Random rand, int rank) {
		Quality quality = Quality.getToolQuality(rand, rank);
		switch (quality) {
		case DIAMOND: return new ItemStack(Items.diamond_axe);
		case GOLD: return new ItemStack(Items.golden_axe);
		case IRON: return new ItemStack(Items.iron_axe);
		case STONE: return new ItemStack(Items.stone_axe);
		default: return new ItemStack(Items.wooden_axe);
		}
	}
	
	private static ItemStack pickShovel(Random rand, int rank) {

		Quality quality = Quality.getToolQuality(rand, rank);
		switch (quality) {
		case DIAMOND: return new ItemStack(Items.diamond_shovel);
		case GOLD: return new ItemStack(Items.golden_shovel);
		case IRON: return new ItemStack(Items.iron_shovel);
		case STONE: return new ItemStack(Items.stone_shovel);
		default: return new ItemStack(Items.wooden_shovel);
		}
	}
	
	private static ItemStack pickPick(Random rand, int rank) {

		Quality quality = Quality.getToolQuality(rand, rank);
		switch (quality) {
		case DIAMOND: return new ItemStack(Items.diamond_pickaxe);
		case GOLD: return new ItemStack(Items.golden_pickaxe);
		case IRON: return new ItemStack(Items.iron_pickaxe);
		case STONE: return new ItemStack(Items.stone_pickaxe);
		default: return new ItemStack(Items.wooden_pickaxe);
		}
	}


	
}
