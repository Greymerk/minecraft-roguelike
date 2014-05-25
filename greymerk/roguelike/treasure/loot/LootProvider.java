package greymerk.roguelike.treasure.loot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class LootProvider implements ILootProvider{

	private int weight;
	private int[] weightSum = {0, 0, 0, 0, 0};
	private Map<Integer, List<ILootProvider>> loot;
	
	public LootProvider(int weight){
		this.weight = weight;
		loot = new HashMap<Integer, List<ILootProvider>>();
		for(int i = 0; i < 5; ++i){
			loot.put(i, new ArrayList<ILootProvider>());
		}
	}
	
	public void clear(){
		for(int i = 0; i < 5; ++i){
			weightSum[i] = 0;
			loot.put(i, new ArrayList<ILootProvider>());
		}
		
	}
	
	public void add(int level, ILootProvider toAdd){
		weightSum[level] += toAdd.getWeight();
		List<ILootProvider> list = this.loot.get(level);
		list.add(toAdd);
	}
	
	public void addAllLevels(ILootProvider toAdd){
		for(int i = 0; i < 5; ++i){
			weightSum[i] += toAdd.getWeight();
			List<ILootProvider> list = this.loot.get(i);
			list.add(toAdd);
		}
	}
	
	public ItemStack getLootItem(Random rand, int level){
		
		if (weightSum[level] == 0) return new ItemStack(Item.stick);
		if (loot.isEmpty()) return new ItemStack(Item.stick);
		
		int roll = rand.nextInt(weightSum[level]);
		
		for(ILootProvider l : this.loot.get(level)){
			roll -= l.getWeight();
			if(roll < 0) return l.getLootItem(rand, level);
		}
		
		return new ItemStack(Item.stick);
	}

	@Override
	public int getWeight() {
		return weight;
	}
}
