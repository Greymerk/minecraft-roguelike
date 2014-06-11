package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSupply extends ItemBase{

	public ItemSupply(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		
		if(rand.nextInt(20) == 0) return new ItemStack(Items.carrot, 1);
		if(rand.nextInt(20) == 0) return new ItemStack(Items.potato, 1);

		switch(rand.nextInt(8)){
		case 0: return new ItemStack(Items.wheat_seeds, rand.nextInt(8) + 1);
		case 1: return new ItemStack(Items.pumpkin_seeds, rand.nextInt(8) + 1);
		case 2: return new ItemStack(Items.melon_seeds, rand.nextInt(8) + 1);		
		case 3: return new ItemStack(Items.wheat, rand.nextInt(8) + 1);
		case 4: return new ItemStack(Blocks.torch, 10 + rand.nextInt(10));
		case 5: return new ItemStack(Items.paper, rand.nextInt(8) + 1);
		case 6:	return new ItemStack(Items.book, rand.nextInt(4) + 1);
		case 7:	return new ItemStack(Blocks.sapling, rand.nextInt(4) + 1, rand.nextInt(4));
		default: return new ItemStack(Items.stick, 1);
		}
	}
}
