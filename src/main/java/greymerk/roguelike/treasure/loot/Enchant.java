package greymerk.roguelike.treasure.loot;

import net.minecraft.enchantment.Enchantment;

public enum Enchant {

	SHARPNESS, SMITE, ARTHOPODS, LOOTING, KNOCKBACK, FIREASPECT,
	AQUAAFFINITY, RESPIRATION,
	FEATHERFALLING, DEPTHSTRIDER,
	PROTECTION, BLASTPROTECTION, FIREPROTECTION, PROJECTILEPROTECTION, THORNS,
	UNBREAKING, EFFICIENCY, SILKTOUCH, FORTUNE,
	POWER, PUNCH, FLAME, INFINITY,
	LURE, LUCKOFTHESEA;
	
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
		case DEPTHSTRIDER: return Enchantment.featherFalling;
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
		default: return Enchantment.efficiency;
		}
	}
}
