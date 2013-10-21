package greymerk.roguelike;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public enum ItemNovelty {

	GREYMERK, ANDERZEL, NEBRIS, ZISTEAUPANTS, ZISTEAUSIGN, ZISTEAUSIGN2, AVIDYA, ASHLEA, KURT, AMLP,
	CLEO, ENIKOSWORD, ENIKOBOW, BDOUBLEO, GUUDE, RLEAHY, ETHO, BAJ, DOCM, GINGER, VECHS, VECHS2,
	NOTCH, JOHNNYRAGGOT, QUANTUMLEAP, MCGAMER, GENERIKB, PAUSE, PAULSOARESJR, FOURLES, DINNERBONE, GRIM, MMILLSS, MILLBEE;
		
	public static final Map<String, ItemNovelty> names;
	static {
		names = new HashMap<String, ItemNovelty>();
		names.put("greymerk", ItemNovelty.GREYMERK);
		names.put("anderzel", ItemNovelty.ANDERZEL);
		names.put("nebris", ItemNovelty.NEBRIS);
		names.put("zisteaupants", ItemNovelty.ZISTEAUPANTS);
		names.put("zisteausign", ItemNovelty.ZISTEAUSIGN);
		names.put("zisteausign2", ItemNovelty.ZISTEAUSIGN2);
		names.put("avidya", ItemNovelty.AVIDYA);
		names.put("ashlea", ItemNovelty.ASHLEA);
		names.put("kurt", ItemNovelty.KURT);
		names.put("amlp", ItemNovelty.AMLP);
		names.put("cleo", ItemNovelty.CLEO);
		names.put("enikosword", ItemNovelty.ENIKOSWORD);
		names.put("enikobow", ItemNovelty.ENIKOBOW);
		names.put("bdoubleo", ItemNovelty.BDOUBLEO);
		names.put("guude", ItemNovelty.GUUDE);
		names.put("rleahy", ItemNovelty.RLEAHY);
		names.put("etho", ItemNovelty.ETHO);
		names.put("baj", ItemNovelty.BAJ);
		names.put("docm", ItemNovelty.DOCM);
		names.put("ginger", ItemNovelty.GINGER);
		names.put("vechs", ItemNovelty.VECHS);
		names.put("vechs2", ItemNovelty.VECHS2);
		names.put("notch", ItemNovelty.NOTCH);
		names.put("johnnyraggot", ItemNovelty.JOHNNYRAGGOT);
		names.put("quantumleap", ItemNovelty.QUANTUMLEAP);
		names.put("mcgamer", ItemNovelty.MCGAMER);
		names.put("generikb", ItemNovelty.GENERIKB);
		names.put("pause", ItemNovelty.PAUSE);
		names.put("paulsoaresjr", ItemNovelty.PAULSOARESJR);
		names.put("fourles", ItemNovelty.FOURLES);
		names.put("dinnerbone", ItemNovelty.DINNERBONE);
		names.put("grim", ItemNovelty.GRIM);
		names.put("mmillss", ItemNovelty.MMILLSS);
		names.put("millbee", ItemNovelty.MILLBEE);
	};
		
	public static ItemStack getItemByName(String name){
		return getItem(names.get(name));
	}
	
	public static ItemStack getItem(ItemNovelty choice){
		
		ItemStack item;
		
		switch(choice){
		
		case GREYMERK:
			item = new ItemStack(Item.axeGold);
			ItemLoot.setItemName(item, "Greymerk's Hatchet", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Made for war", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.unbreaking, 100);
			return item;
		case ANDERZEL:
			item = new ItemStack(Item.plateChain);
			ItemLoot.setItemName(item, "AnderZEL's Battle Garb", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Protection against sneaky ninja", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.projectileProtection, 4);
			item.addEnchantment(Enchantment.unbreaking, 100);
			return item;
		case NEBRIS:
			item = new ItemStack(Item.helmetGold);
			ItemLoot.setItemName(item, "Nebris' Gold Crown", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Dressed for success", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.protection, 4);
			item.addEnchantment(Enchantment.unbreaking, 100);
			return item;
		case ZISTEAUPANTS:
			item = new ItemStack(Item.legsLeather);
			ItemLoot.setItemName(item, "Zisteau's Man Pants", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Yessss, Manpants!", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.fireProtection, 4);
			item.addEnchantment(Enchantment.unbreaking, 100);
			ItemLoot.dyeArmor(item, 250, 128, 114);
			return item;
		case ZISTEAUSIGN:
			item = new ItemStack(Item.sign);
			ItemLoot.setItemName(item, "Zistonian Battle Sign", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Say g'bye spawnah!\"", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 1);
			item.addEnchantment(Enchantment.knockback, 2);
			item.addEnchantment(Enchantment.fireAspect, 1);
			return item;
		case ZISTEAUSIGN2:
			item = new ItemStack(Item.sign);
			ItemLoot.setItemName(item, "Zistonian Battle Sign II", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Say g'bye spawnah!\"", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 5);
			item.addEnchantment(Enchantment.knockback, 3);
			item.addEnchantment(Enchantment.fireAspect, 2);
			return item;
		case AVIDYA:
			item = new ItemStack(Item.bucketMilk);
			ItemLoot.setItemName(item, "Avidya's white russian", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "The dude's favourite", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.baneOfArthropods, 4);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.fireAspect, 1);
			return item;
		case ASHLEA:
			item = new ItemStack(Item.cookie);
			ItemLoot.setItemName(item, "Ashlea's Oatmeal Cookie", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Perfect for elevensies", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.knockback, 1);
			return item;
		case KURT:
			item = new ItemStack(Item.bootsLeather);
			ItemLoot.setItemName(item, "Kurt's Farland Travellers", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Indeed!", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.protection, 2);
			item.addEnchantment(Enchantment.featherFalling, 2);
			item.addEnchantment(Enchantment.unbreaking, 100);
			ItemLoot.dyeArmor(item, 165, 42, 42);
			return item;
		case AMLP:
			item = new ItemStack(Item.shears);
			ItemLoot.setItemName(item, "Amlpian Lascerator", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "The wool collector", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 3);
			item.addEnchantment(Enchantment.knockback, 2);
			item.addEnchantment(Enchantment.fireAspect, 1);
			return item;
		case CLEO:
			item = new ItemStack(Item.fishRaw);
			ItemLoot.setItemName(item, "Cleophian Digging Feesh", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Feesh are not efeeshent for digging", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.efficiency, 10);
			item.addEnchantment(Enchantment.knockback, 5);
			item.addEnchantment(Enchantment.fortune, 10);
			item.addEnchantment(Enchantment.unbreaking, 10);
			return item;
		case BDOUBLEO:
			item = new ItemStack(Item.shovelDiamond);
			ItemLoot.setItemName(item, "BdoubleO's Dig Job", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Recovered from hell's blazes", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.efficiency, 6);
			item.addEnchantment(Enchantment.unbreaking, 100);
			return item;
		case GUUDE:
			item = new ItemStack(Item.record13);
			ItemLoot.setItemName(item, "Boulderfistian Golden Record", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"You're Watching Guude Boulderfist...\"", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 3);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.blastProtection, 3);
			return item;
		case RLEAHY:
			item = new ItemStack(Item.bread);
			ItemLoot.setItemName(item, "Rleahian battle sub", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "With extra pastrami", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.fireAspect, 2);
			return item;
		case ETHO:
			item = new ItemStack(Item.pickaxeWood);
			ItemLoot.setItemName(item, "Etho's First Pick", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Barely used...", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.efficiency, 5);
			item.addEnchantment(Enchantment.unbreaking, 100);
			return item;
		case ENIKOBOW:
			item = new ItemStack(Item.bow);
			ItemLoot.setItemName(item, "Eniko's String Theory", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "For Science!", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.power, 5);
			item.addEnchantment(Enchantment.knockback, 2);
			item.addEnchantment(Enchantment.infinity, 1);
			item.addEnchantment(Enchantment.unbreaking, 100);
			return item;
		case ENIKOSWORD:
			item = new ItemStack(Item.swordGold);
			ItemLoot.setItemName(item, "Eniko's Earring", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"She do the loot take boogie\"", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 5);
			item.addEnchantment(Enchantment.looting, 3);
			item.addEnchantment(Enchantment.unbreaking, 100);
			return item;
		case BAJ:
			item = new ItemStack(Item.hoeGold);
			ItemLoot.setItemName(item, "Baj's Last Resort", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Watching grass grow", TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.fortune, 5);
			return item;
		case DOCM:
			item = new ItemStack(Item.fishingRod);
			ItemLoot.setItemName(item, "DocM's Rod of Command", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Get to the dang land!\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 3);
			item.addEnchantment(Enchantment.knockback, 1);
			return item;
		case GINGER:
			item = new ItemStack(Item.chickenCooked);
			ItemLoot.setItemName(item, "Ginger's Spicy Chicken", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Five Alarm",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.fireAspect, 3);
			item.addEnchantment(Enchantment.sharpness, 1);
			return item;
		case VECHS:
			item = new ItemStack(Item.stick);
			ItemLoot.setItemName(item, "Vechsing Stick", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Really?!\"",  TextFormat.DARKGREEN);
			return item;
		case VECHS2:
			item = new ItemStack(Item.blazeRod);
			ItemLoot.setItemName(item, "Vechsian Rod", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Not again!\"",  TextFormat.DARKGREEN);
			return item;
		case NOTCH:
			item = new ItemStack(Item.appleRed);
			ItemLoot.setItemName(item, "Notch's apple", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Imbued with the creator's power",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 10);
			item.addEnchantment(Enchantment.knockback, 10);
			return item;
		case JOHNNYRAGGOT:
			item = new ItemStack(Item.paper);
			ItemLoot.setItemName(item, "Johnnyraggot's Loot Card", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "Depicts a facemelting zombie",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.looting, 2);
			return item;
		case QUANTUMLEAP:
			item = new ItemStack(Block.sponge);
			ItemLoot.setItemName(item, "QuantumLeap's Swiss Cheese", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Oh boy\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 4);
			return item;
		case MCGAMER:
			item = new ItemStack(Item.dyePowder, 1, 4);
			ItemLoot.setItemName(item, "McGamer's Precious", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Who needs diamonds?\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.knockback, 1);
			return item;
		case GENERIKB:
			item = new ItemStack(Item.bakedPotato);
			ItemLoot.setItemName(item, "GenerikB's Hot Potato", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "All a hermit needs",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.fireAspect, 3);
			item.addEnchantment(Enchantment.knockback, 1);
			return item;
		case PAUSE:
			item = new ItemStack(Item.bow);
			ItemLoot.setItemName(item, "Pauseunpause endless repeater", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"OMG WTF\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.power, 3);
			item.addEnchantment(Enchantment.flame, 2);
			item.addEnchantment(Enchantment.infinity, 1);
			item.addEnchantment(Enchantment.unbreaking, 100);
			return item;
		case PAULSOARESJR:
			item = new ItemStack(Item.feather);
			ItemLoot.setItemName(item, "Soarian Bonker", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Aaaaauuaahh!!\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.knockback, 10);
			return item;
		case FOURLES:
			item = new ItemStack(Item.dyePowder, 1, 3);
			ItemLoot.setItemName(item, "Fourles Darkroast Beans", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Mmmm... Dark Roast\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.fireAspect, 2);
			item.addEnchantment(Enchantment.sharpness, 2);
			return item;
		case DINNERBONE:
			item = new ItemStack(Item.bone, 1);
			ItemLoot.setItemName(item, "Old Dinnerbone", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Dang Skellies!\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.sharpness, 4);
			item.addEnchantment(Enchantment.fireAspect, 2);
			return item;
		case GRIM:
			item = new ItemStack(Item.rottenFlesh);
			ItemLoot.setItemName(item, "Grim's chew-toy", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Come on Grim, let's do this!\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.smite, 2);
			item.addEnchantment(Enchantment.looting, 1);
			return item;
		case MMILLSS:
			item = new ItemStack(Block.cactus);
			ItemLoot.setItemName(item, "MMillssian spider bane", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"I really don't need anymore string...\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.baneOfArthropods, 4);
			item.addEnchantment(Enchantment.thorns, 2);
			item.addEnchantment(Enchantment.looting, 1);
			return item;
		case MILLBEE:
			item = new ItemStack(Item.plateLeather);
			ItemLoot.setItemName(item, "Millbee's Cosy Fleece", TextFormat.DARKPURPLE);
			ItemLoot.setItemLore(item, "\"Warm and fuzzy\"",  TextFormat.DARKGREEN);
			item.addEnchantment(Enchantment.protection, 4);
			item.addEnchantment(Enchantment.unbreaking, 100);
			ItemLoot.dyeArmor(item, 250, 128, 114);
			return item;
		default:
			return null;
		
		}
	}
	
	
}
