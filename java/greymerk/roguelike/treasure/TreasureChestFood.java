package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestFood extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){

		
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;		
		
		for(int i = 0; i < 3; i++){
			item = Loot.getLoot(Loot.FOOD, rand, level);
			chest.setInventorySlotContents((middle - 1) + i, item);
		}
	}

}
