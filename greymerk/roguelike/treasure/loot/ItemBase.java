package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.ItemStack;

public abstract class ItemBase implements ILootProvider {

	private int weight;
	
	public ItemBase(int weight){
		this.weight = weight;
	}
	
	@Override
	public abstract ItemStack getLootItem(Random rand, int level);

	@Override
	public int getWeight() {
		return weight;
	}
}
