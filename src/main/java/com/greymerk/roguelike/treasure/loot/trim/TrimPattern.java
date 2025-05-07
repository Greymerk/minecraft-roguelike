package com.greymerk.roguelike.treasure.loot.trim;

import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.item.trim.ArmorTrimPatterns;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;

public enum TrimPattern {

	COAST, DUNE, EYE, HOST, RAISER, RIB, SENTRY, SHAPER, SILENCE, SNOUT, SPIRE, TIDE, VEX, WARD, WAYFINDER, WILD;
	
	public static ItemStack addTrim(ItemStack item) {
		return item;
	}
	
	public static RegistryEntry<ArmorTrimPattern> getEntry(DynamicRegistryManager registry, Random rand){
		TrimPattern choice = getRandom(rand);
		return getEntry(registry, choice);
	}
	
	public static RegistryEntry<ArmorTrimPattern> getEntry(DynamicRegistryManager registry, TrimPattern mat){
		Registry<ArmorTrimPattern> patterns = registry.get(RegistryKeys.TRIM_PATTERN);
		RegistryKey<ArmorTrimPattern> key = getRegistryKey(mat);
		return patterns.getEntry(key).get();
	}
	
	public static RegistryKey<ArmorTrimPattern> getRegistryKey(Random rand){
		TrimPattern choice = Arrays.asList(TrimPattern.values()).get(rand.nextInt(TrimPattern.values().length));
		return getRegistryKey(choice);
	}
	
	public static RegistryKey<ArmorTrimPattern> getRegistryKey(TrimPattern pattern){
		switch(pattern) {
		case COAST: return ArmorTrimPatterns.COAST;
		case DUNE: return ArmorTrimPatterns.DUNE;
		case EYE: return ArmorTrimPatterns.EYE;
		case HOST: return ArmorTrimPatterns.HOST;
		case RAISER: return ArmorTrimPatterns.RAISER;
		case RIB: return ArmorTrimPatterns.RIB;
		case SENTRY: return ArmorTrimPatterns.SENTRY;
		case SHAPER: return ArmorTrimPatterns.SHAPER;
		case SILENCE: return ArmorTrimPatterns.SILENCE;
		case SNOUT: return ArmorTrimPatterns.SNOUT;
		case SPIRE: return ArmorTrimPatterns.SPIRE;
		case TIDE: return ArmorTrimPatterns.TIDE;
		case VEX: return ArmorTrimPatterns.VEX;
		case WARD: return ArmorTrimPatterns.WARD;
		case WAYFINDER: return ArmorTrimPatterns.WAYFINDER;
		case WILD: return ArmorTrimPatterns.WILD;
		default: return ArmorTrimPatterns.COAST;
		}
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