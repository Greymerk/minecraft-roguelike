package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestFood extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, LootSettings loot, int level){

		
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;		
		
		for(int i = 0; i < 3; i++){
			item = loot.get(Loot.FOOD, rand);
			chest.setInventorySlotContents((middle - 1) + i, item);
		}
	}

}
