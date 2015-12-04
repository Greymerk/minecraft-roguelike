package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.item.ItemStack;

public interface ILoot {

	public ItemStack get(Random rand, Loot type, int level);
	
}
