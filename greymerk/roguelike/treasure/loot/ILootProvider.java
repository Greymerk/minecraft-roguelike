package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.ItemStack;

public interface ILootProvider {

	ItemStack getLootItem(Random rand, int level);
	
	int getWeight();
	
}
