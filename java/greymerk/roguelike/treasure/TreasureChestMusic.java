package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

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
