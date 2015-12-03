package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;

public class TreasureChestSpecialty extends TreasureChest{
	
	@Override
	protected void fillChest(LootSettings loot, int level){

		int middle = this.inventory.getInventorySize()/2;
						
		ItemStack item;		
		
		item = loot.get(Loot.SPECIAL, rand);
		this.setInventorySlot(middle, item);
		
		this.type = Treasure.SPECIAL;
	}
}
