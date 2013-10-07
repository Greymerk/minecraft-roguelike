package greymerk.roguelike;

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
		int middle = chest.getSizeInventory()/2;
		
		ItemStack item;
		
		item = ItemNovelty.getItemByRank(rand, Dungeon.getRank(posY));

		chest.setInventorySlotContents(middle, item);
	}
	
}
		
