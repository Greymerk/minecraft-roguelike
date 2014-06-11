package greymerk.roguelike.treasure.loot;


import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;

public class LootProvider{

	private static final int NUM_LEVELS = 5;
	private Map<Integer, WeightedRandomizer<ItemStack>> loot;
	
	public LootProvider(){
		loot = new HashMap<Integer, WeightedRandomizer<ItemStack>>();
		for(int i = 0; i < NUM_LEVELS; ++i){
			loot.put(i, new WeightedRandomizer<ItemStack>());
		}
	}
	
	public void clear(){
		for(int i = 0; i < NUM_LEVELS; ++i){
			loot.put(i, new WeightedRandomizer<ItemStack>());
		}
		
	}
	
	public void add(int level, IWeighted<ItemStack> toAdd){
		this.loot.get(level).add(toAdd);
	}
	
	public void addAllLevels(IWeighted<ItemStack> toAdd){
		for(int i = 0; i < NUM_LEVELS; ++i){
			this.loot.get(i).add(toAdd);
		}
	}

	public ItemStack get(Random rand, int level) {
		 WeightedRandomizer<ItemStack> randomizer = this.loot.get(level);
		 return randomizer.get(rand);
	}
}
