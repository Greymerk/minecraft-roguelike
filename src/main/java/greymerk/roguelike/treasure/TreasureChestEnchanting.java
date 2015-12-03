package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;

public class TreasureChestEnchanting extends TreasureChest{
	
	@Override
	protected void fillChest(LootSettings loot, int level){
		
		int middle = this.inventory.getInventorySize()/2;
				
		ItemStack item;
		
		item = loot.get(Loot.ENCHANTBONUS, rand);
		this.setInventorySlot(middle - 1, item);
		
		item = loot.get(Loot.ENCHANTBOOK, rand);	
		this.setInventorySlot(middle, item);
		
		item = loot.get(Loot.ENCHANTBONUS, rand);
		this.setInventorySlot(middle + 1, item);
		
		this.type = Treasure.ENCHANTING;
	}
	

}
