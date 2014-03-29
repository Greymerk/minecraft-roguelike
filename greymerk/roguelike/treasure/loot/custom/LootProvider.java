package greymerk.roguelike.treasure.loot.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class LootProvider {

	private int weightSum;
	private List<WeightedRandomLoot> lootList;
	
	public LootProvider(){
		weightSum = 0;
		lootList = new ArrayList<WeightedRandomLoot>();
	}
	
	public void add(WeightedRandomLoot loot){
		weightSum += loot.getWeight();
		lootList.add(loot);
	}
	
	public ItemStack getLoot(Random rand){
		
		if (weightSum == 0) return new ItemStack(Item.stick);
		if (lootList.isEmpty()) return new ItemStack(Item.stick);
		
		int roll = rand.nextInt(weightSum);
		
		for(WeightedRandomLoot loot : lootList){
			roll -= loot.getWeight();
			if(roll < 0) return loot.getItem(rand);
		}
		
		return new ItemStack(Item.stick);
	}
}
