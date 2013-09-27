package greymerk.roguelike;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public enum ItemNovelty {

	GREYMERK, GREYMERK2, ANDERZEL, NEBRIS, ZISTEAUPANTS, ZISTEAUSIGN, ZISTEAUSIGN2, AVIDYA, ASHLEA, KURT, AMLP,
	CLEO, ENIKOSWORD, ENIKOBOW, BOOROCKJOB, BOODIGJOB, GUUDE, RLEAHY, ETHO, BAJ, DOCM, GINGER, VECHS, VECHS2,
	NOTCH, JOHNNYRAGGOT, QUANTUMLEAP, MCGAMER, GENERIKB, PAUSE, PAULSOARESJR, FOURLES, DINNERBONE, GRIM, MMILLSS;
		
	private static final int SIZE = ItemNovelty.values().length;
	private static ItemNovelty[] RANK0 = {
		GRIM, MMILLSS, DOCM, ZISTEAUPANTS, ZISTEAUSIGN, ASHLEA, MCGAMER
	};
	private static ItemNovelty[] RANK1 = {
		VECHS, GREYMERK, ANDERZEL, AVIDYA, KURT, AMLP, CLEO, GUUDE, RLEAHY, BAJ,
		DOCM, JOHNNYRAGGOT, QUANTUMLEAP, PAULSOARESJR, ZISTEAUPANTS, ZISTEAUSIGN,
		ASHLEA, ETHO, BAJ, GINGER, MCGAMER, FOURLES, MMILLSS
	};
	private static ItemNovelty[] RANK2 = {
		VECHS, GREYMERK, NEBRIS, AVIDYA, AMLP, GUUDE,
		QUANTUMLEAP, GENERIKB, PAUSE, PAULSOARESJR, DINNERBONE, MMILLSS
	};
	private static ItemNovelty[] RANK3 = {
		GREYMERK2, VECHS2, ENIKOBOW, BOOROCKJOB, BOODIGJOB, DINNERBONE, ENIKOSWORD, ZISTEAUSIGN2
	};
	
	
	
	public static ItemStack getItemByRank(Random rand, int rank){
				
		switch(rank){
			case 3:
				return getItem(RANK3[rand.nextInt(RANK3.length)]);
			case 2:
				return getItem(RANK2[rand.nextInt(RANK2.length)]);
			case 1:
				return getItem(RANK1[rand.nextInt(RANK1.length)]);
			case 0:
				return getItem(RANK0[rand.nextInt(RANK0.length)]);
			default:
				return getItem(RANK0[rand.nextInt(RANK0.length)]);
		}
	}
	
	
	public static ItemStack getItem(ItemNovelty choice){
		
		ItemStack item;
		
		switch(choice){
		
		case GREYMERK:
			item = new ItemStack(Item.axeIron);
			item.setItemName("§5Greymerk's Hatchet");
			ItemLoot.setItemLore(item, "§2Made for war");
			item.addEnchantment(Enchantment.sharpness, 3);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.unbreaking, 3);
			return item;
		case GREYMERK2:
			item = new ItemStack(Item.axeDiamond);
			item.setItemName("§5Greymerk's Battle-Axe");
			ItemLoot.setItemLore(item, "§2Made for war");
			item.addEnchantment(Enchantment.sharpness, 7);
			item.addEnchantment(Enchantment.knockback, 2);
			item.addEnchantment(Enchantment.unbreaking, 10);
			return item;
		case ANDERZEL:
			item = new ItemStack(Item.plateChain);
			item.setItemName("§5AnderZEL's Battle Garb");
			ItemLoot.setItemLore(item, "§2Protection against sneaky ninja");
			item.addEnchantment(Enchantment.projectileProtection, 4);
			item.addEnchantment(Enchantment.unbreaking, 3);
			return item;
		case NEBRIS:
			item = new ItemStack(Item.helmetGold);
			item.setItemName("§5Nebris' Gold Crown");
			ItemLoot.setItemLore(item, "§2Dressed for success");
			item.addEnchantment(Enchantment.protection, 4);
			item.addEnchantment(Enchantment.unbreaking, 3);
			return item;
		case ZISTEAUPANTS:
			item = new ItemStack(Item.legsLeather);
			item.setItemName("§5Zisteau's Man Pants");
			ItemLoot.setItemLore(item, "§2Yessss, Manpants!");
			item.addEnchantment(Enchantment.fireProtection, 4);
			item.addEnchantment(Enchantment.unbreaking, 3);
		case ZISTEAUSIGN:
			item = new ItemStack(Item.sign);
			item.setItemName("§5Zistonian Battle Sign");
			ItemLoot.setItemLore(item, "§2\"Say g'bye spawnah!\"");
			item.addEnchantment(Enchantment.sharpness, 1);
			item.addEnchantment(Enchantment.knockback, 2);
			item.addEnchantment(Enchantment.fireAspect, 1);
		case ZISTEAUSIGN2:
			item = new ItemStack(Item.sign);
			item.setItemName("§6Zistonian Battle Sign II");
			ItemLoot.setItemLore(item, "§2\"Say g'bye spawnah!\"");
			item.addEnchantment(Enchantment.sharpness, 5);
			item.addEnchantment(Enchantment.knockback, 3);
			item.addEnchantment(Enchantment.fireAspect, 2);	
		case AVIDYA:
			item = new ItemStack(Item.bucketMilk);
			item.setItemName("§5Avidya's white russian");
			ItemLoot.setItemLore(item, "§2The dude's favourite");
			item.addEnchantment(Enchantment.baneOfArthropods, 4);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.fireAspect, 1);
			return item;
		case ASHLEA:
			item = new ItemStack(Item.cookie);
			item.setItemName("§5Ashlea's Oatmeal Cookie");
			ItemLoot.setItemLore(item, "§2Perfect for elevensies");
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.knockback, 1);
			return item;
		case KURT:
			item = new ItemStack(Item.bootsLeather);
			item.setItemName("§5Kurt's Farland Travellers");
			ItemLoot.setItemLore(item, "§2Indeed!");
			item.addEnchantment(Enchantment.protection, 2);
			item.addEnchantment(Enchantment.featherFalling, 2);
			item.addEnchantment(Enchantment.unbreaking, 3);
			return item;
		case AMLP:
			item = new ItemStack(Item.shears);
			item.setItemName("§5Amlpian Lascerator");
			ItemLoot.setItemLore(item, "§2Milbee approved");
			item.addEnchantment(Enchantment.sharpness, 3);
			item.addEnchantment(Enchantment.knockback, 2);
			item.addEnchantment(Enchantment.fireAspect, 1);
			return item;
		case CLEO:
			item = new ItemStack(Item.fishRaw);
			item.setItemName("§5Cleophian Digging Feesh");
			ItemLoot.setItemLore(item, "§2Feesh are not efeeshent for digging");
			item.addEnchantment(Enchantment.efficiency, 10);
			item.addEnchantment(Enchantment.knockback, 5);
			item.addEnchantment(Enchantment.fortune, 10);
			item.addEnchantment(Enchantment.unbreaking, 10);
			return item;
		case BOOROCKJOB:
			item = new ItemStack(Item.pickaxeDiamond);
			item.setItemName("§6BdoubleO's Rock Job");
			ItemLoot.setItemLore(item, "§2Recovered from hell's blazes");
			item.addEnchantment(Enchantment.efficiency, 7);
			item.addEnchantment(Enchantment.unbreaking, 10);
			return item;
		case BOODIGJOB:
			item = new ItemStack(Item.shovelDiamond);
			item.setItemName("§6BdoubleO's Dig Job");
			ItemLoot.setItemLore(item, "§2Recovered from hell's blazes");
			item.addEnchantment(Enchantment.efficiency, 7);
			item.addEnchantment(Enchantment.unbreaking, 10);
			return item;
		case GUUDE:
			item = new ItemStack(Item.record13);
			item.setItemName("§5Boulderfistian Golden Record");
			ItemLoot.setItemLore(item, "§2\"You're Watching Guude Boulderfist...\"");
			item.addEnchantment(Enchantment.sharpness, 3);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.blastProtection, 3);
			return item;
		case RLEAHY:
			item = new ItemStack(Item.bread);
			item.setItemName("§5Rleahian battle sub");
			ItemLoot.setItemLore(item, "§2With extra pastrami");
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.fireAspect, 2);
			return item;
		case ETHO:
			item = new ItemStack(Item.pickaxeWood);
			item.setItemName("§5Etho's First Pick");
			ItemLoot.setItemLore(item, "§2Barely used...");
			item.addEnchantment(Enchantment.efficiency, 5);
			item.addEnchantment(Enchantment.unbreaking, 3);
			return item;
		case ENIKOBOW:
			item = new ItemStack(Item.bow);
			item.setItemName("§6Eniko's String Theory");
			ItemLoot.setItemLore(item, "§2For Science!");
			item.addEnchantment(Enchantment.power, 7);
			item.addEnchantment(Enchantment.knockback, 2);
			item.addEnchantment(Enchantment.infinity, 1);
			item.addEnchantment(Enchantment.unbreaking, 10);
			return item;
		case ENIKOSWORD:
			item = new ItemStack(Item.swordDiamond);
			item.setItemName("§6Eniko's Earring");
			ItemLoot.setItemLore(item, "§2\"She do the loot take boogie\"");
			item.addEnchantment(Enchantment.sharpness, 7);
			item.addEnchantment(Enchantment.looting, 3);
			item.addEnchantment(Enchantment.unbreaking, 10);
			return item;
		case BAJ:
			item = new ItemStack(Item.hoeGold);
			item.setItemName("§5Baj's Last Resort");
			ItemLoot.setItemLore(item, "§2Watching grass grow");
			item.addEnchantment(Enchantment.sharpness, 3);
			item.addEnchantment(Enchantment.knockback, 2);
			item.addEnchantment(Enchantment.fortune, 5);
			return item;
		case DOCM:
			item = new ItemStack(Item.fishingRod);
			item.setItemName("§5DocM's Rod of Command");
			ItemLoot.setItemLore(item, "§2\"Get to the dang land!\"");
			item.addEnchantment(Enchantment.sharpness, 3);
			item.addEnchantment(Enchantment.knockback, 1);
			return item;
		case GINGER:
			item = new ItemStack(Item.chickenCooked);
			item.setItemName("§5Ginger's Spicy Chicken");
			ItemLoot.setItemLore(item, "§2Five Alarm");
			item.addEnchantment(Enchantment.knockback, 1);
			item.addEnchantment(Enchantment.fireAspect, 3);
			item.addEnchantment(Enchantment.sharpness, 1);
			return item;
		case VECHS:
			item = new ItemStack(Item.stick);
			item.setItemName("§5Vechsing Stick");
			ItemLoot.setItemLore(item, "§2\"Really?!\"");
			return item;
		case VECHS2:
			item = new ItemStack(Item.blazeRod);
			item.setItemName("§6Vechsian Rod");
			ItemLoot.setItemLore(item, "§2\"Not again!\"");
			return item;
		case NOTCH:
			item = new ItemStack(Item.appleRed);
			item.setItemName("§5Notch's apple");
			ItemLoot.setItemLore(item, "§2Imbued with the creator's power");
			item.addEnchantment(Enchantment.sharpness, 10);
			item.addEnchantment(Enchantment.knockback, 10);
			return item;
		case JOHNNYRAGGOT:
			item = new ItemStack(Item.paper);
			item.setItemName("§5Johnnyraggot's Loot Card");
			ItemLoot.setItemLore(item, "§2Depicts a facemelting zombie");
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.looting, 2);
			return item;
		case QUANTUMLEAP:
			item = new ItemStack(Block.sponge);
			item.setItemName("§5QuantumLeap's Swiss Cheese");
			ItemLoot.setItemLore(item, "§2\"Oh boy\"");
			item.addEnchantment(Enchantment.sharpness, 4);
			return item;
		case MCGAMER:
			item = new ItemStack(Item.dyePowder, 1, 4);
			item.setItemName("§5McGamer's Precious");
			ItemLoot.setItemLore(item, "§2\"Who needs diamonds?\"");
			item.addEnchantment(Enchantment.sharpness, 2);
			item.addEnchantment(Enchantment.knockback, 1);
			return item;
		case GENERIKB:
			item = new ItemStack(Item.bakedPotato);
			item.setItemName("§5GenerikB's Hot Potato");
			ItemLoot.setItemLore(item, "§2All a hermit needs");
			item.addEnchantment(Enchantment.fireAspect, 3);
			item.addEnchantment(Enchantment.knockback, 1);
			return item;
		case PAUSE:
			item = new ItemStack(Item.bow);
			item.setItemName("§5Pauseunpause endless repeater");
			ItemLoot.setItemLore(item, "§2\"OMG WTF\"");
			item.addEnchantment(Enchantment.power, 3);
			item.addEnchantment(Enchantment.flame, 2);
			item.addEnchantment(Enchantment.infinity, 1);
			item.addEnchantment(Enchantment.unbreaking, 3);
			return item;
		case PAULSOARESJR:
			item = new ItemStack(Item.shovelIron);
			item.setItemName("§5Soarian Bonker");
			ItemLoot.setItemLore(item, "§2\"Aaaaauuaahh!!\"");
			item.addEnchantment(Enchantment.knockback, 10);
			item.addEnchantment(Enchantment.unbreaking, 10);
			return item;
		case FOURLES:
			item = new ItemStack(Item.dyePowder, 1, 3);
			item.setItemName("§5Fourles Darkroast Beans");
			ItemLoot.setItemLore(item, "§2\"Mmmm... Dark Roast\"");
			item.addEnchantment(Enchantment.fireAspect, 2);
			item.addEnchantment(Enchantment.sharpness, 2);
			return item;
		case DINNERBONE:
			item = new ItemStack(Item.bone, 1);
			item.setItemName("§5Old Dinnerbone");
			ItemLoot.setItemLore(item, "§2\"Dang Skellies!\"");
			item.addEnchantment(Enchantment.sharpness, 5);
			item.addEnchantment(Enchantment.fireAspect, 2);
			return item;
		case GRIM:
			item = new ItemStack(Item.rottenFlesh);
			item.setItemName("§5Grim's chew-toy");
			ItemLoot.setItemLore(item, "§2\"Come on Grim, let's do this!\"");
			item.addEnchantment(Enchantment.smite, 2);
			item.addEnchantment(Enchantment.looting, 1);
			return item;
		case MMILLSS:
			item = new ItemStack(Block.cactus);
			item.setItemName("§5MMillssian spider bane");
			ItemLoot.setItemLore(item, "§2\"I really don't need anymore string...\"");
			item.addEnchantment(Enchantment.baneOfArthropods, 4);
			item.addEnchantment(Enchantment.thorns, 2);
			item.addEnchantment(Enchantment.looting, 1);
			return item;
		default:
			return null;
		
		}
	}
	
	
}
