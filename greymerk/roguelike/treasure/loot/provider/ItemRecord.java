package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.Record;

import java.util.Random;

import net.minecraft.src.ItemStack;

public class ItemRecord extends ItemBase{

	public ItemRecord(int weight) {
		super(weight);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return Record.getRandomRecord(rand);
	}
	
	

}
