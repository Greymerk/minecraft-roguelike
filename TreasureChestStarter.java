package greymerk.roguelike;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestStarter extends TreasureChestBase{

	@Override
	protected void fillChest(TileEntityChest chest) {

		int size;
		
		int middle;
		try{
			size = chest.getSizeInventory();
		} catch(NullPointerException e){
			return;
		}
				
		int quantity = 20;

		for (int i = 0; i < quantity; i++) {
			ItemStack item;
			item = ItemLoot.getStarterLoot(rand);
			chest.setInventorySlotContents(rand.nextInt(size), item);
		}
	}
}
