package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

public class ItemBlock {
	
	public static ItemStack getRandom(Random rand, int level){
		
		if(level > 0 && rand.nextInt(1000) == 0){
			if(rand.nextBoolean()){
				return ItemNovelty.getItem(ItemNovelty.MMILLSS);
			} else {
				return ItemNovelty.getItem(ItemNovelty.QUANTUMLEAP);
			}
		}
		
		return pickBlocks(rand, level);
	}
	
	private static ItemStack pickBlocks(Random rand, int level) {


		
		if(level > 2 && rand.nextInt(20) == 0){
			
			if(rand.nextInt(50) == 0){
				return new ItemStack(Block.blockDiamond, 1);
			}
			
			switch(rand.nextInt(3)){
			case 0: return new ItemStack(Block.blockLapis, 1 + rand.nextInt(6));
			case 1: return new ItemStack(Block.blockEmerald, 1 + rand.nextInt(3));
			case 2: return new ItemStack(Block.blockGold, 1);
			}
		}
		
		if(level > 0 && rand.nextInt(20) == 0){
			switch(rand.nextInt(6)){
			case 0: return new ItemStack(Block.blockIron, 1);
			case 1: return new ItemStack(Block.coalBlock, 3 + rand.nextInt(1 + level * 2));
			case 2: return new ItemStack(Block.blockRedstone, 1);
			case 3: return new ItemStack(Block.tnt, 2 * rand.nextInt(level + 1));
			case 4: return new ItemStack(Block.glowStone, 3 + rand.nextInt(1 + level * 2));
			case 5: return new ItemStack(Block.glass, 1 + rand.nextInt(1 + level * 2));
			}
		}
		
		if(level > 3){
			if(rand.nextBoolean()){
				return new ItemStack(Block.netherBrick, 5 + rand.nextInt(10));
			} else {
				return new ItemStack(Block.blockNetherQuartz, 5 + rand.nextInt(10));
			}
		}
		
		switch (rand.nextInt(4)) {
		case 0: return new ItemStack(Block.planks, 5 + rand.nextInt(10));
		case 1: return new ItemStack(Block.cobblestone, 5 + rand.nextInt(1 + level * 5));
		case 2: return new ItemStack(Block.stoneBrick, 5 + rand.nextInt(1 + level * 5));
		case 3: return new ItemStack(Block.wood, 1 + level + rand.nextInt(1 + level * 3));
		default: 
			return new ItemStack(Block.dirt, 5 + rand.nextInt(10));
		}
	}

}
