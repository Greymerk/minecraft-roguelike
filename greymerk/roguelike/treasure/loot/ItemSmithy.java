package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.ItemStack;

public class ItemSmithy extends ItemBase{

	public ItemSmithy(int weight) {
		super(weight);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(level == 0){
			return ItemSpecialty.getRandomItem(Equipment.SWORD, rand, Quality.IRON);
		}
		
		return ItemSpecialty.getRandomTool(rand, Quality.IRON);
	}
	
	

}
