package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestSpecialty extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest, int level){

		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;		
		
		item = Loot.getLoot(Loot.SPECIAL, rand, level);
		chest.setInventorySlotContents(middle, item);
	}
}
