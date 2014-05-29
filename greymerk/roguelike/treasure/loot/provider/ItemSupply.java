package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemSupply extends ItemBase{

	public ItemSupply(int weight) {
		super(weight);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		
		if(rand.nextInt(20) == 0) return new ItemStack(Item.carrot, 1);
		if(rand.nextInt(20) == 0) return new ItemStack(Item.potato, 1);

		switch(rand.nextInt(8)){
		case 0: return new ItemStack(Item.seeds, rand.nextInt(8) + 1);
		case 1: return new ItemStack(Item.pumpkinSeeds, rand.nextInt(8) + 1);
		case 2: return new ItemStack(Item.melonSeeds, rand.nextInt(8) + 1);		
		case 3: return new ItemStack(Item.wheat, rand.nextInt(8) + 1);
		case 4: return new ItemStack(Block.torchWood, 10 + rand.nextInt(10));
		case 5: return new ItemStack(Item.paper, rand.nextInt(8) + 1);
		case 6:	return new ItemStack(Item.book, rand.nextInt(4) + 1);
		case 7:	return new ItemStack(Block.sapling, rand.nextInt(4) + 1, rand.nextInt(4));
		default: return new ItemStack(Item.stick, 1);
		}
	}
}
