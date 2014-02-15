package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemOre {

	public static ItemStack getRandom(Random rand, int level){
		
		if(level < 2 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.MCGAMER);
		}
		return pickOre(rand, level);
	}
	
	private static ItemStack pickOre(Random rand, int level) {

		switch(level){
		
		case 4:
			if (rand.nextInt(10) == 0) return new ItemStack(Item.diamond, 1);
			if (rand.nextInt(5) == 0) return new ItemStack(Item.emerald, 1);
			if (rand.nextInt(10) == 0) return new ItemStack(Item.ingotIron, 4 + rand.nextInt(5));
		case 3:
			if (rand.nextInt(15) == 0) return new ItemStack(Item.diamond, 1);
			if (rand.nextInt(10) == 0) return new ItemStack(Item.ingotGold, 2 + rand.nextInt(4));
			if(rand.nextInt(5) == 0) return new ItemStack(Item.ingotIron, 3 + rand.nextInt(5));
		case 2:
			if (rand.nextInt(50) == 0) return new ItemStack(Item.diamond, 1);
			if(rand.nextInt(30) == 0) return new ItemStack(Item.emerald, 1);
			if(rand.nextInt(10) == 0) return new ItemStack(Item.ingotIron, 3 + rand.nextInt(5));
		case 1:			
		case 0:
			if(rand.nextInt(10) == 0) return new ItemStack(Item.ingotIron, 1);
			if(rand.nextInt(5) == 0) return new ItemStack(Item.leather, 1 + rand.nextInt(3) * level);
			return new ItemStack(Item.coal, 1 + level + rand.nextInt(2 + level));
		default:
			return new ItemStack(Item.coal, 1);
		}
				
	}
	
}
