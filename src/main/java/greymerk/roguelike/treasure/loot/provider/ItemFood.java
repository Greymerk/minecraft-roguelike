package greymerk.roguelike.treasure.loot.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.util.WeightedRandomizer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemFood extends ItemBase{

	private Map<Integer, WeightedRandomizer<ItemStack>> loot;
	
	public ItemFood(int weight, int level) {
		super(weight, level);
		this.loot = new HashMap<Integer, WeightedRandomizer<ItemStack>>();
		for(int i = 0; i < 5; ++i){
			
			WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<ItemStack>();
			
			switch(i){
			case 4:
				randomizer.add(new WeightedRandomLoot(Items.golden_apple, 0, 1, 1, 1));
				randomizer.add(new WeightedRandomLoot(Items.golden_carrot, 0, 1, 1, 2));
				randomizer.add(new WeightedRandomLoot(Items.cooked_beef, 0, 1, 5, 3));
				randomizer.add(new WeightedRandomLoot(Items.cooked_porkchop, 0, 1, 5, 3));
				break;
			case 3:
				randomizer.add(new WeightedRandomLoot(Items.cooked_beef, 0, 1, 3, 3));
				randomizer.add(new WeightedRandomLoot(Items.cooked_porkchop, 0, 1, 3, 3));
				randomizer.add(new WeightedRandomLoot(Items.cooked_chicken, 0, 1, 2, 1));
				randomizer.add(new WeightedRandomLoot(Items.baked_potato, 0, 1, 2, 1));
				break;
			case 2:
				randomizer.add(new WeightedRandomLoot(Items.cooked_beef, 0, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.cooked_porkchop, 0, 1, 3, 1));
				randomizer.add(new WeightedRandomLoot(Items.cooked_chicken, 0, 1, 2, 2));
				randomizer.add(new WeightedRandomLoot(Items.baked_potato, 0, 1, 2, 2));
				break;
			case 1:	
				randomizer.add(new WeightedRandomLoot(Items.bread, 0, 1, 3, 5));
				randomizer.add(new WeightedRandomLoot(Items.cooked_fished, 0, 1, 3, 5));
				randomizer.add(new WeightedRandomLoot(Items.apple, 0, 1, 3, 2));
				randomizer.add(new WeightedRandomLoot(Items.cooked_chicken, 0, 1, 2, 2));
				randomizer.add(new WeightedRandomLoot(Items.baked_potato, 0, 1, 2, 2));
				break;
			case 0:
				randomizer.add(new WeightedRandomLoot(Items.bread, 0, 1, 2, 5));
				randomizer.add(new WeightedRandomLoot(Items.cooked_fished, 0, 1, 2, 5));
				randomizer.add(new WeightedRandomLoot(Items.apple, 0, 1, 2, 5));
				randomizer.add(new WeightedRandomLoot(Items.cookie, 0, 1, 4, 1));
				break;
			default:
				randomizer.add(new WeightedRandomLoot(Items.bread, 1));
			}
			
			loot.put(i, randomizer);
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return this.loot.get(level).get(rand);
	}


}
