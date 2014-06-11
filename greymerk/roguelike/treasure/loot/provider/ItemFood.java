package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFood extends ItemBase{

	public ItemFood(int weight, int level) {
		super(weight, level);
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
		case 0: return new ItemStack(Items.cake);
		case 1: return new ItemStack(Items.egg);
		case 2: return new ItemStack(Items.milk_bucket);
		case 3: return new ItemStack(Items.pumpkin_pie);
		default: return new ItemStack(Items.cookie);
		}
		
		
	}
	
	private static ItemStack pickFood(Random rand, int level) {

		int quantity = 1 + rand.nextInt(2 + level);
		
		switch(level){

		case 4:
		case 3:
			if(rand.nextInt(10) == 0) return new ItemStack(Items.golden_apple);
			if(rand.nextInt(5) == 0) return new ItemStack(Items.golden_carrot, quantity);
		case 2:
			if(rand.nextInt(5) == 0){
				if(rand.nextBoolean()) return new ItemStack(Items.cooked_porkchop, quantity);
				return new ItemStack(Items.cooked_beef, quantity);
			}
			
		case 1:
			if(rand.nextInt(10) == 0) return new ItemStack(Items.melon, quantity);
			
			if(rand.nextInt(5) == 0){
				if(rand.nextBoolean()) return new ItemStack(Items.cooked_chicken, quantity);
				return new ItemStack(Items.baked_potato, quantity);
			}
		case 0:
			if(rand.nextInt(20) == 0) return new ItemStack(Items.apple, 1);
			
			if(rand.nextInt(10) == 0) return new ItemStack(Items.mushroom_stew);
			
			if(rand.nextInt(5) == 0) return new ItemStack(Items.cooked_fished, quantity);
		default:
			return new ItemStack(Items.bread, quantity);
		
		
		
		}
	}


}
