package greymerk.roguelike;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestBlocks extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){

		int rank = Dungeon.getRank(posY);

		int middle = chest.getSizeInventory()/2;
		
		ItemStack item;

		for (int i = 0; i < 4 + rand.nextInt(3); i++) {
			item = ItemLoot.getBlocks(rand, rank);			
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
		}
	}
}
