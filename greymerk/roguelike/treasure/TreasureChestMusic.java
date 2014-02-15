package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.ItemNovelty;
import greymerk.roguelike.treasure.loot.ItemRecord;
import greymerk.roguelike.treasure.loot.Loot;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestMusic extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level) {

		int middle = chest.getSizeInventory()/2;
				
		ItemStack item;
		
		for (int i = 0; i < 5; i++) {
			item = Loot.getSupplyItem(rand, level);
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
		
		if(rand.nextInt(10) == 0){
			chest.setInventorySlotContents(middle, ItemNovelty.getItem(ItemNovelty.GUUDE));
		} else {
			chest.setInventorySlotContents(middle, ItemRecord.getRandomRecord(rand));
		}
	}
}
