package com.greymerk.roguelike.treasure.loot;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.util.math.RandHelper;

public enum Enchant {

	MENDING, UNBREAKING, 
	CURSE_OF_VANISHING, CURSE_OF_BINDING,
	EFFICIENCY, SILKTOUCH, FORTUNE,
	SHARPNESS, SMITE, ARTHOPODS, FIREASPECT, KNOCKBACK, LOOTING,   
	AQUAAFFINITY, RESPIRATION,
	FEATHERFALLING, DEPTHSTRIDER, FROSTWALKER,
	PROTECTION, BLASTPROTECTION, FIREPROTECTION, PROJECTILEPROTECTION, THORNS, 
	POWER, PUNCH, FLAME, INFINITY, 
	LURE, LUCKOFTHESEA,
	CHANNELING, IMPALING, RIPTIDE, LOYALTY,
	MULTISHOT, PIERCING, QUICK_CHARGE,
	SOUL_SPEED, SWIFT_SNEAK;
	
	public static final List<Enchant> common = List.of(
			UNBREAKING, EFFICIENCY, SILKTOUCH, FORTUNE,
			SHARPNESS, SMITE, ARTHOPODS, FIREASPECT, KNOCKBACK, LOOTING,
			AQUAAFFINITY, RESPIRATION, FEATHERFALLING, DEPTHSTRIDER, FROSTWALKER,
			PROTECTION, BLASTPROTECTION, FIREPROTECTION, PROJECTILEPROTECTION, THORNS,
			POWER, PUNCH, FLAME, INFINITY, LURE, LUCKOFTHESEA,
			CHANNELING, IMPALING, RIPTIDE, LOYALTY, MULTISHOT, PIERCING, QUICK_CHARGE);
	
	public static final List<Enchant> endgame = List.of(SOUL_SPEED, SWIFT_SNEAK);
	
	public static final List<Enchant> cursed = List.of(CURSE_OF_VANISHING, CURSE_OF_BINDING);
	
	public static Holder<Enchantment> getEnchant(RegistryAccess reg, Enchant type){
		Registry<Enchantment> enchantments = reg.lookupOrThrow(Registries.ENCHANTMENT);
		String ns = "minecraft";
		String path = getName(type);
		Identifier id = Identifier.fromNamespaceAndPath(ns, path);
		return enchantments.get(id).get();
	}
	
	public static String getName(Enchant type){
		switch(type){
			case SHARPNESS: return "sharpness";
			case SMITE: return "smite";
			case ARTHOPODS: return "bane_of_arthropods";
			case LOOTING: return "looting";
			case KNOCKBACK: return "knockback";
			case FIREASPECT: return "fire_aspect";
			case AQUAAFFINITY: return "aqua_affinity";
			case RESPIRATION: return "respiration";
			case FEATHERFALLING: return "feather_falling";
			case DEPTHSTRIDER: return "depth_strider";
			case PROTECTION: return "protection";
			case BLASTPROTECTION: return "blast_protection";
			case FIREPROTECTION: return "fire_protection";
			case PROJECTILEPROTECTION: return "projectile_protection";
			case THORNS: return "thorns";
			case UNBREAKING: return "unbreaking";
			case EFFICIENCY: return "efficiency";
			case SILKTOUCH: return "silk_touch";
			case FORTUNE: return "fortune";
			case POWER: return "power";
			case PUNCH: return "punch";
			case FLAME: return "flame";
			case INFINITY: return "infinity";
			case LURE: return "lure";
			case LUCKOFTHESEA: return "luck_of_the_sea";
			case MENDING: return "mending";
			case CHANNELING: return "channeling";
			case CURSE_OF_BINDING: return "binding_curse";
			case CURSE_OF_VANISHING: return "vanishing_curse";
			case FROSTWALKER: return "frost_walker";
			case IMPALING: return "impaling";
			case LOYALTY: return "loyalty";
			case MULTISHOT: return "multishot";
			case PIERCING: return "piercing";
			case QUICK_CHARGE: return "quick_charge";
			case RIPTIDE: return "riptide";
			case SOUL_SPEED: return "soul_speed";
			case SWIFT_SNEAK: return "swift_sneak";
		}
		return "efficiency";
	}
	
