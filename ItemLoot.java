package greymerk.roguelike;

import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.EnchantmentData;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;

public final class ItemLoot {

	public static final int WEAPON = 0;
	public static final int HEAD = 1;
	public static final int CHEST = 2;
	public static final int LEGS = 3;
	public static final int FEET = 4;
	
	public static final int WOOD = 0;
	public static final int STONE = 1;
	public static final int IRON = 2;
	public static final int GOLD = 3;
	public static final int DIAMOND = 4;

	public static ItemStack getEquipmentBySlot(Random rand, int slot, int rank, boolean enchant){
		
		ItemStack item;
		
		if(slot == WEAPON){
			if(rand.nextInt(10) == 0){
				return getBow(rand, rank, true);
			} else {
				return getSword(rand, rank);
			}
		}
		
		if(rand.nextInt(20 + (rank * 10)) == 0){
			switch(slot){
			case HEAD:
				return ItemSpecialty.getItem(ItemSpecialty.HELMET, rand, rank); 
			case CHEST:
				return ItemSpecialty.getItem(ItemSpecialty.CHEST, rand, rank); 
			case LEGS:
				return ItemSpecialty.getItem(ItemSpecialty.LEGS, rand, rank); 
			case FEET:
				return ItemSpecialty.getItem(ItemSpecialty.FEET, rand, rank); 
			}
		}
	
		switch(slot){
		case HEAD:
			if(rank > 1 && rand.nextInt(300) == 0){
				return ItemNovelty.getItem(ItemNovelty.NEBRIS);
			}
			break; 
		case CHEST:
			if(rank == 1 && rand.nextInt(300) == 0){
				return ItemNovelty.getItem(ItemNovelty.ANDERZEL);
			}
			break;
		case LEGS:
			if(rank < 2 && rand.nextInt(300) == 0){
				return ItemNovelty.getItem(ItemNovelty.ZISTEAUPANTS);
			}
			break;
		case FEET:
			if(rank < 2 && rand.nextInt(300) == 0){
				return ItemNovelty.getItem(ItemNovelty.KURT);
			}
			break;
		}
		
		return getArmour(rand, rank, slot, enchant);
		
	}
	
	
	
	public static ItemStack getBow(Random rand, int rank, boolean enchant){
		
		if(rank == 3 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.ENIKOBOW);
		}
		
