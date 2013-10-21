package greymerk.roguelike;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestArmour extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){

		int rank = Dungeon.getRank(posY);
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;
		
		item = ItemLoot.getPotion(rand, rank);
		chest.setInventorySlotContents(middle - 1, item);
		
		int slot = 1 + rand.nextInt(4);
		item = ItemLoot.getEquipmentBySlot(rand, slot, rank, true);
		chest.setInventorySlotContents(middle, item);
		
		item = ItemLoot.getFood(rand, rank);
		chest.setInventorySlotContents(middle + 1, item);
	}

}
