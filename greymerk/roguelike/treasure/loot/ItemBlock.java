package greymerk.roguelike.treasure.loot;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

public class ItemBlock {
	
	public static ItemStack getRandom(Random rand, int rank){
		
		if(rank > 0 && rand.nextInt(300) == 0){
			if(rand.nextBoolean()){
				return ItemNovelty.getItem(ItemNovelty.MMILLSS);
			} else {
				return ItemNovelty.getItem(ItemNovelty.QUANTUMLEAP);
			}
		}
		
		return pickBlocks(rand, rank);
	}
	
	private static ItemStack pickBlocks(Random rand, int rank) {

		if(rank > 1 && rand.nextInt((5 - rank) * 50) == 0){
			return new ItemStack(Block.blockDiamond, 1);
		}
		
		if(rank > 1 && rand.nextInt((5 - rank) * 30) == 0){
			switch(rand.nextInt(3)){
			case 0: return new ItemStack(Block.blockLapis, 2 + rand.nextInt(6));
			case 1: return new ItemStack(Block.blockEmerald, 1 + rand.nextInt(3));
			case 2: return new ItemStack(Block.blockGold, 1);
			}
		}
		
		if(rank > 0 && rand.nextInt((5 - rank) * 20) == 0){
			switch(rand.nextInt(6)){
			case 0: return new ItemStack(Block.blockIron, 1);
			case 1: return new ItemStack(Block.coalBlock, 1 + rand.nextInt(rank + 1));
			case 2: return new ItemStack(Block.blockRedstone, 1);
			case 3: return new ItemStack(Block.tnt, 2 * rand.nextInt(rank + 1));
			case 4: return new ItemStack(Block.glowStone, 3 + rand.nextInt(1 + rank * 2));
			case 5: return new ItemStack(Block.glass, 1 + rand.nextInt(1 + rank * 2));
			}
		}
		
		if(rank == 3){
			if(rand.nextBoolean()){
				return new ItemStack(Block.netherBrick, 5 + rand.nextInt(10));
			} else {
				return new ItemStack(Block.blockNetherQuartz, 5 + rand.nextInt(10));
			}
		}
		
		switch (rand.nextInt(4)) {
		case 0: return new ItemStack(Block.planks, 5 + rand.nextInt(10));
		case 1: return new ItemStack(Block.cobblestone, 5 + rand.nextInt(1 + rank * 5));
		case 2: return new ItemStack(Block.stoneBrick, 5 + rand.nextInt(1 + rank * 5));
		case 3: return new ItemStack(Block.wood, 1 + rank + rand.nextInt(1 + rank * 3));
		default: 
			return new ItemStack(Block.dirt, 5 + rand.nextInt(10));
		}
	}

}
