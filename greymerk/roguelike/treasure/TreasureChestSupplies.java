package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.ItemLoot;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestSupplies extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest) {

		int middle = chest.getSizeInventory()/2;
				
		ItemStack item;
		
		for (int i = 0; i < 15; i++) {
			item = ItemLoot.getSupplyItem(rand, Catacomb.getRank(posY));
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
