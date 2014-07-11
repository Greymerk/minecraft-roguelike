package greymerk.roguelike.treasure.loot;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemBlock;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.treasure.loot.provider.ItemJunk;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.treasure.loot.provider.ItemOre;
import greymerk.roguelike.treasure.loot.provider.ItemPotion;
import greymerk.roguelike.treasure.loot.provider.ItemRecord;
import greymerk.roguelike.treasure.loot.provider.ItemSmithy;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.provider.ItemSupply;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.TextFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public enum Loot {

	WEAPON, ARMOUR, BLOCK, JUNK, ORE, TOOL, POTION, FOOD, ENCHANTBOOK, ENCHANTBONUS, SUPPLY, MUSIC, SMITHY, SPECIAL;
	
	private static final int NUM_LEVELS = 5;
	private static Map<String, LootProvider> loot = new HashMap<String, LootProvider>();
	static{
		init();
	}
	
	
	public static void init(){
	
		for(Loot type : Loot.values()){
			LootProvider loots;
			
			loot.put(type.name(), loots = new LootProvider());
			
			for(int i = 0; i < NUM_LEVELS; ++i){
				switch(type){
					case WEAPON: loots.add(i, new ItemWeapon(1000, i)); continue;
					case ARMOUR: loots.add(i, new ItemArmour(1000, i)); continue;
					case BLOCK: loots.add(i, new ItemBlock(1000, i)); continue;
					case JUNK: loots.add(i, new ItemJunk(1000, i)); continue;
					case ORE: loots.add(i, new ItemOre(1000, i)); continue;
					case TOOL: loots.add(i, new ItemTool(1000, i)); continue;
					case POTION: loots.add(i, new ItemPotion(1000, i)); continue;
					case FOOD: loots.add(i, new ItemFood(1000, i)); continue;
					case ENCHANTBOOK: loots.add(i, new ItemEnchBook(1000, i)); continue;
					case ENCHANTBONUS: loots.add(i, new ItemEnchBonus(1000, i)); continue;
					case SUPPLY: loots.add(i, new ItemSupply(1000, i)); continue;
					case MUSIC: loots.add(i, new ItemRecord(1000, i)); continue;
					case SMITHY: loots.add(i, new ItemSmithy(1000, i)); continue;
					case SPECIAL: loots.add(i, new ItemSpecialty(1000, i)); continue;
				}
			}
		}

		CustomLoot.parseLoot();
		
	}
	
	public static void clear(Loot type){
		LootProvider lootp = loot.get(type.name());
		lootp.clear();
	}
	
	public static void addAllLevels(Loot type, IWeighted<ItemStack> toAdd){
		LootProvider lootp = loot.get(type.name());
		lootp.addAllLevels(toAdd);
	}
	
	public static void add(Loot type, IWeighted<ItemStack> toAdd, int level){
		LootProvider lootp = loot.get(type.name());
		lootp.add(level, toAdd);
	}
	
	public static ItemStack getLoot(Loot type, Random rand, int level){
		LootProvider p =  loot.get(type.name());
		return p.get(rand, level);		
	}
	
	public static ItemStack getEquipmentBySlot(Random rand, Slot slot, int level, boolean enchant){
		
		if(slot == Slot.WEAPON){
			return ItemWeapon.getRandom(rand, level, enchant);
		}
		
		return ItemArmour.getRandom(rand, level, slot, enchant);
		
	}

	public static int getEnchantLevel(Random rand, int level) {

		switch(level){
		case 4: return 21 + rand.nextInt(10);
		case 3: return 16 + rand.nextInt(10);
		case 2: return 5 + rand.nextInt(10);
		case 1: return 1 + rand.nextInt(5);
		case 0: return 1;
		default: return 1;
		}
	}

	public static void enchantItemChance(ItemStack item, Random rand, int level){
		if(rand.nextInt(7 - level) == 0) enchantItem(item, rand, getEnchantLevel(rand, level));
	}


	
	public static void enchantItem(ItemStack item, Random rand, int enchantLevel) {

		if (item == null ) return;

		@SuppressWarnings("unchecked")
		List<EnchantmentData> enchants = EnchantmentHelper.buildEnchantmentList(rand, item, enchantLevel);
		
		boolean isBook = item.getItem() == Items.book;

		if (isBook){
			item.func_150996_a(Items.enchanted_book);
			if(enchants.size() > 1){
				enchants.remove(rand.nextInt(enchants.size()));
			}
		}

		for (EnchantmentData toAdd : enchants){
			if (isBook){
				Items.enchanted_book.addEnchantment(item, toAdd);
			} else {
				item.addEnchantment(toAdd.enchantmentobj, toAdd.enchantmentLevel);
			}
		}
	}
	
	
	public static void setItemLore(ItemStack item, String loreText){
		
		if (item.stackTagCompound == null){
			item.stackTagCompound.setTag("tag", new NBTTagCompound());
		}

		if (!item.stackTagCompound.hasKey("display")){
			item.stackTagCompound.setTag("display", new NBTTagCompound());
		}
		
		NBTTagCompound display = item.stackTagCompound.getCompoundTag("display");
		
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
			
		Random rand = world.rand;
				
		EnumDifficulty difficulty = world.difficultySetting;
		
		boolean enchant;
		
		switch(difficulty){
		case EASY: enchant = rand.nextInt(5) == 0; break;
		case NORMAL: enchant = level == 3 || rand.nextBoolean(); break;
		case HARD: enchant = true; break;
		default: enchant = true;
		}
		
		ItemStack weapon;
		
		// zombie gets a sword
		if(mob instanceof EntityZombie){
			if(rand.nextInt(20) == 0){
				((EntityZombie)mob).setChild(true);
				if(rand.nextInt(20) == 0){
					weapon = ItemNovelty.getItem(ItemNovelty.ASHLEA);
				} else if(rand.nextInt(5) == 0){
					weapon = new ItemStack(Items.cookie);
				} else {
					weapon = ItemTool.getRandom(rand, level, enchant);
				}
			} else {
				weapon = ItemTool.getRandom(rand, level, enchant);
			}
			mob.setCurrentItemOrArmor(0, weapon);
		}
		
		// skelly gets a bow
		if(mob instanceof EntitySkeleton){
			
			if(rand.nextInt(10) == 0 && level > 1){
				((EntitySkeleton) mob).setSkeletonType(1);
				mob.setCurrentItemOrArmor(0, ItemWeapon.getSword(rand, level, enchant));
			} else {
				if(rand.nextInt(20) == 0){
					mob.setCurrentItemOrArmor(0, ItemWeapon.getSword(rand, level, enchant));
				} else {
					mob.setCurrentItemOrArmor(0, ItemWeapon.getBow(rand, level, enchant));
				}
			}
		}
		
		// put on some armour
		for(int i = 1; i < 5; i++){
			if (enchant){
				ItemStack item = Loot.getEquipmentBySlot(rand, Slot.getSlotByNumber(i), level, enchant);
				mob.setCurrentItemOrArmor(i, item);
			}
		}
		
		// lower drop chance

		for(int s = 0; s < 5; s++){
			((EntityLiving)mob).setEquipmentDropChance(s, (float) RogueConfig.getDouble(RogueConfig.LOOTING));
		}
	}
}
