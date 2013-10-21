package greymerk.roguelike;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestWeapons extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){
		
		int rank = Dungeon.getRank(posY);
		
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;
		
		item = new ItemStack(Item.arrow, 2 + rand.nextInt(6));
		chest.setInventorySlotContents(middle - 1, item);
		
		item = ItemLoot.getEquipmentBySlot(rand, ItemLoot.WEAPON, rank, true);
		chest.setInventorySlotContents(middle, item);
		
		item = ItemLoot.getFood(rand, rank);
		chest.setInventorySlotContents(middle + 1, item);
	}

}
