package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemEnchBook extends ItemBase{

	public ItemEnchBook(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		ItemStack book = new ItemStack(Items.BOOK);
		Enchant.enchantItem(rand, book, Enchant.getLevel(rand, level));
		return book;
	}

}
