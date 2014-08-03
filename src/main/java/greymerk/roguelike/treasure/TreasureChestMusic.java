package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestMusic extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, LootSettings loot, int level) {

		int middle = chest.getSizeInventory()/2;
				
		ItemStack item;
		
		for (int i = 0; i < 5; i++) {
			item = loot.get(Loot.SUPPLY, rand);
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
		
		item = loot.get(Loot.MUSIC, rand);
		chest.setInventorySlotContents(middle, item);
	}
}
