package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.ItemStack;

public class PotionProvider extends ItemBase{

	public PotionProvider(int weight) {
		super(weight);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return Potion.getRandom(rand);
	}

}