		if(rank > 1 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.PAUSE);
		}
		
		if(rand.nextInt(20 + (rank * 10)) == 0){
			return ItemSpecialty.getItem(ItemSpecialty.BOW, rand, rank);
		}
		
		ItemStack bow = new ItemStack(Item.bow);
		
		if(enchant && rand.nextInt(6 - rank) == 0){
			enchantItem(bow, rand, getEnchantLevel(rank));
		}
		
		return bow;
		
	}
	
	public static ItemStack getOre(Random rand, int rank){
		
		if(rank < 2 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.MCGAMER);
		}
		return pickOre(rand, rank);
	}
	
	public static ItemStack getSword(Random rand, int rank){
		return getSword(rand, rank, true);
	}
	
	public static ItemStack getSword(Random rand, int rank, boolean enchant){
		ItemStack sword;
		
		if(enchant && rand.nextInt(10 + (rank * 10)) == 0){
			return ItemSpecialty.getItem(ItemSpecialty.SWORD, rand, rank);
		}
		
		sword = pickSword(rand, rank);
		
		if(enchant && rand.nextInt(6 - rank) == 0){
			enchantItem(sword, rand, getEnchantLevel(rank));
		}
		
		return sword;		
	}
	
	public static ItemStack getArmour(Random rand, int rank, int slot, boolean enchant){

		if(rand.nextInt(100) == 0){
			if(enchant && slot == HEAD && rank > 1){
				return ItemNovelty.getItem(ItemNovelty.NEBRIS);
			}
			
			if(enchant && slot == FEET && rank < 2){
				return ItemNovelty.getItem(ItemNovelty.KURT);
			}
			
			if(enchant && slot == CHEST && rank == 2){
				return ItemNovelty.getItem(ItemNovelty.ANDERZEL);
			}
			
			if(enchant && slot == LEGS && rank == 0){
				return ItemNovelty.getItem(ItemNovelty.ZISTEAUPANTS);
			}
		}

		ItemStack item = pickArmour(rand, slot, rank);
		
		if(enchant && rand.nextInt(6 - rank) == 0){
			enchantItem(item, rand, getEnchantLevel(rank));
		}
		
		return item;
		
	}

	
	public static ItemStack getEnchantedBook(Random rand, int rank){
		
		ItemStack book = new ItemStack(Item.book);
		
		enchantItem(book, rand, getEnchantLevel(rank));
		
		return book;
	}
	
	public static ItemStack getPotion(Random rand, int rank){
		
		if(rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.AVIDYA);
		}
				
		return pickPotion(rand, rank);
	}
	
	public static ItemStack getFood(Random rand, int rank){
		
		ItemNovelty[] items = {ItemNovelty.RLEAHY, ItemNovelty.GINGER, ItemNovelty.GENERIKB, ItemNovelty.ASHLEA, ItemNovelty.CLEO};
		
		if(rank > 0 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(items[rand.nextInt(items.length)]);
		}
		
		return pickFood(rand, rank);
	}
	
	public static ItemStack getTool(Random rand, int rank){
		return getTool(rand, rank, true);
	}
	
	public static ItemStack getTool(Random rand, int rank, boolean enchant){
		
		if(enchant && rand.nextInt(20 + (rank * 10)) == 0){
			switch(rand.nextInt(3)){
			case 0:
				return ItemSpecialty.getItem(ItemSpecialty.PICK, rand, rank);
			case 1:
				return ItemSpecialty.getItem(ItemSpecialty.AXE, rand, rank);
			case 2:
				return ItemSpecialty.getItem(ItemSpecialty.SHOVEL, rand, rank);
			}
			
		}
		
		if(enchant && rand.nextInt(100) == 0){
			switch(rank){
			case 0:
				return ItemNovelty.getItem(ItemNovelty.ETHO);
			case 1:
				if(rand.nextBoolean()){
					return ItemNovelty.getItem(ItemNovelty.GREYMERK);
				} else {
					return ItemNovelty.getItem(ItemNovelty.BAJ);
				}
			case 2:
				return ItemNovelty.getItem(ItemNovelty.AMLP);
			case 3:
				if(rand.nextBoolean()){
					return ItemNovelty.getItem(ItemNovelty.BOODIGJOB);
				} else {
					return ItemNovelty.getItem(ItemNovelty.BOOROCKJOB);
				}
			}
		}
		
		ItemStack tool;
		
		tool = pickTool(rand, rank);
		
		if(!enchant){
			return tool;
		}
		
		if(rand.nextInt(6 - rank) == 0){
			enchantItem(tool, rand, getEnchantLevel(rank));
		}
		
		return tool;
	}
	
	public static ItemStack getBlocks(Random rand, int rank){
		
		if(rank < 2 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.MMILLSS);
		}
		
		if(rank == 2 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.QUANTUMLEAP);
		}
		
		return pickBlocks(rand, rank);
	}
	
	public static ItemStack getSupplyItem(Random rand, int rank){
		
		ItemNovelty[] items = {ItemNovelty.GUUDE, ItemNovelty.BAJ, ItemNovelty.JOHNNYRAGGOT, ItemNovelty.FOURLES};
		
		if(rand.nextInt(200) == 0){
			return ItemNovelty.getItem(items[rand.nextInt(items.length)]);
		}
		
		if(rand.nextInt(10) == 0){
			return pickRecord(rand);
		}
		
		if(rand.nextInt(10) == 0){
			return getBlocks(rand, rank);
		}
		
		return pickSupplyItem(rand);
	}
	
	public static ItemStack getStarterLoot(Random rand){
		
		ItemStack item;
		
		switch (rand.nextInt(6)){
		case 5:
			return getTool(rand, 0, false);
		case 4:
			return new ItemStack(Item.swordStone);
		case 3:
			return pickBlocks(rand, 0);	
		case 2:
			return pickFood(rand, 0);
		case 1:
			return ItemSpecialty.getItem(ItemSpecialty.LEGS, rand, 0);
		default:
			return new ItemStack(Block.torchWood, 1 + rand.nextInt(10));
		}
		
	}

	public static ItemStack getJunk(Random rand, int rank){
		
		if(rand.nextInt(1000) == 0){
			
			if(rand.nextBoolean()){
				return ItemNovelty.getItem(ItemNovelty.VECHS);
			}
			
			if(rank == 0){
				return ItemNovelty.getItem(ItemNovelty.GRIM);
			}
			
			if(rank == 1){
				return ItemNovelty.getItem(ItemNovelty.ZISTEAUSIGN);
			}
			
			if(rank == 3){
				return ItemNovelty.getItem(ItemNovelty.DINNERBONE);
			}
		}
		
		if(rand.nextInt(20) == 0){
			switch(rand.nextInt(3)){
			case 0:
				return new ItemStack(Item.redstone);
			case 1:
				return new ItemStack(Item.glowstone);
			case 2:
				return new ItemStack(Item.gunpowder);			
			}
		}
		
		
		if(rand.nextInt(10) == 0){
			switch(rand.nextInt(6)){
			case 0:
				return new ItemStack(Item.slimeBall);
			case 1:
				return new ItemStack(Item.snowball);
			case 2:
				return new ItemStack(Item.bowlEmpty);
			case 3:
				return new ItemStack(Item.clay);
			case 4:
				return new ItemStack(Item.flint);
			case 5:
				return new ItemStack(Item.feather);
			}
		}
		
		switch(rand.nextInt(5)){
		case 0:
			return new ItemStack(Item.bone);
		case 1:
			return new ItemStack(Item.rottenFlesh);
		case 2:
			return new ItemStack(Item.spiderEye);
		case 3:
			return new ItemStack(Item.stick);
		case 4:
			return new ItemStack(Item.silk);
		default:
			return new ItemStack(Item.stick);
		}
	}

	private static ItemStack pickPotion(Random rand, int rank) {
		return Potion.getRandom(rand, rank);
	}
	
	private static ItemStack pickSword(Random rand, int rank){
		
		int quality = getQuality(rand, rank);
		
		switch (quality) {

		case DIAMOND:
			return new ItemStack(Item.swordDiamond);
		case GOLD:
			return new ItemStack(Item.swordGold);
		case IRON:
			return new ItemStack(Item.swordIron);
		case STONE:
			return new ItemStack(Item.swordStone);
		default:
			return new ItemStack(Item.swordWood);
		}
	}
	
	private static ItemStack pickBlocks(Random rand, int rank) {

		switch(rank){
		
		case 0:
			if(rand.nextInt(100) == 0){
				return new ItemStack(Block.blockIron, 1);
			}
			
			if(rand.nextInt(50) == 0){
				return new ItemStack(Block.blockRedstone, 1);
			}
			
			if(rand.nextInt(50) == 0){
				return new ItemStack(Block.coalBlock, 1);
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.glass, 1 + rand.nextInt(5));
			}
			
			if(rand.nextInt(10) == 0){
				return new ItemStack(Block.wood, 2 + rand.nextInt(4));
			}
			break;
		case 1:
			if(rand.nextInt(50) == 0){
				return new ItemStack(Block.blockIron, 1);
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.blockRedstone, 1);
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.glass, 1 + rand.nextInt(10));
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.coalBlock, 1 + rand.nextInt(2));
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.tnt, 2 + rand.nextInt(4));
			}
			
			if(rand.nextInt(10) == 0){
				return new ItemStack(Block.glowStone, 3 + rand.nextInt(5));
			}
			
			break;
		case 2:
			if(rand.nextInt(300) == 0){
				return new ItemStack(Block.blockDiamond, 1);
			}
			
			if(rand.nextInt(100) == 0){
				return new ItemStack(Block.blockGold, 1);
			}
			
			if(rand.nextInt(50) == 0){
				return new ItemStack(Block.blockRedstone, 1 + rand.nextInt(3));
			}

			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.blockEmerald, 1);
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.blockIron, 1);
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.glass, 1 + rand.nextInt(15));
			}
			
			if(rand.nextInt(20) == 0){
				return new ItemStack(Block.coalBlock, 1 + rand.nextInt(3));
			}
			
			if(rand.nextInt(20) == 0){
				return new ItemStack(Block.tnt, 3 + rand.nextInt(6));
			}
			
			if(rand.nextInt(10) == 0){
				return new ItemStack(Block.glowStone, 3 + rand.nextInt(10));
			}
			if(rand.nextInt(5) == 0){
				return new ItemStack(Block.wood, 2 + rand.nextInt(4));
			}
			break;
		case 3:
			
			if(rand.nextInt(100) == 0){
				return new ItemStack(Block.blockGold, 1);
			}
			
			if(rand.nextInt(100) == 0){
				return new ItemStack(Block.blockDiamond, 1);
			}

			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.blockRedstone, 1 + rand.nextInt(5));
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Block.glass, 1 + rand.nextInt(20));
			}

			if(rand.nextInt(20) == 0){
				return new ItemStack(Block.blockEmerald, 1 + rand.nextInt(3));
			}
			
			if(rand.nextInt(20) == 0){
				return new ItemStack(Block.coalBlock, 1 + rand.nextInt(5));
			}
			
			if(rand.nextInt(15) == 0){
				return new ItemStack(Block.tnt, 4 + rand.nextInt(8));
			}
			
			if(rand.nextInt(15) == 0){
				return new ItemStack(Block.blockIron, 1);
			}
			
			return new ItemStack(Block.netherBrick, 5 + rand.nextInt(10));			
		}
		
		switch (rand.nextInt(4)) {
		case (3): 
			return new ItemStack(Block.planks, 5 + rand.nextInt(10));
		case (2): 
			return new ItemStack(Block.cobblestone, 5 + rand.nextInt(10));
		case (1): 
			return new ItemStack(Block.stoneBrick, 5 + rand.nextInt(10));
		default: 
			return new ItemStack(Block.dirt, 5 + rand.nextInt(10));
		}
	}

	private static ItemStack pickArmour(Random rand, int slot, int rank) {

		int quality = getQuality(rand, rank);
		
		switch(slot){
		
		case HEAD:
			switch (quality) {

			case DIAMOND:
				return new ItemStack(Item.helmetDiamond);
			case GOLD:
				return new ItemStack(Item.helmetGold);
			case IRON:
				return new ItemStack(Item.helmetIron);
			case STONE:
				return new ItemStack(Item.helmetChain);
			default:
				ItemStack item = new ItemStack(Item.helmetLeather);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case FEET:
			switch (quality) {

			case DIAMOND:
				return new ItemStack(Item.bootsDiamond);
			case GOLD:
				return new ItemStack(Item.bootsGold);
			case IRON:
				return new ItemStack(Item.bootsIron);
			case STONE:
				return new ItemStack(Item.bootsChain);
			default:
				ItemStack item = new ItemStack(Item.bootsLeather);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case CHEST:
			switch (quality) {

			case DIAMOND:
				return new ItemStack(Item.plateDiamond);
			case GOLD:
				return new ItemStack(Item.plateGold);
			case IRON:
				return new ItemStack(Item.plateIron);
			case STONE:
				return new ItemStack(Item.plateChain);
			default:
				ItemStack item = new ItemStack(Item.plateLeather);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		case LEGS:
			switch (quality) {
	
			case DIAMOND:
				return new ItemStack(Item.legsDiamond);
			case GOLD:
				return new ItemStack(Item.legsGold);
			case IRON:
				return new ItemStack(Item.legsIron);
			case STONE:
				return new ItemStack(Item.legsChain);
			default:
				ItemStack item = new ItemStack(Item.legsLeather);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		}
		return null;

	}
	
	private static ItemStack pickTool(Random rand, int rank){
		
		switch(rand.nextInt(3)){
		
		case 0:
			return pickPick(rand, rank);
		case 1:
			return pickAxe(rand, rank);
		case 2:
			return pickShovel(rand, rank);		
		default:
			return pickPick(rand, rank);
		
		}
		
		
	}
	

	private static ItemStack pickAxe(Random rand, int rank) {
		
		int quality = getQuality(rand, rank);

		switch (quality) {

		case DIAMOND:
			return new ItemStack(Item.axeDiamond);
		case GOLD:
			return new ItemStack(Item.axeGold);
		case IRON:
			return new ItemStack(Item.axeIron);
		case STONE:
			return new ItemStack(Item.axeStone);
		default:
			return new ItemStack(Item.axeWood);

		}

	}
	
	private static ItemStack pickShovel(Random rand, int rank) {

		int quality = getQuality(rand, rank);

		switch (quality) {

		case DIAMOND:
			return new ItemStack(Item.shovelDiamond);
		case GOLD:
			return new ItemStack(Item.shovelGold);
		case IRON:
			return new ItemStack(Item.shovelIron);
		case STONE:
			return new ItemStack(Item.shovelStone);
		default:
			return new ItemStack(Item.shovelWood);

		}
	}
	
	private static ItemStack pickPick(Random rand, int rank) {

		int quality = getQuality(rand, rank);

		switch (quality) {

		case DIAMOND:
			return new ItemStack(Item.pickaxeDiamond);
		case GOLD:
			return new ItemStack(Item.pickaxeGold);
		case IRON:
			return new ItemStack(Item.pickaxeIron);
		case STONE:
			return new ItemStack(Item.pickaxeStone);
		default:
			return new ItemStack(Item.pickaxeWood);

		}
	}
	
	private static ItemStack pickRecord(Random rand){
		return new ItemStack(Item.record13.itemID + rand.nextInt(12), 1, 0);
	}

	private static ItemStack pickSupplyItem(Random rand) {

		switch(rand.nextInt(9)){
		
		case 0:
			switch(rand.nextInt(3)){
			case 0:
				return new ItemStack(Item.seeds, rand.nextInt(8) + 1);
			case 1:
				return new ItemStack(Item.pumpkinSeeds, rand.nextInt(8) + 1);
			case 2:
				return new ItemStack(Item.melonSeeds, rand.nextInt(8) + 1);
			}			
		case 1:
			return new ItemStack(Item.wheat, rand.nextInt(8) + 1);
		case 2:
			// name tag
			return new ItemStack(421, 1, 0);
		case 3:
			return new ItemStack(Block.torchWood, 10 + rand.nextInt(10));
		case 4:
			if(rand.nextBoolean()){
				return new ItemStack(Item.paper, rand.nextInt(8) + 1);
			}
			return new ItemStack(Item.book, rand.nextInt(4) + 1);
		case 5:
			return new ItemStack(Item.saddle);
		case 6:
			// diamond horse armour
			if(rand.nextInt(6) == 0){
				return new ItemStack(419, 1, 0);
			}
			
			// gold horse armour
			if(rand.nextInt(3) == 0){
				return new ItemStack(418, 1, 0);
			}
			
			// iron horse armour
			return new ItemStack(417, 1, 0);			
		default:
			return new ItemStack(Item.stick, 1);
		}
	}

	private static ItemStack pickFood(Random rand, int rank) {

		int quantity = 2 + rand.nextInt(6);

		int choice = rand.nextInt(10);

		switch(choice){
		case 0:
			if(rank >= 3){
				return new ItemStack(Item.appleGold, quantity);
			}
			
			return new ItemStack(Item.appleRed, quantity);
			
		case 1:
			
			if(rank > 1){
				return new ItemStack(Item.appleGold, quantity);
			}
			
			return new ItemStack(Item.appleRed, quantity);
			
		case 2:
			return new ItemStack(Item.bowlSoup);
			
		case 3:
			return new ItemStack(Item.beefCooked, quantity);
			
		case 4:
			if(rank > 1){
				return new ItemStack(Item.goldenCarrot, quantity);
			}
			
			return new ItemStack(Item.bread, quantity);
		
		case 5:
			return new ItemStack(Item.porkCooked, quantity);
			
		case 6:
			
			if(rank > 1){
				return new ItemStack(Item.goldenCarrot, quantity);
			}
			
			return new ItemStack(Item.melon, quantity);
			
		case 7:
			return new ItemStack(Item.chickenCooked, quantity);
			
		case 8:
			if(rank > 1){
				return new ItemStack(Item.goldenCarrot, quantity);
			}
			
			return new ItemStack(Item.fishCooked, quantity);
		
		case 9:
			return new ItemStack(Item.bakedPotato, quantity);

		}
		return new ItemStack(Item.bread, quantity);
	}

	private static ItemStack pickOre(Random rand, int rank) {

		int quantity = 1 + rand.nextInt(4);		
		
		switch(rank){
		
		case 3:

			
			if (rand.nextInt(10) == 0) {
				return new ItemStack(Item.diamond, quantity);
			}

			if(rand.nextInt(5) == 0){
				return new ItemStack(Item.emerald, quantity);
			}
			
			if(rand.nextBoolean()){
				return new ItemStack(Item.diamond, 1);
			}
			
			return new ItemStack(Item.ingotIron, quantity * 3);

		case 2:
			
			if (rand.nextInt(15) == 0){
				return new ItemStack(Item.diamond, 1);
			}
			
			if(rand.nextInt(10) == 0){
				return new ItemStack(Item.emerald, quantity * 2);
			}
			
			if (rand.nextInt(10) == 0) {
				return new ItemStack(Item.ingotGold, quantity);
			}
			
			if(rand.nextInt(5) == 0){
				return new ItemStack(Item.coal, quantity * 4);
			}
			
			return new ItemStack(Item.ingotIron, quantity * 2);
			
		case 1:
			
			if (rand.nextInt(50) == 0){
				return new ItemStack(Item.diamond, 1);
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Item.emerald, quantity);
			}
			
			if(rand.nextInt(3) == 0){
				return new ItemStack(Item.coal, quantity * 2);
			}
			
			return new ItemStack(Item.ingotIron, quantity);
			
		case 0:
			
			if(rand.nextInt(3) == 0){
				return new ItemStack(Item.leather, 1 + rand.nextInt(3));
			}
			
			if(rand.nextBoolean()){
				return new ItemStack(Item.ingotIron, 1);
			}
			
			return new ItemStack(Item.coal, quantity);
			
		default:
			
			return new ItemStack(Item.coal, 1);
		}
				
	}

	private static int getQuality(Random rand, int rank) {

		switch(rank){
		
		case 3:
			
			if(rand.nextInt(20) == 0){
				return GOLD;
			}
			
			if(rand.nextInt(10) == 0){
				return DIAMOND;
			}

			return IRON;
		
		case 2:
			
			if(rand.nextInt(40) == 0){
				return DIAMOND;
			}
			
			if(rand.nextInt(20) == 0){
				return GOLD;
			}
			
			if(rand.nextInt(3) == 0){
				return STONE;
			}
						
			return IRON;
			
		case 1:
			
			if(rand.nextInt(200) == 0){
				return DIAMOND;
			}
			
			if(rand.nextInt(100) == 0){
				return GOLD;
			}
			
			if(rand.nextInt(20) == 0){
				return IRON;
			}
			
			if(rand.nextBoolean()){
				return WOOD;
			}
			
			return STONE; 
			
		case 0:
			
			if(rand.nextInt(100) == 0){
				return IRON;
			}
			
			if(rand.nextInt(10) == 0){
				return STONE;
			}
			
			return WOOD;
			
		default:
			return WOOD;
		}
	}

	private static int getEnchantLevel(int rank) {

		switch(rank){
		case 3:
			return 30;
		case 2:
			return 20;
		case 1:
			return 10;
		case 0:
			return 1;
		default:
			return 1;
		}
	}

	public static void enchantItem(ItemStack item, Random rand, int enchantLevel) {
		
        List enchants = EnchantmentHelper.buildEnchantmentList(rand, item, enchantLevel);
        boolean canEnchant = enchants != null;
        boolean isABook = item.itemID == Item.book.itemID;

        if (!canEnchant){
        	return;
        }
        
        if (isABook){
            item.itemID = Item.enchantedBook.itemID;
        }

        int var6 = isABook ? rand.nextInt(enchants.size()) : -1;

        for (int i = 0; i < enchants.size(); ++i)
        {
            EnchantmentData enchantData = (EnchantmentData)enchants.get(i);

            if (!isABook || i == var6)
            {
                if (isABook)
                {
                    Item.enchantedBook.addEnchantment(item, enchantData);
                }
                else
                {
                    item.addEnchantment(enchantData.enchantmentobj, enchantData.enchantmentLevel);
                }
            }
        }
	}
	
	public static ItemStack dyeArmor(ItemStack armor, int r, int g, int b){
		
		int color = r << 16 | g << 8 | b << 0;;
        
        NBTTagCompound nbtdata = armor.getTagCompound();

        if (nbtdata == null)
        {
            nbtdata = new NBTTagCompound();
            armor.setTagCompound(nbtdata);
        }

        NBTTagCompound nbtDisplay = nbtdata.getCompoundTag("display");

        if (!nbtdata.hasKey("display"))
        {
            nbtdata.setCompoundTag("display", nbtDisplay);
        }

        nbtDisplay.setInteger("color", color);
        
		return armor;
	}
	
    public static void setItemLore(ItemStack item, String loreText)
    {
        if (item.stackTagCompound == null)
        {
            item.stackTagCompound = new NBTTagCompound("tag");
        }

        if (!item.stackTagCompound.hasKey("display"))
        {
            item.stackTagCompound.setCompoundTag("display", new NBTTagCompound());
        }
        
        NBTTagCompound display = item.stackTagCompound.getCompoundTag("display");
        
        if (!(display.hasKey("Lore")))
        {
        	display.setTag("Lore", new NBTTagList());
        }
        
        NBTTagList lore = display.getTagList("Lore");
        
        NBTTagString toAdd = new NBTTagString("", loreText);
        
        lore.appendTag(toAdd);
        
        display.setTag("Lore", lore);
        
        
    }
	
}
