package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemWeapon {
	
	public static ItemStack getRandom(Random rand, int rank, boolean enchant){
		
		if(rand.nextInt(10) == 0){
			return ItemWeapon.getBow(rand, rank, enchant);
		} else {
			return ItemWeapon.getSword(rand, rank, enchant);
		}
	}
	
	public static ItemStack getBow(Random rand, int rank, boolean enchant){
		
		if(rand.nextInt(20 + (rank * 10)) == 0){
			return ItemSpecialty.getRandomItem(ItemSpecialty.BOW, rand, rank);
		}
		
		ItemStack bow = new ItemStack(Item.bow);
		
		if(enchant && rand.nextInt(6 - rank) == 0){
			Loot.enchantItem(bow, rand, Loot.getEnchantLevel(rank));
		}
		
		return bow;
		
	}
	
	public static ItemStack getSword(Random rand, int rank, boolean enchant){
		ItemStack sword;
		
		if(enchant && rand.nextInt(10 + (rank * 10)) == 0){
			return ItemSpecialty.getRandomItem(ItemSpecialty.SWORD, rand, rank);
		}
		
		sword = pickSword(rand, rank);
		
		if(enchant && rand.nextInt(6 - rank) == 0){
			Loot.enchantItem(sword, rand, Loot.getEnchantLevel(rank));
		}
		
		return sword;		
	}
	
	private static ItemStack pickSword(Random rand, int rank){
		
		Quality quality = Quality.getQuality(rand, rank);
		
		switch (quality) {
		case DIAMOND: return new ItemStack(Item.swordDiamond);
		case GOLD: return new ItemStack(Item.swordGold);
		case IRON: return new ItemStack(Item.swordIron);
		case STONE: return new ItemStack(Item.swordStone);
		default: return new ItemStack(Item.swordWood);
		}
	}
	
}
