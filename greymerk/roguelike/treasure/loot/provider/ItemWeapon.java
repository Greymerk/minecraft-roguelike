package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ILootProvider;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemWeapon extends ItemBase implements ILootProvider{
	
	public ItemWeapon(int weight) {
		super(weight);
	}
	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return getRandom(rand, level, true);
	}

	public static ItemStack getRandom(Random rand, int rank, boolean enchant){
		
		if(rand.nextInt(10) == 0){
			return ItemWeapon.getBow(rand, rank, enchant);
		} else {
			return ItemWeapon.getSword(rand, rank, enchant);
		}
	}
	
	public static ItemStack getBow(Random rand, int level, boolean enchant){
		
		if(rand.nextInt(20 + (level * 10)) == 0){
			return ItemSpecialty.getRandomItem(Equipment.BOW, rand, level);
		}
		
		ItemStack bow = new ItemStack(Item.bow);
		
		if(enchant && rand.nextInt(6 - level) == 0){
			Loot.enchantItem(bow, rand, Loot.getEnchantLevel(rand, level));
		}
		
		return bow;
		
	}
	
	public static ItemStack getSword(Random rand, int level, boolean enchant){
		ItemStack sword;
		
		if(enchant && rand.nextInt(10 + (level * 10)) == 0){
			return ItemSpecialty.getRandomItem(Equipment.SWORD, rand, level);
		}
		
		sword = pickSword(rand, level);
		
		if(enchant && rand.nextInt(6 - level) == 0){
			Loot.enchantItem(sword, rand, Loot.getEnchantLevel(rand, level));
		}
		
		return sword;		
	}
	
	private static ItemStack pickSword(Random rand, int rank){
		
		Quality quality = Quality.getWeaponQuality(rand, rank);
		
		switch (quality) {
		case DIAMOND: return new ItemStack(Item.swordDiamond);
		case GOLD: return new ItemStack(Item.swordGold);
		case IRON: return new ItemStack(Item.swordIron);
		case STONE: return new ItemStack(Item.swordStone);
		default: return new ItemStack(Item.swordWood);
		}
	}


	
}
