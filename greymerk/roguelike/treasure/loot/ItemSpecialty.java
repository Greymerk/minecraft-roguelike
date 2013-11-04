package greymerk.roguelike.treasure.loot;

import greymerk.roguelike.util.TextFormat;

import java.util.Random;

import net.minecraft.src.Enchantment;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public enum ItemSpecialty {

	SWORD, BOW, HELMET, CHEST, LEGS, FEET, PICK, AXE, SHOVEL;
	
	public static ItemStack getItem(ItemSpecialty type, Random rand, int rank){
		
		ItemStack item;
		
		switch(type){
		
		case SWORD:
			return getSword(rand, rank);
		case BOW:
			return getBow(rand, rank);
		case HELMET:
			return getHelmet(rand, rank);
		case CHEST:
			return getChest(rand, rank);
		case LEGS:
			return getLegs(rand, rank);
		case FEET:
			return getBoots(rand, rank);
		case PICK:
			return getPick(rand, rank);
		case AXE:
			return getAxe(rand, rank);
		case SHOVEL:
			return getShovel(rand, rank);		
		default:
			return new ItemStack(Item.stick);
		}
	}
	
	private static ItemStack getShovel(Random rand, int rank){
		ItemStack item;
		if(rank == 3){
			item = new ItemStack(Item.shovelDiamond);
			item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
			item.setItemName("Soulsand Spade");
			return item;
		} else {
			item = new ItemStack(Item.shovelIron);
			item.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(2));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
			item.setItemName("Grave Spade");
			return item;
		}
	}
	
	private static ItemStack getAxe(Random rand, int rank){
		
		ItemStack item;
		if(rank == 3){
			item = new ItemStack(Item.axeDiamond);
			item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
			item.setItemName("Hellsteel Axe");
			return item;
		} else {
			item = new ItemStack(Item.axeIron);
			item.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(2));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
			item.setItemName("Lumberjack's Hatchet");
			return item;
		}
		
		
	}
	
	private static ItemStack getPick(Random rand, int rank){
		
		ItemStack item;
		
		if(rank == 3){
			item = new ItemStack(Item.pickaxeDiamond);
			item.addEnchantment(Enchantment.efficiency, 3 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
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
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
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
	
	private static ItemStack getSword(Random rand, int rank){
		
		ItemStack item;
		if (rank == 3){
			item = new ItemStack(Item.swordDiamond);
			item.addEnchantment(Enchantment.sharpness, 3 + rand.nextInt(3));
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchantment.looting, 2 + rand.nextInt(2));
				item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
				item.setItemName("Eldritch Blade of Plundering");
				ItemLoot.setItemLore(item, "The loot taker", TextFormat.DARKGREEN);
				return item;
			}
			if(rand.nextInt(10) == 0){
				item.addEnchantment(Enchantment.fireAspect, 2 + rand.nextInt(2));
				item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
				item.setItemName("Eldritch Blade of the Inferno");
				ItemLoot.setItemLore(item, "From the fiery depths", TextFormat.DARKGREEN);
				return item;
			}
			item.addEnchantment(Enchantment.unbreaking, rank == 3 ? 3 : 1 + rand.nextInt(2));
			item.setItemName("Eldritch Blade");
			ItemLoot.setItemLore(item, "Rune Etched", TextFormat.DARKGREEN);
			return item;
		} else {
			item = new ItemStack(Item.swordIron);
			if(rand.nextBoolean()){
				item.addEnchantment(Enchantment.sharpness, 1);
			}
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
			item.setItemName("Tempered Blade");
			ItemLoot.setItemLore(item, "Highly Durable", TextFormat.DARKGREEN);
			return item;
		}
		
	}
	
	private static ItemStack getBow(Random rand, int rank){
		
		ItemStack item = new ItemStack(Item.bow);
		
		switch(rank){
		case 0:
			item.addEnchantment(Enchantment.power, 1 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, 1);
			item.setItemName("Yew Longbow");
			ItemLoot.setItemLore(item, "Superior craftsmanship", TextFormat.DARKGREEN);
			return item;
		case 1:
			item.addEnchantment(Enchantment.power, 1 + rand.nextInt(3));
			item.addEnchantment(Enchantment.unbreaking, 1 + rand.nextInt(3));
			item.setItemName("Laminated Bow");
			ItemLoot.setItemLore(item, "Highly polished", TextFormat.DARKGREEN);
			return item;
		case 2:
			item.addEnchantment(Enchantment.power, 3 + rand.nextInt(3));
			if(rand.nextBoolean()){
				item.addEnchantment(Enchantment.infinity, 1);
			}
			item.addEnchantment(Enchantment.unbreaking, 1 + rand.nextInt(3));
			item.setItemName("Recurve Bow");
			ItemLoot.setItemLore(item, "Beautifully crafted", TextFormat.DARKGREEN);
			return item;
		case 3:
			item.addEnchantment(Enchantment.power, 3 + rand.nextInt(3));
			item.addEnchantment(Enchantment.flame, 1);
			item.addEnchantment(Enchantment.infinity, 1);
			item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
			item.setItemName("Eldritch Bow");
			ItemLoot.setItemLore(item, "Warm to the touch", TextFormat.DARKGREEN);
			return item;
		default:
			return null;
		}
		
		
		
		
	}
	
	private static ItemStack getHelmet(Random rand, int rank){
		ItemStack item;
		
		String canonical = "";
		
		switch(rank){
		case 0:
			item = new ItemStack(Item.helmetLeather);
			ItemLoot.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Bonnet";
			break;
		case 1:
			item = new ItemStack(Item.helmetChain);
			canonical = "Coif";
			break;
		case 2:
			item = new ItemStack(Item.helmetIron);
			canonical = "Sallet";
			break;
		case 3:
			item = new ItemStack(Item.helmetDiamond);
			canonical = "Helm";
			break;
		default:
			item = new ItemStack(Item.helmetLeather);
		}
		
		
		String suffix = "";

		if(rand.nextInt(20) == 0){
			item.addEnchantment(Enchantment.protection, getProtectionLevel(rank, rand));
			item.addEnchantment(Enchantment.respiration, 3);
			item.addEnchantment(Enchantment.aquaAffinity, 1);
			suffix = "of diving";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchantment.projectileProtection, getProtectionLevel(rank, rand));
			suffix = "of the archer";
		} else {		
			item.addEnchantment(Enchantment.protection, getProtectionLevel(rank, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
		
		String name = getArmourPrefix(rank) + " " + canonical + " " + suffix;
		item.setItemName(name);
		return item;
	}
	
	
	private static ItemStack getBoots(Random rand, int rank){
		ItemStack item;
		
		String canonical = "";
		
		switch(rank){
		case 0:
			item = new ItemStack(Item.bootsLeather);
			ItemLoot.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Shoes";
			break;
		case 1:
			item = new ItemStack(Item.bootsChain);
			canonical = "Greaves";
			break;
		case 2:
			item = new ItemStack(Item.bootsIron);
			canonical = "Sabatons";
			break;
		case 3:
			item = new ItemStack(Item.bootsDiamond);
			canonical = "Boots";
			break;
		default:
			item = new ItemStack(Item.bootsLeather);
			canonical = "Shoes";
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(rank, rand));
			suffix = "of Warding";
		} else if(rand.nextInt(5) == 0){
			item.addEnchantment(Enchantment.protection, getProtectionLevel(rank, rand));
			item.addEnchantment(Enchantment.featherFalling, rank == 3 ? 4 : 1 + rand.nextInt(3));
			suffix = "of Lightness";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchantment.projectileProtection, getProtectionLevel(rank, rand));
			suffix = "of Archery";
		} else {
			item.addEnchantment(Enchantment.protection, getProtectionLevel(rank, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
		
		String name = getArmourPrefix(rank) + " " + canonical + " " + suffix;
		item.setItemName(name);
		return item;
	}
	
	
	private static ItemStack getLegs(Random rand, int rank){
		ItemStack item;
		
		String canonical = "";
		
		switch(rank){
		case 0:
			item = new ItemStack(Item.legsLeather);
			ItemLoot.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Pantaloons";
			break;
		case 1:
			item = new ItemStack(Item.legsChain);
			canonical = "Chausses";
			break;
		case 2:
			item = new ItemStack(Item.legsIron);
			canonical = "Leg-plates";
			break;
		case 3:
			item = new ItemStack(Item.legsDiamond);
			canonical = "Leggings";
			break;
		default:
			item = new ItemStack(Item.legsLeather);
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(rank, rand));
			suffix = "of Warding";
		} else if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.blastProtection, getProtectionLevel(rank, rand));
			suffix = "of Integrity";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchantment.projectileProtection, getProtectionLevel(rank, rand));
			suffix = "of Archery";
		} else {
			item.addEnchantment(Enchantment.protection, getProtectionLevel(rank, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
		
		String name = getArmourPrefix(rank) + " " + canonical + " " + suffix;
		item.setItemName(name);
		return item;
	}
	
	private static ItemStack getChest(Random rand, int rank){
		ItemStack item;
		
		String canonical = "";
		
		switch(rank){
		case 0:
			item = new ItemStack(Item.plateLeather);
			ItemLoot.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
			canonical = "Tunic";
			break;
		case 1:
			item = new ItemStack(Item.plateChain);
			canonical = "Hauberk";
			break;
		case 2:
			item = new ItemStack(Item.plateIron);
			canonical = "Cuirass";
			break;
		case 3:
			item = new ItemStack(Item.plateDiamond);
			canonical = "Armour";
			break;
		default:
			item = new ItemStack(Item.plateLeather);
			canonical = "Tunic";
		}

		String suffix = "";
		
		if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.fireProtection, getProtectionLevel(rank, rand));
			suffix = "of Flamewarding";
		} else if(rand.nextInt(10) == 0){
			item.addEnchantment(Enchantment.blastProtection, getProtectionLevel(rank, rand));
			suffix = "of Integrity";
		} else if(rand.nextInt(3) == 0){
			item.addEnchantment(Enchantment.projectileProtection, getProtectionLevel(rank, rand));
			suffix = "of Archery";
		} else {
			item.addEnchantment(Enchantment.protection, getProtectionLevel(rank, rand));
			suffix = "of Defense";
		}
		
		item.addEnchantment(Enchantment.unbreaking, getUnbreakingLevel(rank, rand));
		
		String name = getArmourPrefix(rank) + " " + canonical + " " + suffix;
		item.setItemName(name);
		return item;
	}
	
	private static int getUnbreakingLevel(int rank, Random rand){
		return rank == 3 ? 3 : 1 + rand.nextInt(2);
	}
	
	private static int getProtectionLevel(int rank, Random rand){
		
		int value = 1;
		
		switch(rank){
		case 0:
			if(rand.nextInt(3) == 0){
				value++;
			}
			break;
		case 1:
			if(rand.nextBoolean()){
				value++;
			}
			break;
		case 2:
			value += rand.nextInt(3);
			break;
		case 3:
			value += 3 + rand.nextInt(3);
			break;
		}
		
		return value;
	}
	
	private static String getArmourPrefix(int rank){
		
		switch(rank){
		case 0:
			return "Surplus";
		case 1:
			return "Riveted";
		case 2:
			return "Gothic";
		case 3:
			return "Magic";
		default:
			return "Strange";
		}
	}		
}
