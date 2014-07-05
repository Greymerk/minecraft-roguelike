package greymerk.roguelike.treasure.loot;


import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.nbt.JsonNBT;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.google.gson.JsonObject;

public class WeightedRandomLoot implements Comparable<WeightedRandomLoot>, IWeighted<ItemStack>{
	
	private Item item;
	private int damage;
	
	private int min;
	private int max;
	
	private int weight;
	
	private NBTTagCompound nbt; 
	
	public WeightedRandomLoot(Item item, int damage, int minStackSize, int maxStackSize, int weight){
		this.item = item;
		this.damage = damage;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
	}

	public WeightedRandomLoot(JsonObject json){
		String name = json.get("name").getAsString();
		this.item = (Item) Item.itemRegistry.getObject(name);
		this.damage = json.has("meta") ? json.get("meta").getAsInt() : 0;
		this.weight = json.get("weight").getAsInt();

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
	
	
	public WeightedRandomLoot(Item id, int damage, int weight){
		this(id, damage, 1, 1, weight);
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
		ItemStack item = new ItemStack(this.item, this.getStackSize(rand), damage);
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