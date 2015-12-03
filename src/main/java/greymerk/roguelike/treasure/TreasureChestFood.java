package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;

public class TreasureChestFood extends TreasureChest{
	
	@Override
	protected void fillChest(LootSettings loot, int level){

		
		int middle = this.inventory.getInventorySize()/2;
						
		ItemStack item;		
		
		for(int i = 0; i < 3; i++){
			item = loot.get(Loot.FOOD, rand);
			this.setInventorySlot((middle - 1) + i, item);
		}
		
		this.type = Treasure.FOOD;
	}

}
