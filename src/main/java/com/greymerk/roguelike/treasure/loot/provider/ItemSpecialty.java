package com.greymerk.roguelike.treasure.loot.provider;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.Loot;
import com.greymerk.roguelike.treasure.loot.Quality;
import com.greymerk.roguelike.util.TextFormat;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.random.Random;

public class ItemSpecialty extends ItemBase {
	
	private Equipment type;
	private Quality quality;
	
	private DynamicRegistryManager reg;
	
	public ItemSpecialty(DynamicRegistryManager reg, int weight, Difficulty diff){
		super(weight, diff);
		this.reg = reg;
	}
	
	public ItemSpecialty(DynamicRegistryManager reg, int weight, Difficulty diff, Equipment type, Quality q){
		super(weight, diff);
		this.type = type;
		this.quality = q;
		this.reg = reg;
	}
	
	public ItemSpecialty(DynamicRegistryManager reg, int weight, Difficulty diff, Quality q){
		super(weight, diff);
		this.quality = q;
		this.reg = reg;
	}
	
	@Override
	public ItemStack get(Random rand){
		Equipment t = this.type == null ? Equipment.values()[rand.nextInt(Equipment.values().length)] : this.type;
		Quality q = this.quality == null ? Quality.get(rand, diff, t) : this.quality;
		return getRandomItem(reg, t, rand, q);
	}
		
	public static ItemStack getRandomItem(DynamicRegistryManager reg, Random rand, Difficulty diff){
		return getRandomItem(reg, Equipment.values()[rand.nextInt(Equipment.values().length)], rand, diff);
	}
	
	
	public static ItemStack getRandomItem(DynamicRegistryManager reg, Equipment type, Random rand, Difficulty diff){
		return getRandomItem(reg, type, rand, Quality.get(rand, diff, type));
	}
	
	public static ItemStack getRandomItem(DynamicRegistryManager reg, Equipment type, Random rand, Quality quality){
		
		ItemStack item;
		
		switch(type){
		case SWORD: item = getSword(reg, rand, quality); break;
		case BOW: item = getBow(reg, rand, quality); break;
		case HELMET: item = getHelmet(reg, rand, quality); break;
		case CHEST: item = getChest(reg, rand, quality); break;
		case LEGS: item = getLegs(reg, rand, quality); break;
		case FEET: item = getBoots(reg, rand, quality); break;
		case PICK: item = getPick(reg, rand, quality); break;
		case AXE: item = getAxe(reg, rand, quality); break;
		case SHOVEL: item = getShovel(reg, rand, quality); break;	
		default: item = getSword(reg, rand, quality);
		}
		
		Loot.setRarity(item, Rarity.RARE);
		return item;
	}
	
	public static ItemStack getRandomArmour(DynamicRegistryManager reg, Random rand, Quality quality){
		switch(rand.nextInt(4)){
		case 0: return getRandomItem(reg, Equipment.HELMET, rand, quality);
		case 1: return getRandomItem(reg, Equipment.CHEST, rand, quality);
		case 2: return getRandomItem(reg, Equipment.LEGS, rand, quality);
		case 3: return getRandomItem(reg, Equipment.FEET, rand, quality);
		default: return getRandomItem(reg, Equipment.HELMET, rand, quality);
		}
	}
	
	public static ItemStack getRandomTool(DynamicRegistryManager reg, Random rand, Quality quality){
		switch(rand.nextInt(3)){
		case 0: return getRandomItem(reg, Equipment.PICK, rand, quality);
		case 1: return getRandomItem(reg, Equipment.AXE, rand, quality);
		case 2: return getRandomItem(reg, Equipment.SHOVEL, rand, quality);
		default: return getRandomItem(reg, Equipment.PICK, rand, quality);
		}
	}
	
