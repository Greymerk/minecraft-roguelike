package greymerk.roguelike;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestEnchanting extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){
		int rank = Dungeon.getRank(posY);
		int middle;
		try{
			middle = chest.getSizeInventory()/2;
		} catch(NullPointerException e){
			return;
		}
		
				
		ItemStack item;
		
		item = new ItemStack(Item.expBottle, 2 + rand.nextInt(6));
		chest.setInventorySlotContents(middle - 1, item);
		
		item = ItemLoot.getEnchantedBook(rand, rank);
		chest.setInventorySlotContents(middle, item);
		
		item = new ItemStack(Item.enderPearl, 1 + rand.nextInt(3));
		chest.setInventorySlotContents(middle + 1, item);
	}
	

}
