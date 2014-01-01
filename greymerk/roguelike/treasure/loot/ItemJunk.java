package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemJunk {

	public static ItemStack getRandom(Random rand, int level){
		
		if(rand.nextInt(1000) == 0){
			
			if(rand.nextBoolean()){
				return ItemNovelty.getItem(ItemNovelty.VECHS);
			}
			
			switch(level){
			case 0: return ItemNovelty.getItem(ItemNovelty.GRIM);
			case 1: return ItemNovelty.getItem(ItemNovelty.FOURLES);
			case 2: return ItemNovelty.getItem(ItemNovelty.ZISTEAUSIGN);
			case 3: return ItemNovelty.getItem(ItemNovelty.PAULSOARESJR);
			case 4: return ItemNovelty.getItem(ItemNovelty.DINNERBONE);
			}
		}
		
		if(level > 0 && rand.nextInt(500) == 0){
			// diamond horse armour
			if(level > 2 && rand.nextInt(10) == 0){
				return new ItemStack(Item.horseArmorDiamond.itemID, 1, 0);
			}
			
			// gold horse armour
			if(level > 1 && rand.nextInt(5) == 0){
				return new ItemStack(Item.horseArmorGold, 1, 0);
			}
			
			// iron horse armour
			return new ItemStack(Item.horseArmorIron, 1, 0);
		}
		
		if(level > 0 && rand.nextInt(300) == 0){
			return new ItemStack(Item.saddle);
		}
		
		if(level > 1 && rand.nextInt(100) == 0){
			return new ItemStack(Item.ghastTear);
		}
		
		if(level > 0 && rand.nextInt(50) == 0){
			switch(rand.nextInt(5)){
			case 0: return new ItemStack(Item.gunpowder);
			case 1: return new ItemStack(Item.blazePowder);
			case 2: return new ItemStack(Item.goldNugget);
			case 3: return new ItemStack(Item.redstone);
			case 4: return new ItemStack(Item.glowstone);
			}
		}
		
		if(rand.nextInt(30) == 0){
			if(rand.nextBoolean()){
				return ItemBlock.getRandom(rand, level);
			} else {
				return ItemFood.getRandom(rand, level);
			}
		}

		if(level < 3 && rand.nextInt(20) == 0){
			return new ItemStack(Item.book);
		}
		
		if(rand.nextInt(10) == 0){
			switch(rand.nextInt(7)){
			case 0: return new ItemStack(Item.slimeBall);
			case 1: return new ItemStack(Item.snowball);
			case 2: return new ItemStack(Item.bowlEmpty);
			case 3: return new ItemStack(Item.clay);
			case 4: return new ItemStack(Item.flint);
			case 5: return new ItemStack(Item.feather);
			case 6: return new ItemStack(Item.glassBottle, 1 + rand.nextInt(3));
			}
		}
		
		switch(rand.nextInt(6)){
		case 0: return new ItemStack(Item.bone);
		case 1: return new ItemStack(Item.rottenFlesh);
		case 2: return new ItemStack(Item.spiderEye);
		case 3: return new ItemStack(Item.stick);
		case 4: return new ItemStack(Item.silk);
		case 5: return new ItemStack(Item.stick);
		default: return new ItemStack(Item.stick);
		}
	}
	
}
