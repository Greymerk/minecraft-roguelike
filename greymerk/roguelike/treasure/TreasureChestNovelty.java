package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.ItemNovelty;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestNovelty extends TreasureChestBase{

	@Override
	protected void fillChest(TileEntityChest chest){

		ItemNovelty choice = ItemNovelty.values()[rand.nextInt(ItemNovelty.values().length)];
		ItemStack item = ItemNovelty.getItem(choice);

		int middle = chest.getSizeInventory()/2;
		chest.setInventorySlotContents(middle, item);
	}
	
}
		
