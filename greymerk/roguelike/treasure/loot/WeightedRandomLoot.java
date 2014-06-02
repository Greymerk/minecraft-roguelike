package greymerk.roguelike.treasure.loot;


import java.util.Random;

import com.google.gson.JsonObject;

import net.minecraft.src.ItemStack;

public class WeightedRandomLoot implements Comparable, ILootProvider{
	
	private int id;
	private int damage;
	
	private int min;
	private int max;
	
	private int weight;
	
	

	
	public WeightedRandomLoot(int id, int damage, int minStackSize, int maxStackSize, int weight){
		this.id = id;
		this.damage = damage;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
	}
	
	public WeightedRandomLoot(int id, int damage, int weight){
		this(id, damage, 1, 1, weight);
	}	
	
	public ItemStack getLootItem(Random rand, int level) {
		return new ItemStack(id, getStackSize(rand), damage);
	}
	
	private int getStackSize(Random rand){
		if (max == 1) return 1;
		
		return rand.nextInt(max - min) + min;
	}

	@Override
	public int compareTo(Object o) {
		
		WeightedRandomLoot other = (WeightedRandomLoot)o;
		
		if (this.weight > other.weight) return -1;
		if (this.weight < other.weight) return 1;
		
		return 0;
	}
	
	public static WeightedRandomLoot decode(JsonObject item){
		
		int id = item.get("id").getAsInt();
		int dam = item.has("meta") ? item.get("meta").getAsInt() : 0;
		int min;
		int max;
		int weight = item.get("weight").getAsInt();

		if(item.has("min") && item.has("max")){
			min = item.get("min").getAsInt();
			max = item.get("max").getAsInt();	
		} else {
			min = 1;
			max = 1;
		}
		
		return new WeightedRandomLoot(id, dam, min, max, weight);
	}
	
	public int getWeight(){
		return this.weight;
	}


}