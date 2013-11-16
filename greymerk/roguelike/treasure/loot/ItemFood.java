package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemFood {

	public static ItemStack getRandom(Random rand, int rank){
		
		ItemNovelty[] items = {
				ItemNovelty.RLEAHY,
				ItemNovelty.GINGER,
				ItemNovelty.GENERIKB,
				ItemNovelty.ASHLEA,
				ItemNovelty.CLEO
		};
		
		if(rank > 0 && rand.nextInt(100) == 0){
			return ItemNovelty.getItem(items[rand.nextInt(items.length)]);
		}
		
		return pickFood(rand, rank);
	}
	
	private static ItemStack pickFood(Random rand, int rank) {

		int quantity = 2 + rand.nextInt(6);

		int choice = rand.nextInt(10);

		switch(choice){
		case 0:
			if(rank >= 3){
				return new ItemStack(Item.appleGold, quantity);
			}
			
			return new ItemStack(Item.appleRed, quantity);
			
		case 1:
			
			if(rank > 1){
				return new ItemStack(Item.appleGold, quantity);
			}
			
			return new ItemStack(Item.appleRed, quantity);
			
		case 2:
			return new ItemStack(Item.bowlSoup);
			
		case 3:
			return new ItemStack(Item.beefCooked, quantity);
			
		case 4:
			if(rank > 1){
				return new ItemStack(Item.goldenCarrot, quantity);
			}
			
			return new ItemStack(Item.bread, quantity);
		
		case 5:
			return new ItemStack(Item.porkCooked, quantity);
			
		case 6:
			
			if(rank > 1){
				return new ItemStack(Item.goldenCarrot, quantity);
			}
			
			return new ItemStack(Item.melon, quantity);
			
		case 7:
			return new ItemStack(Item.chickenCooked, quantity);
			
		case 8:
			if(rank > 1){
				return new ItemStack(Item.goldenCarrot, quantity);
			}
			
			return new ItemStack(Item.fishCooked, quantity);
		
		case 9:
			return new ItemStack(Item.bakedPotato, quantity);

		}
		return new ItemStack(Item.bread, quantity);
	}


	
}
