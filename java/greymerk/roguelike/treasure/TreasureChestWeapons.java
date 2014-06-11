package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestWeapons extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){
				
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;
		
		item = Loot.getLoot(Loot.POTION, rand, level);
		chest.setInventorySlotContents(middle - 1, item);
		
		item = Loot.getLoot(Loot.WEAPON, rand, level);
		chest.setInventorySlotContents(middle, item);
		
		item = Loot.getLoot(Loot.FOOD, rand, level);
		chest.setInventorySlotContents(middle + 1, item);
	}

}
