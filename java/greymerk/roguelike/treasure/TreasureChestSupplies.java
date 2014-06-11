package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestSupplies extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level) {

		int middle = chest.getSizeInventory()/2;
				
		ItemStack item;
		
		for (int i = 0; i < 10; i++) {
			item = Loot.getSupplyItem(rand, level);
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
