package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.ItemNovelty;
import greymerk.roguelike.treasure.loot.Record;
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
			item = Loot.getLoot(Loot.SUPPLY, rand, level);
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
		
		item = Loot.getLoot(Loot.MUSIC, rand, level);
		chest.setInventorySlotContents(middle, item);
	}
}
