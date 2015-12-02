package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestEnchanting extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, LootSettings loot, int level){
		
		int middle = chest.getSizeInventory()/2;
				
		ItemStack item;
		
		item = loot.get(Loot.ENCHANTBONUS, rand);
		chest.setInventorySlotContents(middle - 1, item);
		
		item = loot.get(Loot.ENCHANTBOOK, rand);	
		chest.setInventorySlotContents(middle, item);
		
		item = loot.get(Loot.ENCHANTBONUS, rand);
		chest.setInventorySlotContents(middle + 1, item);
		
		this.type = TreasureChest.ENCHANTING;
	}
	

}
