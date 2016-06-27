package greymerk.roguelike.treasure.loot;

import java.util.Random;

import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import greymerk.roguelike.util.TextFormat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

public enum Loot {
	
	WEAPON, ARMOUR, BLOCK, JUNK, ORE, TOOL, POTION, FOOD, ENCHANTBOOK,
	ENCHANTBONUS, SUPPLY, MUSIC, SMITHY, SPECIAL, REWARD, STARTER;

	public static ILoot getLoot(){
		
		LootProvider loot = new LootProvider();
		for(int i = 0; i < 5; ++i){
			loot.put(i, new LootSettings(i));
		}
		
		return loot;
	}
	
	public static ItemStack getEquipmentBySlot(Random rand, EntityEquipmentSlot slot, int level, boolean enchant){
		if(slot == EntityEquipmentSlot.MAINHAND){
			return ItemWeapon.getRandom(rand, level, enchant);
		}
		
		return ItemArmour.getRandom(rand, level, Slot.getSlot(slot), enchant);
	}
	
	public static ItemStack getEquipmentBySlot(Random rand, Slot slot, int level, boolean enchant){
		
		if(slot == Slot.WEAPON){
			return ItemWeapon.getRandom(rand, level, enchant);
		}
		
		return ItemArmour.getRandom(rand, level, slot, enchant);
	}

	public static void setItemLore(ItemStack item, String loreText){
		
		NBTTagCompound tag = item.getTagCompound(); 
		
		if (tag == null){
			tag = new NBTTagCompound();
			item.setTagCompound(tag);
		}

		if (!tag.hasKey("display")){
			tag.setTag("display", new NBTTagCompound());
		}
		
		NBTTagCompound display = tag.getCompoundTag("display");
		
		if (!(display.hasKey("Lore")))
		{
			display.setTag("Lore", new NBTTagList());
		}
		
		NBTTagList lore = display.getTagList("Lore", 0);
		
		NBTTagString toAdd = new NBTTagString(loreText);
		
		lore.appendTag(toAdd);
		
		display.setTag("Lore", lore);   
	}
	
	public static void setItemLore(ItemStack item, String loreText, TextFormat option){
		setItemLore(item, TextFormat.apply(loreText, option));
	}
	
	public static void setItemName(ItemStack item, String name, TextFormat option){
		
		if(option == null){
			item.setStackDisplayName(name);
			return;
		}
		
		item.setStackDisplayName(TextFormat.apply(name, option));
	}
	
	public static void setItemName(ItemStack item, String name){
		setItemName(item, name, null);
	}
	
	public static void addEquipment(World world, int level, Entity mob){
			
		if(level > 4) level = 4;
		Random rand = world.rand;
		boolean enchant = Enchant.canEnchant(world.getDifficulty(), rand, level);
		
		ItemStack weapon;
		
		// zombie gets a sword
		if(mob instanceof EntityZombie){
			if(rand.nextInt(20) == 0){
				((EntityZombie)mob).setChild(true);
				if(rand.nextInt(20) == 0){
					weapon = ItemNovelty.getItem(ItemNovelty.ASHLEA);
				} else if(rand.nextInt(5) == 0){
					weapon = new ItemStack(Items.COOKIE);
				} else {
					weapon = ItemTool.getRandom(rand, level, enchant);
				}
			} else {
				if(level > 1 && rand.nextInt(10) == 0){
					((EntityZombie)mob).func_189778_a(ZombieType.HUSK);
				}
				if(level > 1 && rand.nextInt(50) == 0){
					weapon = ItemNovelty.getItem(ItemNovelty.AMLP);
				} else if(level > 2 && rand.nextInt(50) == 0){
					weapon = ItemNovelty.getItem(ItemNovelty.DINNERBONE);
				} else if(level > 1 && rand.nextInt(20) == 0){
					weapon = ItemSpecialty.getRandomTool(rand, Quality.get(rand, level, Equipment.SHOVEL));
				} else {
					weapon = ItemTool.getRandom(rand, level, enchant);
				}
			}
			mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, weapon);
			
			ItemStack shield = Shield.get(rand);
			mob.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, shield);
		}
		
		// skelly gets a bow
		if(mob instanceof EntitySkeleton){
			
			if(rand.nextInt(10) == 0 && level > 1){
				((EntitySkeleton) mob).func_189768_a(SkeletonType.WITHER);
				mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemWeapon.getSword(rand, level, enchant));
			} else {
				if(rand.nextInt(10) == 0){
					((EntitySkeleton)mob).func_189768_a(SkeletonType.STRAY);
				}
				if(rand.nextInt(20) == 0){
					if(level > 2 && rand.nextInt(10) == 0){
						mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemNovelty.getItem(ItemNovelty.NULL));
					} else if(level > 0 && rand.nextInt(5) == 0){
						mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemNovelty.getItem(ItemNovelty.VALANDRAH));
					} else if(level > 0 && rand.nextInt(3) == 0){
						mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemSpecialty.getRandomItem(Equipment.SWORD, rand, level));
					} else {
						mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemWeapon.getSword(rand, level, enchant));
					}
				} else {
					if(level > 2 && rand.nextInt(50) == 0){
						mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemNovelty.getItem(ItemNovelty.ENIKOBOW));
					} else if(level > 1 && rand.nextInt(20) == 0){
						mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemSpecialty.getRandomItem(Equipment.BOW, rand, level));
					} else {
						mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemWeapon.getBow(rand, level, enchant));	
					}
				}
			}
		}
		
		// put on some armour
		for(EntityEquipmentSlot slot : new EntityEquipmentSlot[]{
				EntityEquipmentSlot.HEAD,
				EntityEquipmentSlot.CHEST,
				EntityEquipmentSlot.LEGS,
				EntityEquipmentSlot.FEET
				}){
			ItemStack item;
			if(mob instanceof EntitySpider){
				item = Loot.getEquipmentBySlot(rand, Slot.FEET, level, enchant);	
			} else {
				item = Loot.getEquipmentBySlot(rand, Slot.getSlot(slot), level, enchant);
			}
			mob.setItemStackToSlot(slot, item);
		}
		
		// lower drop chance
	}
}
