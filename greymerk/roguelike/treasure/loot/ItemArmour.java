package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ItemArmour {

	public static ItemStack getRandom(Random rand, int rank, boolean enchant){
		return getRandom(rand, rank, Slot.getSlotByNumber(rand.nextInt(4) + 1), enchant);
	}
	
	
	public static ItemStack getRandom(Random rand, int rank, Slot slot, boolean enchant){

		if(enchant && rand.nextInt(20 + (rank * 10)) == 0){
			switch(slot){
			case HEAD: return ItemSpecialty.getRandomItem(ItemSpecialty.HELMET, rand, rank); 
			case CHEST: return ItemSpecialty.getRandomItem(ItemSpecialty.CHEST, rand, rank); 
			case LEGS: return ItemSpecialty.getRandomItem(ItemSpecialty.LEGS, rand, rank); 
			case FEET: return ItemSpecialty.getRandomItem(ItemSpecialty.FEET, rand, rank); 
			}
		}
		
		if(enchant && rand.nextInt(100) == 0){
			if(slot == Slot.HEAD && rank > 1) return ItemNovelty.getItem(ItemNovelty.NEBRIS);
			if(slot == Slot.FEET && rank < 2) return ItemNovelty.getItem(ItemNovelty.KURT);
			if(slot == Slot.CHEST && rank == 1) return ItemNovelty.getItem(ItemNovelty.MILLBEE);
			if(slot == Slot.CHEST && rank == 2) return ItemNovelty.getItem(ItemNovelty.ANDERZEL);
			if(slot == Slot.LEGS && rank == 0) return ItemNovelty.getItem(ItemNovelty.ZISTEAUPANTS);
		}

		ItemStack item = pickArmour(rand, slot, rank);
		
		if(enchant && rand.nextInt(6 - rank) == 0){
			Loot.enchantItem(item, rand, Loot.getEnchantLevel(rank));
		}
		
		return item;
		
	}
	
	private static ItemStack pickArmour(Random rand, Slot slot, int rank) {

		Quality quality = Quality.getQuality(rand, rank);
		
		switch(slot){
		
		case HEAD:
			switch (quality) {

			case DIAMOND: return new ItemStack(Item.helmetDiamond);
			case GOLD: return new ItemStack(Item.helmetGold);
			case IRON: return new ItemStack(Item.helmetIron);
			case STONE: return new ItemStack(Item.helmetChain);
			default:
				ItemStack item = new ItemStack(Item.helmetLeather);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case FEET:
			switch (quality) {

			case DIAMOND: return new ItemStack(Item.bootsDiamond);
			case GOLD: return new ItemStack(Item.bootsGold);
			case IRON: return new ItemStack(Item.bootsIron);
			case STONE: return new ItemStack(Item.bootsChain);
			default:
				ItemStack item = new ItemStack(Item.bootsLeather);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case CHEST:
			switch (quality) {

			case DIAMOND: return new ItemStack(Item.plateDiamond);
			case GOLD: return new ItemStack(Item.plateGold);
			case IRON: return new ItemStack(Item.plateIron);
			case STONE: return new ItemStack(Item.plateChain);
			default:
				ItemStack item = new ItemStack(Item.plateLeather);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		case LEGS:
			switch (quality) {
	
			case DIAMOND: return new ItemStack(Item.legsDiamond);
			case GOLD: return new ItemStack(Item.legsGold);
			case IRON: return new ItemStack(Item.legsIron);
			case STONE: return new ItemStack(Item.legsChain);
			default:
				ItemStack item = new ItemStack(Item.legsLeather);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		}
		return null;
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
}
