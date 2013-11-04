package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.ItemSpecialty;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestSmithy extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){

		int middle = chest.getSizeInventory()/2;
						
		chest.setInventorySlotContents(middle - 2, ItemSpecialty.getItem(ItemSpecialty.SWORD, rand, 2));
		chest.setInventorySlotContents(middle, ItemSpecialty.getItem(ItemSpecialty.CHEST, rand, 2));
		chest.setInventorySlotContents(middle + 2, ItemSpecialty.getItem(ItemSpecialty.PICK, rand, 2));
	}
}
