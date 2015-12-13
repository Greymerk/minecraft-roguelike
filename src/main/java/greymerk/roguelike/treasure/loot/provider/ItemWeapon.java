package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemWeapon extends ItemBase{
	
	public ItemWeapon(int weight, int level) {
		super(weight, level);
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
		
		ItemStack bow = new ItemStack(Items.bow);
		
		if(enchant && rand.nextInt(6 - level) == 0){
			Enchant.enchantItem(bow, rand, Enchant.getLevel(rand, level));
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
			Enchant.enchantItem(sword, rand, Enchant.getLevel(rand, level));
		}
		
		return sword;		
	}
	
	private static ItemStack pickSword(Random rand, int level){
		
		Quality quality = Quality.getWeaponQuality(rand, level);
		
		switch (quality) {
		case DIAMOND: return new ItemStack(Items.diamond_sword);
		case GOLD: return new ItemStack(Items.golden_sword);
		case IRON: return new ItemStack(Items.iron_sword);
		case STONE: return new ItemStack(Items.stone_sword);
		default: return new ItemStack(Items.wooden_sword);
		}
	}




	
}
