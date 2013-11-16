package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemOre {

	public static ItemStack getRandom(Random rand, int rank){
		
		if(rank < 2 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(ItemNovelty.MCGAMER);
		}
		return pickOre(rand, rank);
	}
	
	private static ItemStack pickOre(Random rand, int rank) {

		int quantity = 1 + rand.nextInt(4);		
		
		switch(rank){
		
		case 3:

			
			if (rand.nextInt(10) == 0) {
				return new ItemStack(Item.diamond, quantity);
			}

			if(rand.nextInt(5) == 0){
				return new ItemStack(Item.emerald, quantity);
			}
			
			if(rand.nextBoolean()){
				return new ItemStack(Item.diamond, 1);
			}
			
			return new ItemStack(Item.ingotIron, quantity * 3);

		case 2:
			
			if (rand.nextInt(15) == 0){
				return new ItemStack(Item.diamond, 1);
			}
			
			if(rand.nextInt(10) == 0){
				return new ItemStack(Item.emerald, quantity * 2);
			}
			
			if (rand.nextInt(10) == 0) {
				return new ItemStack(Item.ingotGold, quantity);
			}
			
			if(rand.nextInt(5) == 0){
				return new ItemStack(Item.coal, quantity * 4);
			}
			
			return new ItemStack(Item.ingotIron, quantity * 2);
			
		case 1:
			
			if (rand.nextInt(50) == 0){
				return new ItemStack(Item.diamond, 1);
			}
			
			if(rand.nextInt(30) == 0){
				return new ItemStack(Item.emerald, quantity);
			}
			
			if(rand.nextInt(3) == 0){
				return new ItemStack(Item.coal, quantity * 2);
			}
			
			return new ItemStack(Item.ingotIron, quantity);
			
		case 0:
			
			if(rand.nextInt(3) == 0){
				return new ItemStack(Item.leather, 1 + rand.nextInt(3));
			}
			
			if(rand.nextBoolean()){
				return new ItemStack(Item.ingotIron, 1);
			}
			
			return new ItemStack(Item.coal, quantity);
			
		default:
			
			return new ItemStack(Item.coal, 1);
		}
				
	}
	
}
