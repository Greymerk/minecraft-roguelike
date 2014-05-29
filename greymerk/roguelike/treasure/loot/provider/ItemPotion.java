package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.Potion;
import greymerk.roguelike.treasure.loot.PotionMixture;

import java.util.Random;

import net.minecraft.src.ItemStack;

public class ItemPotion extends ItemBase{

	public ItemPotion(int weight) {
		super(weight);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		
		if(level > 2) return PotionMixture.getPotion(rand, PotionMixture.VILE);
		
		if(rand.nextInt(10) == 0) return PotionMixture.getBooze(rand);
		return Potion.getRandom(rand);

	}

}
