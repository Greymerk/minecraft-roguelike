package com.greymerk.roguelike.treasure.loot.trim;

import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import com.greymerk.roguelike.util.math.RandHelper;

public enum TrimPattern {

	COAST, DUNE, EYE, HOST, RAISER, RIB, SENTRY, SHAPER, 
	SILENCE, SNOUT, SPIRE, TIDE, VEX, WARD, WAYFINDER, WILD,
	BOLT, FLOW;
	
	public static ItemStack addTrim(ItemStack item) {
		return item;
	}
	
	public static Holder<net.minecraft.world.item.equipment.trim.TrimPattern> getEntry(RegistryAccess registry, RandomSource rand){
		TrimPattern choice = getRandom(rand);
		return getEntry(registry, choice);
	}
	
	public static Holder<net.minecraft.world.item.equipment.trim.TrimPattern> getEntry(RegistryAccess registry, TrimPattern mat){
		Registry<net.minecraft.world.item.equipment.trim.TrimPattern> patterns = registry.lookupOrThrow(Registries.TRIM_PATTERN);
		ResourceKey<net.minecraft.world.item.equipment.trim.TrimPattern> key = getRegistryKey(mat);
		return patterns.getOrThrow(key);
	}
	
	public static ResourceKey<net.minecraft.world.item.equipment.trim.TrimPattern> getRegistryKey(RandomSource rand){
		TrimPattern choice = Arrays.asList(TrimPattern.values()).get(rand.nextInt(TrimPattern.values().length));
		return getRegistryKey(choice);
	}
	
	public static ResourceKey<net.minecraft.world.item.equipment.trim.TrimPattern> getRegistryKey(TrimPattern pattern){
		switch(pattern) {
		case COAST: return TrimPatterns.COAST;
		case DUNE: return TrimPatterns.DUNE;
		case EYE: return TrimPatterns.EYE;
		case HOST: return TrimPatterns.HOST;
		case RAISER: return TrimPatterns.RAISER;
		case RIB: return TrimPatterns.RIB;
		case SENTRY: return TrimPatterns.SENTRY;
		case SHAPER: return TrimPatterns.SHAPER;
		case SILENCE: return TrimPatterns.SILENCE;
		case SNOUT: return TrimPatterns.SNOUT;
		case SPIRE: return TrimPatterns.SPIRE;
		case TIDE: return TrimPatterns.TIDE;
		case VEX: return TrimPatterns.VEX;
		case WARD: return TrimPatterns.WARD;
		case WAYFINDER: return TrimPatterns.WAYFINDER;
		case WILD: return TrimPatterns.WILD;
		case BOLT: return TrimPatterns.BOLT;
		case FLOW: return TrimPatterns.FLOW;
		default: return TrimPatterns.COAST;
		}
	}
	
	public static StringTag getNbt(TrimPattern pattern) {
		return StringTag.valueOf(getId(pattern));
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
	
	public static TrimPattern getRandom(RandomSource rand) {
		List<TrimPattern> mats = Arrays.asList(TrimPattern.values());
		RandHelper.shuffle(mats, rand);
		return mats.get(0);
	}
}
