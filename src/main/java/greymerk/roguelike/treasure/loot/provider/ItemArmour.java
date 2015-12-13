package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemArmour extends ItemBase {

	public ItemArmour(int weight, int level) {
		super(weight, level);
	}
	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return getRandom(rand, level, true);
	}

	public static ItemStack getRandom(Random rand, int level, boolean enchant){
		return getRandom(rand, level,
				Slot.getSlotByNumber(rand.nextInt(4) + 1),
				enchant ? Enchant.getLevel(rand, level) : 0);
	}
	
	public static ItemStack getRandom(Random rand, int level, Slot slot, boolean enchant){
		return getRandom(rand, level, slot, enchant ? Enchant.getLevel(rand, level) : 0);
	}
	
	@SuppressWarnings("incomplete-switch")
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
		
		if(enchantLevel > 0) Enchant.enchantItem(item, rand, enchantLevel);
		
		return item;
		
	}
	
	@SuppressWarnings("incomplete-switch")
	private static ItemStack pickArmour(Random rand, Slot slot, Quality quality) {
		
		switch(slot){
		
		case HEAD:
			switch (quality) {

			case DIAMOND: return new ItemStack(Items.diamond_helmet);
			case GOLD: return new ItemStack(Items.golden_helmet);
			case IRON: return new ItemStack(Items.iron_helmet);
			case STONE: return new ItemStack(Items.chainmail_helmet);
			default:
				ItemStack item = new ItemStack(Items.leather_helmet);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case FEET:
			switch (quality) {

			case DIAMOND: return new ItemStack(Items.diamond_helmet);
			case GOLD: return new ItemStack(Items.golden_helmet);
			case IRON: return new ItemStack(Items.iron_helmet);
			case STONE: return new ItemStack(Items.chainmail_helmet);
			default:
				ItemStack item = new ItemStack(Items.leather_boots);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		
		case CHEST:
			switch (quality) {

			case DIAMOND: return new ItemStack(Items.diamond_chestplate);
			case GOLD: return new ItemStack(Items.golden_chestplate);
			case IRON: return new ItemStack(Items.iron_chestplate);
			case STONE: return new ItemStack(Items.chainmail_chestplate);
			default:
				ItemStack item = new ItemStack(Items.leather_chestplate);
				dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
				return item;
			}
		case LEGS:
			switch (quality) {
	
			case DIAMOND: return new ItemStack(Items.diamond_leggings);
			case GOLD: return new ItemStack(Items.golden_leggings);
			case IRON: return new ItemStack(Items.iron_leggings);
			case STONE: return new ItemStack(Items.chainmail_leggings);
			default:
				ItemStack item = new ItemStack(Items.leather_leggings);
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
            nbtdata.setTag("display", nbtDisplay);
        }

        nbtDisplay.setInteger("color", color);
        
		return armor;
	}


}
