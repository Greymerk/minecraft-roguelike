package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;

public class TreasureChestSupplies extends TreasureChest{
	
	@Override
	protected void fillChest(LootSettings loot, int level) {


		ItemStack item;
		
		for (int i = 0; i < 10; i++) {
			item = loot.get(Loot.SUPPLY, rand);
			this.setRandomEmptySlot(item);
		}
		
		this.type = Treasure.SUPPLIES;
	}
}
