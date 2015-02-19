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
		case SHARPNESS: return Enchantment.field_180308_g; 
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
}
