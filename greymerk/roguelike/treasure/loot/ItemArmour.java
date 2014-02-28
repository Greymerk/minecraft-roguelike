package greymerk.roguelike.treasure.loot;

import greymerk.roguelike.catacomb.Catacomb;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ItemArmour {

	public static ItemStack getRandom(Random rand, int level, boolean enchant){
		return getRandom(rand, level,
				Slot.getSlotByNumber(rand.nextInt(4) + 1),
				enchant ? Loot.getEnchantLevel(rand, level) : 0);
	}
	
	public static ItemStack getRandom(Random rand, int level, Slot slot, boolean enchant){
		return getRandom(rand, level, slot, enchant ? Loot.getEnchantLevel(rand, level) : 0);
	}
	
	public static ItemStack getRandom(Random rand, int level, Slot slot, int enchantLevel){

		if(enchantLevel > 0 && rand.nextInt(20 + (level * 10)) == 0){
			switch(slot){
			case HEAD: return ItemSpecialty.getRandomItem(Equipment.HELMET, rand, level); 
			case CHEST: return ItemSpecialty.getRandomItem(Equipment.CHEST, rand, level); 
			case LEGS: return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, level); 
			case FEET: return ItemSpecialty.getRandomItem(Equipment.FEET, rand, level); 
			}
		}

		ItemStack item = pickArmour(rand, slot, Quality.getArmourQuality(rand, level));
		
		if(enchantLevel > 0) Loot.enchantItem(item, rand, enchantLevel);
		
		return item;
		
	}
	
	private static ItemStack pickArmour(Random rand, Slot slot, Quality quality) {
		
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
