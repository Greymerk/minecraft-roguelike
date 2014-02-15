package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemTool {


	public static ItemStack getRandom(Random rand, int rank, boolean enchant){
		
		if(enchant && rand.nextInt(20 + (rank * 10)) == 0){
			switch(rand.nextInt(3)){
			case 0: return ItemSpecialty.getRandomItem(Equipment.PICK, rand, rank);
			case 1: return ItemSpecialty.getRandomItem(Equipment.AXE, rand, rank);
			case 2: return ItemSpecialty.getRandomItem(Equipment.SHOVEL, rand, rank);
			}
			
		}
		
		ItemStack tool = pickTool(rand, rank);
		
		if(enchant && rand.nextInt(6 - rank) == 0){
			Loot.enchantItem(tool, rand, Loot.getEnchantLevel(rank));
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
		case DIAMOND: return new ItemStack(Item.axeDiamond);
		case GOLD: return new ItemStack(Item.axeGold);
		case IRON: return new ItemStack(Item.axeIron);
		case STONE: return new ItemStack(Item.axeStone);
		default: return new ItemStack(Item.axeWood);
		}
	}
	
	private static ItemStack pickShovel(Random rand, int rank) {

		Quality quality = Quality.getToolQuality(rand, rank);
		switch (quality) {
		case DIAMOND: return new ItemStack(Item.shovelDiamond);
		case GOLD: return new ItemStack(Item.shovelGold);
		case IRON: return new ItemStack(Item.shovelIron);
		case STONE: return new ItemStack(Item.shovelStone);
		default: return new ItemStack(Item.shovelWood);
		}
	}
	
	private static ItemStack pickPick(Random rand, int rank) {

		Quality quality = Quality.getToolQuality(rand, rank);
		switch (quality) {
		case DIAMOND: return new ItemStack(Item.pickaxeDiamond);
		case GOLD: return new ItemStack(Item.pickaxeGold);
		case IRON: return new ItemStack(Item.pickaxeIron);
		case STONE: return new ItemStack(Item.pickaxeStone);
		default: return new ItemStack(Item.pickaxeWood);
		}
	}
	
}
