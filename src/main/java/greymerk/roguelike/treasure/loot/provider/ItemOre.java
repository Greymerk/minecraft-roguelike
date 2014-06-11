package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemOre extends ItemBase{

	public ItemOre(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(level < 2 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.MCGAMER);
		}
		return pickOre(rand, level);
	}
	
	private static ItemStack pickOre(Random rand, int level) {

		switch(level){
		
		case 4:
			if (rand.nextInt(10) == 0) return new ItemStack(Items.gold_ingot, 2 + rand.nextInt(4));
			if (rand.nextInt(5) == 0) return new ItemStack(Items.emerald, 1);
			if (rand.nextInt(3) == 0) return new ItemStack(Items.diamond, 1);
			return new ItemStack(Items.iron_ingot, 4 + rand.nextInt(5));
		case 3:
			if (rand.nextInt(10) == 0) return new ItemStack(Items.diamond, 1);
			if (rand.nextInt(10) == 0) return new ItemStack(Items.gold_ingot, 2 + rand.nextInt(4));
			if (rand.nextInt(3) == 0) return new ItemStack(Items.emerald, 1);
			return new ItemStack(Items.iron_ingot, 3 + rand.nextInt(5));
		case 2:
			if (rand.nextInt(30) == 0) return new ItemStack(Items.diamond, 1);
			if(rand.nextInt(30) == 0) return new ItemStack(Items.emerald, 1);
			if(rand.nextInt(10) == 0) return new ItemStack(Items.iron_ingot, 3 + rand.nextInt(5));
		case 1:			
		case 0:
			if(rand.nextInt(10) == 0) return new ItemStack(Items.iron_ingot, 1);
			if(rand.nextInt(5) == 0) return new ItemStack(Items.leather, 1 + rand.nextInt(5) * level);
			return new ItemStack(Items.coal, 1 + level + rand.nextInt(2 + level));
		default:
			return new ItemStack(Items.coal, 1);
		}
				
	}


}
