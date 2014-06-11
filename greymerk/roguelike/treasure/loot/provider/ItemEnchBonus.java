package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEnchBonus extends ItemBase{

	public ItemEnchBonus(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(rand.nextBoolean()) return new ItemStack(Items.experience_bottle, 1 + rand.nextInt(5));
		return new ItemStack(Items.ender_pearl, 1 + rand.nextInt(2));
	}

	
}
