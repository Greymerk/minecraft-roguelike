package greymerk.roguelike.treasure.loot;


import java.util.Random;

import com.google.gson.JsonObject;

import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.JsonNBT;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WeightedRandomLoot implements Comparable<WeightedRandomLoot>, IWeighted<ItemStack>{
	
	
	private String name;
	private Item item;
	private Block block;
	private int damage;
	private int min;
	private int max;
	private int enchLevel;
	private int weight;
	
	private NBTTagCompound nbt; 
	
	public WeightedRandomLoot(Block block, int damage, int minStackSize, int maxStackSize, int weight){
		this.name = block.getUnlocalizedName();
		this.block = block;
		this.damage = damage;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
		this.enchLevel = 0;
	}
	
	public WeightedRandomLoot(Item item, int damage, int minStackSize, int maxStackSize, int weight){
		this(item, damage, minStackSize, maxStackSize, weight, 0);
	}
	
	public WeightedRandomLoot(Item item, int damage, int minStackSize, int maxStackSize, int weight, int ench){
		
		this.name = item.getUnlocalizedName();
		this.item = item;
		this.damage = damage;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
		this.enchLevel = ench;
	}

	public WeightedRandomLoot(Item item, int damage, int weight){
		this(item, damage, 1, 1, weight, 0);
	}
	
	public WeightedRandomLoot(Item item, int weight){
		this(item, 0, 1, 1, weight, 0);
	}
	
	public WeightedRandomLoot(JsonObject json, int weight) throws Exception{
		this.name = json.get("name").getAsString();
		this.item = (Item) Item.itemRegistry.getObject(name);
		try{
			this.item.getUnlocalizedName();
		} catch (NullPointerException e){
			throw new Exception("Invalid item: " + this.name);
		}
		this.damage = json.has("meta") ? json.get("meta").getAsInt() : 0;
		this.weight = weight;
		this.enchLevel = json.has("ench") ? json.get("ench").getAsInt() : 0;

		if(json.has("min") && json.has("max")){
			min = json.get("min").getAsInt();
			max = json.get("max").getAsInt();	
		} else {
			min = 1;
			max = 1;
		}
		
		if(json.has("nbt")){
			JsonObject nbtdata = json.get("nbt").getAsJsonObject();
			this.nbt = JsonNBT.jsonToCompound(nbtdata);
		}
	}

	public WeightedRandomLoot(Block block, int i) {
		this(Item.getItemById(Block.getIdFromBlock(block)), i);
	}

	private int getStackSize(Random rand){
		if (max == 1) return 1;
		
		return rand.nextInt(max - min) + min;
	}

	
	@Override
	public int getWeight(){
		return this.weight;
	}

	@Override
	public ItemStack get(Random rand) {
		ItemStack item = null;
		if(this.item != null) item = new ItemStack(this.item, this.getStackSize(rand), damage);
		if(this.block != null) item = new ItemStack(this.block, this.getStackSize(rand), damage);
		try{
			if(this.enchLevel > 0 && this.enchLevel <= 30) Enchant.enchantItem(rand, item, this.enchLevel);
		} catch (NullPointerException e){
			System.err.println("error occurred while attempting to enchant " + this.name);
		}
		if(this.nbt != null) item.setTagCompound(this.nbt);
		return item;
	}

	@Override
	public int compareTo(WeightedRandomLoot other) {
		if (this.weight > other.weight) return -1;
		if (this.weight < other.weight) return 1;
		
		return 0;
	}
}