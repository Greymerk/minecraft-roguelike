package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;

public class TreasureChestTools extends TreasureChest{
	
	@Override
	protected void fillChest(LootSettings loot, int level){

		int middle = this.inventory.getInventorySize()/2;
						
		ItemStack item;		
		
		item = loot.get(Loot.BLOCK, rand);
		this.setInventorySlot(middle - 1, item);
		
		item = loot.get(Loot.TOOL, rand);
		this.setInventorySlot(middle, item);
		
		item = loot.get(Loot.ORE, rand);
		this.setInventorySlot(middle + 1, item);
		
		this.type = Treasure.TOOLS;
	}
}
