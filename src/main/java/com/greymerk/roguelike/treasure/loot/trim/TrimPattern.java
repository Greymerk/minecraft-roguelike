package com.greymerk.roguelike.treasure.loot.trim;

import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.math.random.Random;

public enum TrimPattern {

	COAST, DUNE, EYE, HOST, RAISER, RIB, SENTRY, SHAPER, SILENCE, SNOUT, SPIRE, TIDE, VEX, WARD, WAYFINDER, WILD;
	
	public static ItemStack addTrim(ItemStack item) {
		return item;
	}
	
	public static NbtString getNbt(TrimPattern pattern) {
		return NbtString.of(getId(pattern));
	}
	
	public static String getId(TrimPattern pattern) {
		switch(pattern) {
		case COAST: return "minecraft:coast";
		case DUNE: return "minecraft:dune";
		case EYE: return "minecraft:eye";
		case HOST: return "minecraft:host";
		case RAISER: return "minecraft:raiser";
		case RIB: return "minecraft:rib";
		case SENTRY: return "minecraft:sentry";
		case SHAPER: return "minecraft:shaper";
		case SILENCE: return "minecraft:silence";
		case SNOUT: return "minecraft:snout";
		case SPIRE: return "minecraft:spire";
		case TIDE: return "minecraft:tide";
		case VEX: return "minecraft:vex";
		case WARD: return "minecraft:ward";
		case WAYFINDER: return "minecraft:wayfinder";
		case WILD: return "minecraft:wild";
		default: return "minecraft:wild";
		}
	}
	
	public static TrimPattern getRandom(Random rand) {
		List<TrimPattern> mats = Arrays.asList(TrimPattern.values());
		RandHelper.shuffle(mats, rand);
		return mats.get(0);
	}
}
