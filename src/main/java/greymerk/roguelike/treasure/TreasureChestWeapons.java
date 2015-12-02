package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestWeapons extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, LootSettings loot, int level){
				
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;
		
		item = loot.get(Loot.POTION, rand);
		chest.setInventorySlotContents(middle - 1, item);
		
		item = loot.get(Loot.WEAPON, rand);
		chest.setInventorySlotContents(middle, item);
		
		item = loot.get(Loot.FOOD, rand);
		chest.setInventorySlotContents(middle + 1, item);
		
		this.type = TreasureChest.WEAPONS;
	}

}
