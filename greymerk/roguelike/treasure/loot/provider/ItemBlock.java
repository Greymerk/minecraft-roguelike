package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.config.RogueConfig;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

public class ItemBlock extends ItemBase{
	
	public ItemBlock(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return getRandom(rand, level);
	}
	
	public static ItemStack getRandom(Random rand, int level){
		
		if(level > 0 && rand.nextInt(500) == 0){
			if(rand.nextBoolean()){
				return ItemNovelty.getItem(ItemNovelty.MMILLSS);
			} else {
				return ItemNovelty.getItem(ItemNovelty.QUANTUMLEAP);
			}
		}
		
		return pickBlocks(rand, level);
	}
	
	private static ItemStack pickBlocks(Random rand, int level) {

		int size = 64;
		
		switch(level){
		case 0:
		case 1:
			if(rand.nextInt(10) == 0) return new ItemStack(Block.cloth, 16, rand.nextInt(16));
			if(rand.nextInt(10) == 0) return new ItemStack(Block.wood, 16, rand.nextInt(4));
			
			switch(rand.nextInt(3)){
			case 0: return new ItemStack(Block.stoneBrick, size, rand.nextInt(3));
			case 1: return new ItemStack(Block.planks, size, rand.nextInt(4));
			case 2: return new ItemStack(Block.dirt, size);
			}
		case 2:
			if(rand.nextInt(10) == 0) return new ItemStack(Block.brick, size, rand.nextInt(16));
			
			switch(rand.nextInt(3)){
			case 0: return new ItemStack(Block.stoneBrick, size, rand.nextInt(3));
			case 1: return new ItemStack(Block.gravel, size, rand.nextInt(4));
			case 2: return new ItemStack(Block.stainedClay, 16, rand.nextInt(16));
			}
		case 3:
			switch(rand.nextInt(5)){
			case 0: return new ItemStack(Block.cobblestoneMossy, size);
			case 1: return new ItemStack(Block.cobblestone, size);
			case 2: return new ItemStack(Block.gravel, 16);
			case 3: return new ItemStack(Block.blockClay, 16);
			case 4: return new ItemStack(Block.stone, size);
			}
		case 4:
			if(rand.nextInt(10) == 0) return new ItemStack(Block.glowStone, 8);
			if(rand.nextInt(10) == 0) return new ItemStack(Block.blockNetherQuartz, 8);
			
			switch(rand.nextInt(4)){
			case 0: return new ItemStack(Block.netherrack, size);
			case 1: return new ItemStack(Block.netherBrick, size);
			case 2: return new ItemStack(Block.slowSand, 16);
			case 3: return new ItemStack(Block.obsidian, 16);
			}
		default: return new ItemStack(Block.dirt, size);
		}
		
		
	}



}
