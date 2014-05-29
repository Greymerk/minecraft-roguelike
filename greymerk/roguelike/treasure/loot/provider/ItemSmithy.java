package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

import java.util.Random;

import net.minecraft.src.ItemStack;

public class ItemSmithy extends ItemBase{

	public ItemSmithy(int weight) {
		super(weight);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return ItemSpecialty.getRandomItem(Equipment.SWORD, rand, Quality.IRON);
	}
	
	

}
