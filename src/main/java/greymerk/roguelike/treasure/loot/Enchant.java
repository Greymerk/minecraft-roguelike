package greymerk.roguelike.treasure.loot;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;

public enum Enchant {

	SHARPNESS, SMITE, ARTHOPODS, LOOTING, KNOCKBACK, FIREASPECT,
	AQUAAFFINITY, RESPIRATION, FEATHERFALLING, DEPTHSTRIDER,
	PROTECTION, BLASTPROTECTION, FIREPROTECTION, PROJECTILEPROTECTION, 
	THORNS, UNBREAKING, EFFICIENCY, SILKTOUCH, FORTUNE,
	POWER, PUNCH, FLAME, INFINITY, LURE, LUCKOFTHESEA;
	
	public static Enchantment getEnchant(Enchant type){
		switch(type){
		case SHARPNESS: return Enchantment.sharpness; 
		case SMITE: return Enchantment.smite;
		case ARTHOPODS: return Enchantment.baneOfArthropods;
		case LOOTING: return Enchantment.looting;
		case KNOCKBACK: return Enchantment.knockback;
		case FIREASPECT: return Enchantment.fireAspect;
		case AQUAAFFINITY: return Enchantment.aquaAffinity;
		case RESPIRATION: return Enchantment.respiration;
		case FEATHERFALLING: return Enchantment.featherFalling;
		case DEPTHSTRIDER: return Enchantment.depthStrider;
		case PROTECTION: return Enchantment.protection;
		case BLASTPROTECTION: return Enchantment.blastProtection;
		case FIREPROTECTION: return Enchantment.fireProtection;
		case PROJECTILEPROTECTION: return Enchantment.projectileProtection;
		case THORNS: return Enchantment.thorns;
		case UNBREAKING: return Enchantment.unbreaking;
		case EFFICIENCY: return Enchantment.efficiency;
		case SILKTOUCH: return Enchantment.silkTouch;
		case FORTUNE: return Enchantment.fortune;
		case POWER: return Enchantment.power;
		case FLAME: return Enchantment.flame;
		case INFINITY: return Enchantment.infinity;
		case LURE: return Enchantment.lure;
		case LUCKOFTHESEA: return Enchantment.luckOfTheSea;
		default: return Enchantment.efficiency;
		}
	}
	
	public static int getLevel(Random rand, int level) {

		switch(level){
		case 4: return 21 + rand.nextInt(10);
		case 3: return 16 + rand.nextInt(10);
		case 2: return 5 + rand.nextInt(10);
		case 1: return 1 + rand.nextInt(5);
		case 0: return 1;
		default: return 1;
		}
	}

	public static boolean canEnchant(EnumDifficulty difficulty, Random rand, int level){
		
		if(difficulty == null) difficulty = EnumDifficulty.NORMAL;
		
		switch(difficulty){
		case PEACEFUL: return false;
		case EASY: return rand.nextInt(5) == 0;
		case NORMAL: return level >= 3 || rand.nextBoolean();
		case HARD: return true;
		default: return true;
		}
	}

	@SuppressWarnings("unchecked")
	public static void enchantItem(Random rand, ItemStack item, int enchantLevel) {

		if (item == null ) return;
		
		List<EnchantmentData> enchants = null;
		try{
			enchants = EnchantmentHelper.buildEnchantmentList(rand, item, enchantLevel);
		} catch(NullPointerException e){
			throw e;
		}
		
		boolean isBook = item.getItem() == Items.book;

		if (isBook){
			item.setItem(Items.enchanted_book);
			if(enchants.size() > 1){
				enchants.remove(rand.nextInt(enchants.size()));
			}
		}

		for (EnchantmentData toAdd : enchants){
			if (isBook){
				Items.enchanted_book.addEnchantment(item, toAdd);
			} else {
				item.addEnchantment(toAdd.enchantmentobj, toAdd.enchantmentLevel);
			}
		}
	}
}
