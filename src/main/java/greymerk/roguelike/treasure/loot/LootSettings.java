package greymerk.roguelike.treasure.loot;

import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemBlock;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.treasure.loot.provider.ItemJunk;
import greymerk.roguelike.treasure.loot.provider.ItemOre;
import greymerk.roguelike.treasure.loot.provider.ItemPotion;
import greymerk.roguelike.treasure.loot.provider.ItemRecord;
import greymerk.roguelike.treasure.loot.provider.ItemSmithy;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.provider.ItemSupply;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;

public class LootSettings {

	private Map<Loot, IWeighted<ItemStack>> loot;
	
	public LootSettings(int level){
		loot = new HashMap<Loot, IWeighted<ItemStack>>();
		loot.put(Loot.WEAPON, new ItemWeapon(0, level));
		loot.put(Loot.ARMOUR, new ItemArmour(0, level));
		loot.put(Loot.BLOCK, new ItemBlock(0, level));
		loot.put(Loot.JUNK, new ItemJunk(0, level));
		loot.put(Loot.ORE, new ItemOre(0, level));
		loot.put(Loot.TOOL, new ItemTool(0, level));
		loot.put(Loot.POTION, new ItemPotion(0, level));
		loot.put(Loot.FOOD, new ItemFood(0, level));
		loot.put(Loot.ENCHANTBOOK, new ItemEnchBook(0, level));
		loot.put(Loot.ENCHANTBONUS, new ItemEnchBonus(0, level));
		loot.put(Loot.SUPPLY, new ItemSupply(0, level));
		loot.put(Loot.MUSIC, new ItemRecord(0, level));
		loot.put(Loot.SMITHY, new ItemSmithy(0, level));
		loot.put(Loot.SPECIAL, new ItemSpecialty(0, level));
	}
	
	public LootSettings(LootSettings toCopy){
		this.loot = new HashMap<Loot, IWeighted<ItemStack>>();
		this.loot.putAll(toCopy.loot);
	}
	
	public LootSettings(LootSettings base, LootSettings override){
		this.loot = new HashMap<Loot, IWeighted<ItemStack>>();
		for(Loot type : Loot.values()){
			IWeighted<ItemStack> toAdd = override.loot.containsKey(type) ? override.loot.get(type) : base.loot.get(type);
			loot.put(type, toAdd);
		}
	}
	
	public LootSettings(JsonObject data){
		this.loot = new HashMap<Loot, IWeighted<ItemStack>>();
		for(Loot type : Loot.values()){
			if(data.has(type.toString())){
				JsonElement providerData = data.get(type.toString());
				IWeighted<ItemStack> provider = parseProvider(providerData);
				loot.put(type, provider);
			}
		}
	}
	
	private IWeighted<ItemStack> parseProvider(JsonElement data) {
		
		if(data.isJsonObject()){
			return new WeightedRandomLoot(data.getAsJsonObject());
		}
		
		JsonArray lootList = data.getAsJsonArray();
		
		WeightedRandomizer<ItemStack> items = new WeightedRandomizer<ItemStack>();
		for(JsonElement e : lootList){
			items.add(parseProvider(e));
		}
		
		return items;
	}

	public ItemStack get(Loot type, Random rand){
		IWeighted<ItemStack> provider = loot.get(type);
		return provider.get(rand);
	}
	
	public void set(Loot type, IWeighted<ItemStack> provider){
		this.loot.put(type, provider);
	}
}
