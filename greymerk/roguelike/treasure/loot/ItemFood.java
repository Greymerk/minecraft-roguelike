package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemFood extends ItemBase{

	public ItemFood(int weight) {
		super(weight);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return getRandom(rand, level);
	}		
	
	public static ItemStack getRandom(Random rand, int rank){
		
		ItemNovelty[] items = {
				ItemNovelty.GINGER,
				ItemNovelty.GENERIKB,
				ItemNovelty.CLEO
		};
		
		if(rank > 0 && rand.nextInt(500) == 0){
			return ItemNovelty.getItem(items[rand.nextInt(items.length)]);
		}
		
		return pickFood(rand, rank);
	}
	
	
	
	public static ItemStack getDessert(Random rand){
		
		switch(rand.nextInt(4)){
		case 0: return new ItemStack(Item.cake);
		case 1: return new ItemStack(Item.egg);
		case 2: return new ItemStack(Item.bucketMilk);
		case 3: return new ItemStack(Item.pumpkinPie);
		default: return new ItemStack(Item.cookie);
		}
		
		
	}
	
	private static ItemStack pickFood(Random rand, int level) {

		int quantity = 1 + rand.nextInt(2 + level);
		
		switch(level){

		case 4:
		case 3:
			if(rand.nextInt(10) == 0) return new ItemStack(Item.goldenCarrot, quantity);
			
			if(rand.nextInt(10) == 0) return new ItemStack(Item.appleGold, 1);
		case 2:
			if(rand.nextInt(5) == 0){
				if(rand.nextBoolean()) return new ItemStack(Item.porkCooked, quantity);
				return new ItemStack(Item.beefCooked, quantity);
			}
			
		case 1:
			if(rand.nextInt(10) == 0) return new ItemStack(Item.melon, quantity);
			
			if(rand.nextInt(5) == 0){
				if(rand.nextBoolean()) return new ItemStack(Item.chickenCooked, quantity);
				return new ItemStack(Item.bakedPotato, quantity);
			}
		case 0:
			if(rand.nextInt(20) == 0) return new ItemStack(Item.appleRed, 1);
			
			if(rand.nextInt(10) == 0) return new ItemStack(Item.bowlSoup);
			
			if(rand.nextInt(5) == 0) return new ItemStack(Item.fishCooked, quantity);
		default:
			return new ItemStack(Item.bread, quantity);
		
		
		
		}
	}


}
