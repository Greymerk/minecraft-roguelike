package greymerk.roguelike.treasure.loot.custom;

import java.util.Random;

import net.minecraft.src.ItemStack;

public class WeightedRandomLoot implements Comparable{
	
	int id;
	int damage;
	
	int min;
	int max;
	
	int weight;
	int scale;
	
	public WeightedRandomLoot(int id, int damage, int minStackSize, int maxStackSize, int weight, int scale){
		this.id = id;
		this.damage = damage;
		this.min = minStackSize;
		this.max = maxStackSize;
		this.weight = weight;
		this.scale = scale;
	}
	
	
	
	public ItemStack getItem(Random rand){
		return new ItemStack(id, getStackSize(rand), damage);
	}
	
	private int getStackSize(Random rand){
		if (max == 1) return 1;
		
		return rand.nextInt(max - min) + min;
	}

	@Override
	public int compareTo(Object o) {
		
		WeightedRandomLoot other = (WeightedRandomLoot)o;
		
		if (this.scale > other.scale) return -1;
		if (this.scale < other.scale) return 1;
		
		if (this.weight > other.weight) return -1;
		if (this.weight < other.weight) return 1;
		
		return 0;
	}

	public boolean roll(Random rand, int rollScale) {
		
		if(rollScale < scale) return false;
		if(weight > 1 && rand.nextInt(weight) != 0) return false;
		
		return true;
	}
}