	public static int getMaxRank(Enchant type) {
		switch(type){
		case SHARPNESS: return 5;
		case SMITE: return 5;
		case ARTHOPODS: return 5;
		case LOOTING: return 3;
		case KNOCKBACK: return 2;
		case FIREASPECT: return 2;
		case AQUAAFFINITY: return 1;
		case RESPIRATION: return 3;
		case FEATHERFALLING: return 4;
		case DEPTHSTRIDER: return 3;
		case PROTECTION: return 4;
		case BLASTPROTECTION: return 4;
		case FIREPROTECTION: return 4;
		case PROJECTILEPROTECTION: return 4;
		case THORNS: return 3;
		case UNBREAKING: return 3;
		case EFFICIENCY: return 5;
		case SILKTOUCH: return 1;
		case FORTUNE: return 3;
		case POWER: return 5;
		case PUNCH: return 2;
		case FLAME: return 1;
		case INFINITY: return 1;
		case LURE: return 3;
		case LUCKOFTHESEA: return 3;
		case MENDING: return 1;
		case CHANNELING: return 1;
		case CURSE_OF_BINDING: return 1;
		case CURSE_OF_VANISHING: return 1;
		case FROSTWALKER: return 2;
		case IMPALING: return 5;
		case LOYALTY: return 3;
		case MULTISHOT: return 1;
		case PIERCING: return 4;
		case QUICK_CHARGE: return 3;
		case RIPTIDE: return 3;
		case SOUL_SPEED: return 3;
		case SWIFT_SNEAK: return 3;
		}
		
		return 1;
	}
	
	public static int getRandomRank(RandomSource rand, Enchant type, Difficulty diff) {
		int max = getMaxRank(type);
		if(max == 1) return 1;
		if(diff == Difficulty.HARDEST) return max;
		return rand.nextIntBetweenInclusive(1, max);
	}
	
	public static int getLevel(RandomSource rand, Difficulty diff) {

		switch(diff){
		case HARDEST: return 30 + rand.nextInt(10);
		case HARD: return 15 + rand.nextInt(15);
		case MEDIUM: return 5 + rand.nextInt(15);
		case EASY: return 1 + rand.nextInt(10);
		case EASIEST: return 1 + rand.nextInt(5);
		default: return 1;
		}
	}

	public static ItemStack enchantItem(RegistryAccess reg, RandomSource rand, ItemStack item, Difficulty diff) {
		return enchantItem(reg, rand, item, getLevel(rand, diff));
	}
	
	public static ItemStack enchantItem(RegistryAccess reg, RandomSource rand, ItemStack item, int enchantmentLevel) {
		if (item == null || item == ItemStack.EMPTY) return ItemStack.EMPTY;
		
		List<EnchantmentInstance> enchants = Enchant.enchantsForItem(reg, rand, item, enchantmentLevel, EnchantmentTags.IN_ENCHANTING_TABLE); 
		if(enchants.isEmpty()) return item;
		if (item.is(Items.BOOK)) return getBook(enchants.getFirst());
		
		enchants.forEach(toAdd -> {
			item.enchant(toAdd.enchantment(), toAdd.level());
		});

		return item;		
	}

	public static List<EnchantmentInstance> enchantsForItem(RegistryAccess reg, RandomSource rand, ItemStack item, int level, TagKey<Enchantment> tag){
		Optional<HolderSet.Named<Enchantment>> optional = reg.lookupOrThrow(Registries.ENCHANTMENT).get(tag);
		if(optional.isEmpty()) return List.of();
		List<EnchantmentInstance> enchants = EnchantmentHelper.selectEnchantment(rand, item, level, optional.get().stream());
		return enchants;
	}
	
	public static ItemStack getBook(RegistryAccess reg, RandomSource rand, Difficulty diff) {
		if(diff == Difficulty.HARDEST && rand.nextInt(6) == 0) {
			Enchant type = endgame.get(rand.nextInt(endgame.size()));
			return Enchant.getBook(reg, rand, type, diff);	
		}

		if(rand.nextInt(6) == 0) return Enchant.getBook(reg, Enchant.MENDING);
		
		Optional<HolderSet.Named<Enchantment>> optional = reg.lookupOrThrow(Registries.ENCHANTMENT)
				.get(EnchantmentTags.IN_ENCHANTING_TABLE);
		if(optional.isEmpty()) return new ItemStack(Items.BOOK);
		List<EnchantmentInstance> enchants = Enchant.enchantsForItem(
				reg, rand, new ItemStack(Items.BOOK), getLevel(rand, diff), EnchantmentTags.IN_ENCHANTING_TABLE);
		if(enchants.isEmpty()) return new ItemStack(Items.BOOK); 
		RandHelper.shuffle(enchants, rand);
		return getBook(enchants.getFirst());
	}
	
	public static ItemStack getBook(RegistryAccess reg, RandomSource rand, Enchant type, Difficulty diff) {
		return getBook(new EnchantmentInstance(Enchant.getEnchant(reg, type), Enchant.getRandomRank(rand, type, diff)));
	}

	public static ItemStack getBook(RegistryAccess reg, Enchant type) {
		return getBook(new EnchantmentInstance(getEnchant(reg, type), getMaxRank(type)));
	}
	
	public static ItemStack getBook(EnchantmentInstance ench) {
		ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
		ItemEnchantments component = book.get(DataComponents.STORED_ENCHANTMENTS);
		ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(component);
		builder.upgrade(ench.enchantment(), ench.level());
		book.set(DataComponents.STORED_ENCHANTMENTS, builder.toImmutable());
		return book;
	}
}
