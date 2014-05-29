package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

import java.util.Random;

import net.minecraft.src.Enchantment;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemSpecialty {

	

	
	public static ItemStack getRandomItem(Random rand, int level){
		return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, level);
	}
	
	
	public static ItemStack getRandomItem(Equipment type, Random rand, int level){
		return getRandomItem(type, rand, Quality.getQuality(rand, level, type));
	}
	
	public static ItemStack getRandomItem(Equipment type, Random rand, Quality quality){
		
		ItemStack item;
				
		switch(type){
		
		case SWORD: return getSword(rand, quality);
		case BOW: return getBow(rand, quality);
		case HELMET: return getHelmet(rand, quality);
		case CHEST: return getChest(rand, quality);
		case LEGS: return getLegs(rand, quality);
		case FEET: return getBoots(rand, quality);
		case PICK: return getPick(rand, quality);
		case AXE: return getAxe(rand, quality);
		case SHOVEL: return getShovel(rand, quality);		
		default: return null;
		}
	}
	
	public static ItemStack getRandomArmour(Random rand, Quality quality){
		switch(rand.nextInt(4)){
		case 0: return getRandomItem(Equipment.HELMET, rand, quality);
		case 1: return getRandomItem(Equipment.CHEST, rand, quality);
		case 2: return getRandomItem(Equipment.LEGS, rand, quality);
		case 3: return getRandomItem(Equipment.FEET, rand, quality);
		default: return null;
		}
	}
	
	public static ItemStack getRandomTool(Random rand, Quality quality){
		switch(rand.nextInt(3)){
		case 0: return getRandomItem(Equipment.PICK, rand, quality);
		case 1: return getRandomItem(Equipment.AXE, rand, quality);
		case 2: return getRandomItem(Equipment.SHOVEL, rand, quality);
		default: return null;
		}
	}
	
	private static ItemStack getShovel(Random rand, Quality quality){
		ItemStack item;
		if(quality == Quality.DIAMOND){
			item = new ItemStack(Item.shovelDiamond);
			item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
			item.setItemName("Soulsand Spade");
			return item;
		} else {
			item = new ItemStack(Item.shovelIron);
			item.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(2));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
			item.setItemName("Grave Spade");
			return item;
		}
	}
	
	private static ItemStack getAxe(Random rand, Quality quality){
		
		ItemStack item;
		if(quality == Quality.DIAMOND){
			item = new ItemStack(Item.axeDiamond);
			item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
			item.setItemName("Hellsteel Axe");
			return item;
		} else {
			item = new ItemStack(Item.axeIron);
			item.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(2));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
			item.setItemName("Lumberjack's Hatchet");
			return item;
		}
		
		
	}
	
	private static ItemStack getPick(Random rand, Quality quality){
		
		ItemStack item;
		
		if(quality == Quality.DIAMOND){
			item = new ItemStack(Item.pickaxeDiamond);
			item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchantment.silkTouch, 1);
				item.setItemName("Crystal Pick of Precision");
				return item;
			}
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchantment.fortune, 2 + rand.nextInt(2));
				item.setItemName("Crystal Pick of Prospecting");
				return item;
			}
			item.setItemName("Crystal Pick");
			return item;
		} else {
			item = new ItemStack(Item.pickaxeIron);
			item.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(2));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchantment.silkTouch, 1);
				item.setItemName("Case Hardened Pick of Precision");
				return item;
			}
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchantment.fortune, 1 + rand.nextInt(3));
				item.setItemName("Case Hardened Pick of Prospecting");
				return item;
			}
			item.setItemName("Case Hardened Pick");
			return item;
		}
		
		
	}
	
	private static ItemStack getSword(Random rand, Quality quality){
		
		ItemStack item;
		if (quality == Quality.DIAMOND){
			item = new ItemStack(Item.swordDiamond);
			item.addEnchantment(Enchantment.sharpness, 3 + rand.nextInt(3));
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchantment.looting, 2 + rand.nextInt(2));
				item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
				item.setItemName("Eldritch Blade of Plundering");
				Loot.setItemLore(item, "The loot taker", TextFormat.DARKGREEN);
				return item;
			}
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchantment.fireAspect, 2 + rand.nextInt(2));
				item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
				item.setItemName("Eldritch Blade of the Inferno");
				Loot.setItemLore(item, "From the fiery depths", TextFormat.DARKGREEN);
				return item;
			}
			item.addEnchantment(Enchantment.unbreaking, quality == Quality.DIAMOND ? 3 : 1 + rand.nextInt(2));
			item.setItemName("Eldritch Blade");
			Loot.setItemLore(item, "Rune Etched", TextFormat.DARKGREEN);
			return item;
		} else {
			item = new ItemStack(Item.swordIron);
			if(rand.nextBoolean()){
				item.addEnchantment(Enchantment.sharpness, 1);
			}
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
			item.setItemName("Tempered Blade");
			Loot.setItemLore(item, "Highly Durable", TextFormat.DARKGREEN);
			return item;
		}
		
	}
	
	private static ItemStack getBow(Random rand, Quality quality){
		
		ItemStack item = new ItemStack(Item.bow);
		
		switch(quality){
		case WOOD:
		case STONE:
			item.addEnchantment(Enchantment.power, 1 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, 1);
			item.setItemName("Yew Longbow");
			Loot.setItemLore(item, "Superior craftsmanship", TextFormat.DARKGREEN);
			return item;
		case IRON:
			item.addEnchantment(Enchantment.power, 1 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, 1 + rand.nextInt(3));
			item.setItemName("Laminated Bow");
			Loot.setItemLore(item, "Highly polished", TextFormat.DARKGREEN);
			return item;
		case GOLD:
			item.addEnchantment(Enchantment.power, 3 + rand.nextInt(3));
			if(rand.nextBoolean()){
				item.addEnchantment(Enchantment.infinity, 1);
			}
			item.addEnchantment(Enchantment.unbreaking, 1 + rand.nextInt(3));
			item.setItemName("Recurve Bow");
			Loot.setItemLore(item, "Beautifully crafted", TextFormat.DARKGREEN);
			return item;
		case DIAMOND:
			item.addEnchantment(Enchantment.power, 3 + rand.nextInt(3));
			item.addEnchantment(Enchantment.flame, 1);
			item.addEnchantment(Enchantment.infinity, 1);
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
			item.setItemName("Eldritch Bow");
			Loot.setItemLore(item, "Warm to the touch", TextFormat.DARKGREEN);
			return item;
		default:
			return null;
		}
	}
	
	private static ItemStack getHelmet(Random rand, Quality quality){
		ItemStack item;
		
		String canonical = "";
		
		switch(quality){
		case WOOD:
			item = new ItemStack(Item.helmetLeather);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Bonnet";
			break;
		case STONE:
			item = new ItemStack(Item.helmetChain);
			canonical = "Coif";
			break;
		case IRON:
		case GOLD:
			item = new ItemStack(Item.helmetIron);
			canonical = "Sallet";
			break;
		case DIAMOND:
			item = new ItemStack(Item.helmetDiamond);
			canonical = "Helm";
			break;
		default:
			item = new ItemStack(Item.helmetLeather);
		}
		
		
		String suffix = "";

		if(rand.nextInt(20) == 0){
			item.addEnchantment(Enchantment.protection, getProtectionLevel(quality, rand));
			item.addEnchantment(Enchantment.respiration, 3);
			item.addEnchantment(Enchantment.aquaAffinity, 1);
			suffix = "of diving";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchantment.projectileProtection, getProtectionLevel(quality, rand));
			suffix = "of the archer";
		} else {		
			item.addEnchantment(Enchantment.protection, getProtectionLevel(quality, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
		
		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		item.setItemName(name);
		return item;
	}
	
	
	private static ItemStack getBoots(Random rand, Quality quality){
		ItemStack item;
		
		String canonical = "";
		
		switch(quality){
		case WOOD:
			item = new ItemStack(Item.bootsLeather);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Shoes";
			break;
		case STONE:
			item = new ItemStack(Item.bootsChain);
			canonical = "Greaves";
			break;
		case IRON:
		case GOLD:
			item = new ItemStack(Item.bootsIron);
			canonical = "Sabatons";
			break;
		case DIAMOND:
			item = new ItemStack(Item.bootsDiamond);
			canonical = "Boots";
			break;
		default:
			item = new ItemStack(Item.bootsLeather);
			canonical = "Shoes";
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(quality, rand));
			suffix = "of Warding";
		} else if(rand.nextInt(5) == 0){
			item.addEnchantment(Enchantment.protection, getProtectionLevel(quality, rand));
			item.addEnchantment(Enchantment.featherFalling, quality == quality.DIAMOND ? 4 : 1 + rand.nextInt(3));
			suffix = "of Lightness";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchantment.projectileProtection, getProtectionLevel(quality, rand));
			suffix = "of Archery";
		} else {
			item.addEnchantment(Enchantment.protection, getProtectionLevel(quality, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
		
		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		item.setItemName(name);
		return item;
	}
	
	
	private static ItemStack getLegs(Random rand, Quality quality){
		ItemStack item;
		
		String canonical = "";
		
		switch(quality){
		case WOOD:
			item = new ItemStack(Item.legsLeather);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Pantaloons";
			break;
		case STONE:
			item = new ItemStack(Item.legsChain);
			canonical = "Chausses";
			break;
		case IRON:
		case GOLD:
			item = new ItemStack(Item.legsIron);
			canonical = "Leg-plates";
			break;
		case DIAMOND:
			item = new ItemStack(Item.legsDiamond);
			canonical = "Leggings";
			break;
		default:
			item = new ItemStack(Item.legsLeather);
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(quality, rand));
			suffix = "of Warding";
		} else if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.blastProtection, getProtectionLevel(quality, rand));
			suffix = "of Integrity";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchantment.projectileProtection, getProtectionLevel(quality, rand));
			suffix = "of Archery";
		} else {
			item.addEnchantment(Enchantment.protection, getProtectionLevel(quality, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
		
		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		item.setItemName(name);
		return item;
	}
	
	private static ItemStack getChest(Random rand, Quality quality){
		ItemStack item;
		
		String canonical = "";
		
		switch(quality){
		case WOOD:
			item = new ItemStack(Item.plateLeather);
			ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Tunic";
			break;
		case STONE:
			item = new ItemStack(Item.plateChain);
			canonical = "Hauberk";
			break;
		case IRON:
		case GOLD:
			item = new ItemStack(Item.plateIron);
			canonical = "Cuirass";
			break;
		case DIAMOND:
			item = new ItemStack(Item.plateDiamond);
			canonical = "Armour";
			break;
		default:
			item = new ItemStack(Item.plateLeather);
			canonical = "Tunic";
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(quality, rand));
			suffix = "of Flamewarding";
		} else if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.blastProtection, getProtectionLevel(quality, rand));
			suffix = "of Integrity";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchantment.projectileProtection, getProtectionLevel(quality, rand));
			suffix = "of Archery";
		} else {
			item.addEnchantment(Enchantment.protection, getProtectionLevel(quality, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(quality, rand));
		
		String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
		item.setItemName(name);
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
		case IRON:
		case GOLD:
			value += rand.nextInt(3);
			break;
		case DIAMOND:
			value += 2 + rand.nextInt(2);
			break;
		}
		
		return value;
	}
	
	private static String getArmourPrefix(Quality quality){
		
		switch(quality){
		case WOOD: return "Surplus";
		case STONE: return "Riveted";
		case IRON: return "Gothic";
		case GOLD: return "Jewelled";
		case DIAMOND: return "Magic";
		default: return "Strange";
		}
	}		
}
