package greymerk.roguelike.treasure.loot;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum Enchant {

	SHARPNESS, SMITE, ARTHOPODS, LOOTING, KNOCKBACK, FIREASPECT,
	AQUAAFFINITY, RESPIRATION, FEATHERFALLING, DEPTHSTRIDER,
	PROTECTION, BLASTPROTECTION, FIREPROTECTION, PROJECTILEPROTECTION, 
	THORNS, UNBREAKING, EFFICIENCY, SILKTOUCH, FORTUNE,
	POWER, PUNCH, FLAME, INFINITY, LURE, LUCKOFTHESEA;
	
	public static Enchantment getEnchant(Enchant type){
		switch(type){
		case SHARPNESS: return Enchantment.field_180314_l; 
		case SMITE: return Enchantment.field_180315_m;
		case ARTHOPODS: return Enchantment.field_180312_n;
		case LOOTING: return Enchantment.looting;
		case KNOCKBACK: return Enchantment.field_180313_o;
		case FIREASPECT: return Enchantment.fireAspect;
		case AQUAAFFINITY: return Enchantment.aquaAffinity;
		case RESPIRATION: return Enchantment.field_180317_h;
		case FEATHERFALLING: return Enchantment.field_180309_e;
		case DEPTHSTRIDER: return Enchantment.field_180316_k;
		case PROTECTION: return Enchantment.field_180310_c;
		case BLASTPROTECTION: return Enchantment.blastProtection;
		case FIREPROTECTION: return Enchantment.fireProtection;
		case PROJECTILEPROTECTION: return Enchantment.field_180308_g;
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

	public static void enchantItemChance(ItemStack item, Random rand, int level){
		if(rand.nextInt(7 - level) == 0) enchantItem(item, rand, getLevel(rand, level));
	}

	public static void enchantItem(ItemStack item, Random rand, int enchantLevel) {

		if (item == null ) return;

		@SuppressWarnings("unchecked")
		List<EnchantmentData> enchants = EnchantmentHelper.buildEnchantmentList(rand, item, enchantLevel);
		
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
