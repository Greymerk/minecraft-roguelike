package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.util.IWeighted;

import java.util.Random;

import net.minecraft.item.ItemStack;

public abstract class ItemBase implements IWeighted<ItemStack> {

	private int weight;
	int level;
	
	public ItemBase(int weight, int level){
		this.weight = weight;
		this.level = level;
	}
	
	public abstract ItemStack getLootItem(Random rand, int level);

	@Override
	public int getWeight() {
		return weight;
	}
	
	@Override
	public ItemStack get(Random rand) {
		return getLootItem(rand, level);
	}
}