	private static ItemStack getShovel(DynamicRegistryManager reg, Random rand, Quality quality){
		ItemStack item;
		if(quality == Quality.DIAMOND){
			item = new ItemStack(Items.DIAMOND_SHOVEL);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.EFFICIENCY), 3 + rand.nextInt(3));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			Loot.setItemName(item, "Soul Spade");
			return item;
		} else {
			item = new ItemStack(Items.IRON_SHOVEL);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.EFFICIENCY), 1 + rand.nextInt(2));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			Loot.setItemName(item, "Grave Spade");
			return item;
		}
	}
	
	private static ItemStack getAxe(DynamicRegistryManager reg, Random rand, Quality quality){
		
		ItemStack item;
		if(quality == Quality.DIAMOND){
			item = new ItemStack(Items.DIAMOND_AXE);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.EFFICIENCY), 3 + rand.nextInt(3));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			Loot.setItemName(item, "Crystal Head Axe");
			return item;
		} else {
			item = new ItemStack(Items.IRON_AXE);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.EFFICIENCY), 1 + rand.nextInt(2));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			Loot.setItemName(item, "Woodland Hatchet");
			return item;
		}
	}
	
	private static ItemStack getPick(DynamicRegistryManager reg, Random rand, Quality quality){
		
		ItemStack item;
		
		if(quality == Quality.DIAMOND){
			item = new ItemStack(Items.DIAMOND_PICKAXE);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.EFFICIENCY), 3 + rand.nextInt(3));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.SILKTOUCH), 1);
				Loot.setItemName(item, "Crystal Pick of Precision");
				return item;
			}
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.FORTUNE), 2 + rand.nextInt(2));
				Loot.setItemName(item, "Crystal Pick of Prospecting");
				return item;
			}
			
			if(rand.nextInt(5) == 0){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
			}
			
			Loot.setItemName(item, "Crystal Pick");
			return item;
		} else {
			item = new ItemStack(Items.IRON_PICKAXE);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.EFFICIENCY), 1 + rand.nextInt(2));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.SILKTOUCH), 1);
				Loot.setItemName(item, "Case Hardened Pick of Precision");
				return item;
			}
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.FORTUNE), 1 + rand.nextInt(3));
				Loot.setItemName(item, "Case Hardened Pick of Prospecting");
				return item;
			}
			
			if(rand.nextInt(5) == 0){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
			}
			
			Loot.setItemName(item, "Case Hardened Pick");
			return item;
		}
		
		
	}
	
	private static ItemStack getSword(DynamicRegistryManager reg, Random rand, Quality quality){
		
		ItemStack item;
		if (quality == Quality.DIAMOND){
			item = new ItemStack(Items.DIAMOND_SWORD);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.SHARPNESS), 3 + rand.nextInt(3));
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.LOOTING), 2 + rand.nextInt(2));
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
				Loot.setItemName(item, "Eldritch Blade of Plundering");
				Loot.setItemLore(item, "The loot taker", TextFormat.DARKGREEN);
				return item;
			}
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.FIREASPECT), 2 + rand.nextInt(2));
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
				Loot.setItemName(item, "Eldritch Blade of the Inferno");
				Loot.setItemLore(item, "From the fiery depths", TextFormat.DARKGREEN);
				return item;
			}
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), quality == Quality.DIAMOND ? 3 : 1 + rand.nextInt(2));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
			Loot.setItemName(item, "Eldritch Blade");
			Loot.setItemLore(item, "Rune Etched", TextFormat.DARKGREEN);
			return item;
		} else {
			item = new ItemStack(Items.IRON_SWORD);
			if(rand.nextBoolean()){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.SHARPNESS), 1);
			}
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), 3);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
			Loot.setItemName(item, "Tempered Blade");
			Loot.setItemLore(item, "Highly Durable", TextFormat.DARKGREEN);
			return item;
		}
		
	}
	
	private static ItemStack getBow(DynamicRegistryManager reg, Random rand, Quality quality){
		
		ItemStack item = new ItemStack(Items.BOW);
		
		switch(quality){
		case WOOD:
		case STONE:
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.POWER), 1 + rand.nextInt(3));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), 1);
			Loot.setItemName(item, "Yew Longbow");
			Loot.setItemLore(item, "Superior craftsmanship", TextFormat.DARKGREEN);
			return item;
		case COPPER:
		case IRON:
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.POWER), 1 + rand.nextInt(3));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), 1 + rand.nextInt(3));
			Loot.setItemName(item, "Laminated Bow");
			Loot.setItemLore(item, "Highly polished", TextFormat.DARKGREEN);
			return item;
		case GOLD:
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.POWER), 3 + rand.nextInt(3));
			if(rand.nextBoolean()){
				item.addEnchantment(Enchant.getEnchant(reg, Enchant.INFINITY), 1);
			}
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), 1 + rand.nextInt(3));
			Loot.setItemName(item, "Recurve Bow");
			Loot.setItemLore(item, "Beautifully crafted", TextFormat.DARKGREEN);
			return item;
		case DIAMOND:
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.POWER), 3 + rand.nextInt(3));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.FLAME), 1);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.INFINITY), 1);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
			Loot.setItemName(item, "Eldritch Bow");
			Loot.setItemLore(item, "Warm to the touch", TextFormat.DARKGREEN);
			return item;
		case NETHERITE:
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.POWER), 3 + rand.nextInt(3));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.FLAME), 1);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.INFINITY), 1);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
			Loot.setItemName(item, "Eldritch Bow");
			Loot.setItemLore(item, "Warm to the touch", TextFormat.DARKGREEN);
			return item;
		default:
			return item;
		}
	}
	
	private static ItemStack getHelmet(DynamicRegistryManager reg, Random rand, Quality quality){
		ItemStack item;
		
		String canonical = "";
		
		switch(quality){
		case WOOD:
			item = new ItemStack(Items.LEATHER_HELMET);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Bonnet";
			break;
		case STONE:
			item = new ItemStack(Items.CHAINMAIL_HELMET);
			canonical = "Coif";
			break;
		case COPPER:
		case IRON:
		case GOLD:
			item = new ItemStack(Items.IRON_HELMET);
			canonical = "Sallet";
			break;
		case DIAMOND:
			item = new ItemStack(Items.DIAMOND_HELMET);
			canonical = "Helm";
			break;
		case NETHERITE:
			item = new ItemStack(Items.NETHERITE_HELMET);
			canonical = "Visor";
			break;
		default:
			item = new ItemStack(Items.LEATHER_HELMET);
		}
		
		
		String suffix = "";

		if(rand.nextInt(20) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROTECTION), getProtectionLevel(quality, rand));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.RESPIRATION), 3);
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.AQUAAFFINITY), 1);
			suffix = "of Diving";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Deflection";
		} else {		
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
		}		
		
		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		Loot.setItemName(item, name);
		return item;
	}
	
	
	private static ItemStack getBoots(DynamicRegistryManager reg, Random rand, Quality quality){
		ItemStack item;
		
		String canonical = "";
		
		switch(quality){
		case WOOD:
			item = new ItemStack(Items.LEATHER_BOOTS);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Shoes";
			break;
		case STONE:
			item = new ItemStack(Items.CHAINMAIL_BOOTS);
			canonical = "Greaves";
			break;
		case COPPER:
		case IRON:
		case GOLD:
			item = new ItemStack(Items.IRON_BOOTS);
			canonical = "Sabatons";
			break;
		case DIAMOND:
			item = new ItemStack(Items.DIAMOND_BOOTS);
			canonical = "Boots";
			break;
		case NETHERITE:
			item = new ItemStack(Items.NETHERITE_BOOTS);
			canonical = "Stompers";
			break;
		default:
			item = new ItemStack(Items.LEATHER_BOOTS);
			canonical = "Shoes";
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Warding";
		} else if(rand.nextInt(5) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROTECTION), getProtectionLevel(quality, rand));
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.FEATHERFALLING), quality == Quality.DIAMOND ? 4 : 1 + rand.nextInt(3));
			suffix = "of Lightness";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Deflection";
		} else {
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
		}
		
		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		Loot.setItemName(item, name);
		return item;
	}
	
	
	private static ItemStack getLegs(DynamicRegistryManager reg, Random rand, Quality quality){
		ItemStack item;
		
		String canonical = "";
		
		switch(quality){
		case WOOD:
			item = new ItemStack(Items.LEATHER_LEGGINGS);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Pantaloons";
			break;
		case STONE:
			item = new ItemStack(Items.CHAINMAIL_LEGGINGS);
			canonical = "Chausses";
			break;
		case COPPER:
		case IRON:
		case GOLD:
			item = new ItemStack(Items.IRON_LEGGINGS);
			canonical = "Leg-plates";
			break;
		case DIAMOND:
			item = new ItemStack(Items.DIAMOND_LEGGINGS);
			canonical = "Leggings";
			break;
		case NETHERITE:
			item = new ItemStack(Items.NETHERITE_BOOTS);
			canonical = "Leg-guards";
			break;
		default:
			item = new ItemStack(Items.LEATHER_LEGGINGS);
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.FIREPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Warding";
		} else if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Integrity";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Deflection";
		} else {
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
		}
		
		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		Loot.setItemName(item, name);
		return item;
	}
	
	private static ItemStack getChest(DynamicRegistryManager reg, Random rand, Quality quality){
		ItemStack item;
		
		String canonical = "";
		
		switch(quality){
		case WOOD:
			item = new ItemStack(Items.LEATHER_CHESTPLATE);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Tunic";
			break;
		case STONE:
			item = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
			canonical = "Hauberk";
			break;
		case COPPER:
		case IRON:
		case GOLD:
			item = new ItemStack(Items.IRON_CHESTPLATE);
			canonical = "Cuirass";
			break;
		case DIAMOND:
			item = new ItemStack(Items.DIAMOND_CHESTPLATE);
			canonical = "Plate";
			break;
		case NETHERITE:
			item = new ItemStack(Items.NETHERITE_CHESTPLATE);
			canonical = "Soul cage";
			break;
		default:
			item = new ItemStack(Items.LEATHER_CHESTPLATE);
			canonical = "Tunic";
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.FIREPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Flamewarding";
		} else if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Integrity";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Deflection";
		} else {
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.PROTECTION), getProtectionLevel(quality, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchant.getEnchant(reg, Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchant.getEnchant(reg, Enchant.MENDING), 1);
		}
		
		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		Loot.setItemName(item, name);
		return item;
	}
	
	private static int getUnbreakingLevel(Quality quality, Random rand){
		return quality == Quality.DIAMOND ? 3 : 1 + rand.nextInt(2);
	}
	
	private static int getProtectionLevel(Quality quality, Random rand){
		
		int value = 1;
		
		switch(quality){
		case WOOD:
			if(rand.nextInt(3) == 0){
				value++;
			}
			break;
		case STONE:
			if(rand.nextBoolean()){
				value++;
			}
			break;
		case COPPER:
		case IRON:
		case GOLD:
			value += rand.nextInt(3);
			break;
		case DIAMOND:
		case NETHERITE:
			value += 2 + rand.nextInt(2);
			break;
		}
		
		return value;
	}
	
	private static String getArmourPrefix(Quality quality){
		
		switch(quality){
		case WOOD: return "Surplus";
		case STONE: return "Riveted";
		case COPPER: return "Tarnished";
		case IRON: return "Gothic";
		case GOLD: return "Jewelled";
		case DIAMOND: return "Crystal";
		case NETHERITE: return "Ornate";
		default: return "Strange";
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, Difficulty diff) {
		return getRandomItem(reg, Equipment.values()[rand.nextInt(Equipment.values().length)], rand, Quality.get(diff));
	}		
}
