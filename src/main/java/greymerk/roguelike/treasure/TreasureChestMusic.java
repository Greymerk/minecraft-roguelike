package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;

public class TreasureChestMusic extends TreasureChest{
	
	@Override
	protected void fillChest(LootSettings loot, int level) {

		int middle = this.inventory.getInventorySize()/2;
				
		ItemStack item;
		
		for (int i = 0; i < 5; i++) {
			item = loot.get(Loot.SUPPLY, rand);
			this.setRandomEmptySlot(item);
		}
		
		item = loot.get(Loot.MUSIC, rand);
		this.setInventorySlot(middle, item);
		
		this.type = Treasure.MUSIC;
	}
}
